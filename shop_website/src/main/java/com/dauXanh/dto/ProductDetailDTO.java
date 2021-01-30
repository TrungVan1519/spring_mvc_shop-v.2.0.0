package com.dauXanh.dto;

public class ProductDetailDTO {

	int id;
	int qty;
	ColorDTO colorDTO;
	SizeDTO sizeDTO;

	public ProductDetailDTO(int id, int qty, ColorDTO colorDTO, SizeDTO sizeDTO) {
		this.id = id;
		this.qty = qty;
		this.colorDTO = colorDTO;
		this.sizeDTO = sizeDTO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public ColorDTO getColorDTO() {
		return colorDTO;
	}

	public void setColorDTO(ColorDTO colorDTO) {
		this.colorDTO = colorDTO;
	}

	public SizeDTO getSizeDTO() {
		return sizeDTO;
	}

	public void setSizeDTO(SizeDTO sizeDTO) {
		this.sizeDTO = sizeDTO;
	}
}
