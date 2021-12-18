package com.dauXanh.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dauXanh.dto.CartItem;
import com.dauXanh.dto.CategoryDTO;
import com.dauXanh.dto.ColorDTO;
import com.dauXanh.dto.Page;
import com.dauXanh.dto.ProductDTO;
import com.dauXanh.dto.ProductDetailDTO;
import com.dauXanh.dto.SizeDTO;
import com.dauXanh.entity.Product;
import com.dauXanh.entity.ProductDetail;
import com.dauXanh.service.CategoryService;
import com.dauXanh.service.ColorService;
import com.dauXanh.service.ProductService;
import com.dauXanh.service.SizeService;
import com.dauXanh.utils.StringChecker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ColorService colorService;

	@Autowired
	SizeService sizeService;

	/**
	 * For product/product.jsp
	 * 
	 * @param req
	 * @param session
	 * @return
	 */
	@GetMapping("/list")
	String getAllProducts(HttpServletRequest req, HttpSession session) {
		
		try {
			req.setAttribute("user", session.getAttribute("user"));
			req.setAttribute("cart", session.getAttribute("cart"));

			Page page = new Page(1, 8);
			req.setAttribute("page", page);
			req.setAttribute("products", productService.findAll(page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "products";
	}

	@GetMapping("/ajax-paging-list")
	@ResponseBody
	Map<String, Object> nextPage(@RequestParam String pageNumber) {
		
		try {
			Map<String, Object> response = new HashMap<String, Object>();

			Page page = new Page(Integer.parseInt(pageNumber.trim()), 8);
			response.put("page", page);

			List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
			productService.findAll(page).forEach(p -> {
				productDTOs.add(new ProductDTO(p.getId(), p.getName(), p.getCost(), p.getDescription(), p.getImg(),
						null, null));
			});
			response.put("products", productDTOs);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * For product/product-detail.jsp
	 * 
	 * @param productId
	 * @param req
	 * @param session
	 * @return
	 */
	@GetMapping("/detail/{productId}")
	String getProductDetail(@PathVariable int productId, HttpServletRequest req, HttpSession session) {
	
		try {
			req.setAttribute("user", session.getAttribute("user"));
			req.setAttribute("cart", session.getAttribute("cart"));

			req.setAttribute("product", productService.findById(productId));

			return "product-detail";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@PostMapping("/ajax-add-to-cart")
	@ResponseBody
	int addToCart(@RequestParam String cartItemJSON, HttpSession session) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode data = mapper.readTree(cartItemJSON);
			CartItem cartItem = new CartItem(data.get("productId").asInt(), data.get("productName").asText().trim(),
					data.get("productCost").asInt(), data.get("productDetailId").asInt(),
					data.get("productDetailQty").asInt(), data.get("colorId").asInt(),
					data.get("colorName").asText().trim(), data.get("sizeId").asInt(),
					data.get("sizeName").asText().trim());

			if (session.getAttribute("cart") == null) {
				session.setAttribute("cart", new ArrayList<CartItem>());
				session.setMaxInactiveInterval(60 * 3); // 3 mins
			}

			List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
			boolean isExist = false;
			for (CartItem item : cart) {
				if (item.getProductId() == cartItem.getProductId() && item.getColorId() == cartItem.getColorId()
						&& item.getSizeId() == cartItem.getSizeId()) {
					item.setProductDetailQty(item.getProductDetailQty() + 1);
					isExist = true;
				}
			}

			if (!isExist) {
				cart.add(cartItem);
			}

			return cart.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * product/cart.jsp
	 * 
	 * @param req
	 * @param session
	 * @return
	 */
	@GetMapping("/show-cart")
	String showCart(HttpServletRequest req, HttpSession session) {
		
		req.setAttribute("user", session.getAttribute("user"));
		req.setAttribute("cart", session.getAttribute("cart"));

		return "cart";
	}

	@PostMapping("/ajax-update-cart")
	@ResponseBody
	boolean updateCart(@RequestParam int productId, @RequestParam int productDetailQty, @RequestParam int colorId,
			@RequestParam int sizeId, HttpSession session) {
		
		if (session.getAttribute("cart") != null) {
			List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
			for (CartItem cartItem : cart) {
				if (cartItem.getProductId() == productId && cartItem.getColorId() == colorId
						&& cartItem.getSizeId() == sizeId) {
					cartItem.setProductDetailQty(productDetailQty);
				}
			}
			
			return true;
		}
		
		return false;
	}

	@PostMapping("/ajax-remove-from-cart")
	@ResponseBody
	int removeFromCart(@RequestParam int productId, @RequestParam int colorId, @RequestParam int sizeId,
			HttpSession session) {
		
		int pos = -1;
		if (session.getAttribute("cart") != null) {
			List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
			for (CartItem cartItem : cart) {
				if (cartItem.getProductId() == productId && cartItem.getColorId() == colorId
						&& cartItem.getSizeId() == sizeId) {
					pos = cart.indexOf(cartItem);
				}
			}

			if (pos > -1) {
				cart.remove(pos);
			}

			return cart.size();
		}
		
		return 0;
	}

	/**
	 * For product/manage-products.jsp
	 * 
	 * @param req
	 * @param session
	 * @return
	 */
	@GetMapping("/manage/list")
	String getMyProducts(HttpServletRequest req, HttpSession session) {
		try {
			req.setAttribute("user", session.getAttribute("user"));
			req.setAttribute("cart", session.getAttribute("cart"));

			Page page = new Page(1, 8);
			req.setAttribute("page", page);
			req.setAttribute("products", productService.findAll(page));
			req.setAttribute("categories", categoryService.findAll(null));
			req.setAttribute("colors", colorService.findAll(null));
			req.setAttribute("sizes", sizeService.findAll(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "manage-products";
	}

	@PostMapping("/ajax-add-product")
	@ResponseBody
	boolean addProduct(@RequestParam String dataJson, MultipartHttpServletRequest mulReg) {
		
		try {
			MultipartFile productImg = mulReg.getFile(mulReg.getFileNames().next());
			if (productImg != null && !StringChecker.isEmptyOrNull(productImg.getOriginalFilename())) {
				// Cach 1:
				productImg.transferTo(new File(
						"E:/4 New Phase/2 Coding/1 Java/1 Java Web/3 Spring MVC Project/shop_website-v.2.0.0/shop_website/src/main/webapp/public/img/product-upload/"
								+ productImg.getOriginalFilename()));
//				// Cach 2:
//				FileOutputStream outputStream = new FileOutputStream(new File(
//						"E:/4 New Phase/2 Coding/1 Java/1 Java Web/3 Spring MVC Project/shop_website-v.2.0.0/shop_website/src/main/webapp/public/img/product-upload/"
//								+ productImg.getOriginalFilename()));
//				outputStream.write(productImg.getBytes());
//				outputStream.close();
			} else {
				System.err.println("File not found");
			}

			ObjectMapper mapper = new ObjectMapper();

			JsonNode productJsonNode = mapper.readTree(dataJson);
			Product product = new Product(productJsonNode.get("productName").asText(),
					productJsonNode.get("productCost").asInt(), productJsonNode.get("productDescription").asText(),
					productJsonNode.get("productImg").asText(),
					categoryService.findById(productJsonNode.get("categoryId").asInt()), new HashSet<ProductDetail>(),
					null);

			JsonNode productDetailsJsonNodes = productJsonNode.get("productDetails");
			for (JsonNode node : productDetailsJsonNodes) {
				ProductDetail productDetail = new ProductDetail(node.get("productDetailQty").asInt(), product,
						colorService.findById(node.get("colorId").asInt()),
						sizeService.findById(node.get("sizeId").asInt()));
				productDetail.setCreatedAt(new Date());
				productDetail.setUpdatedAt(new Date());

				// important
				product.getProductDetails().add(productDetail);
			}
			
			return productService.save(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@PostMapping("/ajax-pre-fill")
	@ResponseBody
	ProductDTO preFill(@RequestParam int productId) {
		try {
			Product product = productService.findById(productId);

			CategoryDTO categoryDTO = new CategoryDTO(product.getCategory().getId(), product.getCategory().getName());

			Set<ProductDetailDTO> productDetailDTOs = new HashSet<ProductDetailDTO>();
			for (ProductDetail productDetail : product.getProductDetails()) {
				ColorDTO colorDTO = new ColorDTO(productDetail.getColor().getId(), productDetail.getColor().getName());
				SizeDTO sizeDTO = new SizeDTO(productDetail.getSize().getId(), productDetail.getSize().getName());
				ProductDetailDTO productDetailDTO = new ProductDetailDTO(productDetail.getId(), productDetail.getQty(),
						colorDTO, sizeDTO);

				// important
				productDetailDTOs.add(productDetailDTO);
			}

			return new ProductDTO(product.getId(), product.getName(), product.getCost(), product.getDescription(),
					product.getImg(), categoryDTO, productDetailDTOs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PostMapping("/ajax-update-product")
	@ResponseBody
	boolean updateProduct(@RequestParam String dataJson, MultipartHttpServletRequest mulReg) {
		try {
			MultipartFile productImg = mulReg.getFile(mulReg.getFileNames().next());
			if (productImg != null && !StringChecker.isEmptyOrNull(productImg.getOriginalFilename())) {
				// Cach 1:
				productImg.transferTo(new File(
						"E:/4 New Phase/2 Coding/1 Java/1 Java Web/3 Spring MVC Project/shop_website-v.2.0.0/shop_website/src/main/webapp/public/img/product-upload/"
								+ productImg.getOriginalFilename()));
//				// Cach 2:
//				FileOutputStream outputStream = new FileOutputStream(new File(
//						"E:/4 New Phase/2 Coding/1 Java/1 Java Web/3 Spring MVC Project/shop_website-v.2.0.0/shop_website/src/main/webapp/public/img/product-upload/"
//								+ productImg.getOriginalFilename()));
//				outputStream.write(productImg.getBytes());
//				outputStream.close();
			} else {
				System.err.println("File not found");
			}

			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode productJsonNode = mapper.readTree(dataJson);
			
//			// Để update 1 Product ta làm theo 1 trong 2 cách sau:
//			// Cach 1: new Product() và setId(_) theo id truyền vào là xong (nhanh nhưng không thích kiểu này lắm)
//			Product product = new Product();
//			product.setId(productJsonNode.get("productId").asInt());
			
			// Cach 2: Dùng Service lấy Product theo id truyền vào (lâu hơn nhưng khi đọc lại dễ hiểu hơn cách 1)		
			Product product = productService.findById(productJsonNode.get("productId").asInt());
			product.setName(productJsonNode.get("productName").asText());
			product.setCost(productJsonNode.get("productCost").asInt());
			product.setDescription(productJsonNode.get("productDescription").asText());
			if (!StringChecker.isEmptyOrNull(productImg.getOriginalFilename())) {
				product.setImg(productJsonNode.get("productImg").asText());
			} 
			product.setCategory(categoryService.findById(productJsonNode.get("categoryId").asInt()));
			
			product.setProductDetails(new HashSet<ProductDetail>());
			JsonNode productDetailJsonNodes = productJsonNode.get("productDetails");
			for (JsonNode node : productDetailJsonNodes) {
				
				// Vì ở đây khi update Product ta cũng update ProductDetail luôn (do Cascade.ALL), nên
				// cách update ProductDetail cũng tương tự như cách update Product (Sử dụng 1 trong 2 cách trên)
				// nhưng do ta chưa làm Service lấy ProductDetail theo id truyền vào nên ta dùng tạm cách 1
				ProductDetail productDetail = new ProductDetail();
				productDetail.setId(node.get("productDetailId").asInt());
				productDetail.setQty(node.get("productDetailQty").asInt());
				productDetail.setColor(colorService.findById(node.get("colorId").asInt()));
				productDetail.setSize(sizeService.findById(node.get("sizeId").asInt()));
				productDetail.setCreatedAt(new Date());
				productDetail.setUpdatedAt(new Date());
				
				// important
				productDetail.setProduct(product);
				product.getProductDetails().add(productDetail);
			}
			
			return productService.update(product); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@PostMapping("/ajax-remove-product")
	@ResponseBody
	boolean removeProduct(@RequestParam int productId) {
		
		try {
			return productService.deleteById(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
