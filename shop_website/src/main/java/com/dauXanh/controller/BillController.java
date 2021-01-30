package com.dauXanh.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dauXanh.dto.CartItem;
import com.dauXanh.entity.Bill;
import com.dauXanh.entity.BillDetail;
import com.dauXanh.entity.embeddedId.BillDetail_Id;
import com.dauXanh.service.BillDetailService;
import com.dauXanh.service.BillService;
import com.dauXanh.utils.StringChecker;

@Controller
@RequestMapping("/bill")
public class BillController {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	BillService billService;

	@Autowired
	BillDetailService billDetailService;

	/**
	 * For product/cart.jsp
	 * 
	 * @param productId
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("/ajax-order")
	@ResponseBody
	boolean order(@RequestParam String userName, @RequestParam String userPhoneNumber, @RequestParam String userAddress,
			HttpSession session) {
		
		try {
			if (StringChecker.isEmptyOrNull(userName) || StringChecker.isEmptyOrNull(userPhoneNumber)
					|| StringChecker.isEmptyOrNull(userAddress)) {
				return false;
			}

			if (session.getAttribute("cart") != null) {
				Bill bill = new Bill(Calendar.getInstance().getTimeInMillis() + " - Bill - " + userName, userName, false,
						userPhoneNumber, userAddress);
				
				boolean isAddSuccess = billService.save(bill);
				if (isAddSuccess) {
					List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
					int savedBillId = billService.findByName(bill.getName()).getId();

					if (cart.size() > 0 && savedBillId > 0) {
						for (CartItem cartItem : cart) {
							BillDetail billDetail = new BillDetail();
							billDetail.setId(new BillDetail_Id(savedBillId, cartItem.getProductDetailId()));
							billDetail.setCost(cartItem.getProductCost());
							billDetail.setQty(cartItem.getProductDetailQty());

							billDetailService.save(billDetail);
						}

						return true;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
