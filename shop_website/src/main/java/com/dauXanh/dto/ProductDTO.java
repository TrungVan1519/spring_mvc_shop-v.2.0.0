package com.dauXanh.dto;

import java.util.Set;

public class ProductDTO {

	int id;
	String name;
	int cost;
	String description;
	String img;
	CategoryDTO categoryDTO;
	Set<ProductDetailDTO> productDetailDTOs;

	public ProductDTO(int id, String name, int cost, String description, String img, CategoryDTO categoryDTO,
			Set<ProductDetailDTO> productDetailDTOs) {
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.img = img;
		this.categoryDTO = categoryDTO;
		this.productDetailDTOs = productDetailDTOs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	public Set<ProductDetailDTO> getProductDetailDTOs() {
		return productDetailDTOs;
	}

	public void setProductDetailDTOs(Set<ProductDetailDTO> productDetailDTOs) {
		this.productDetailDTOs = productDetailDTOs;
	}
}
