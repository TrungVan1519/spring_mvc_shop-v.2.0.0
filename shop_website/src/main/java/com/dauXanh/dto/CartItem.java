package com.dauXanh.dto;

public class CartItem {
	int productId;
	int colorId;
	int sizeId;
	int productDetailId;

	int cost;
	int qty;

	String productName;
	String colorName;
	String sizeName;

	public CartItem(int productId, int colorId, int sizeId, int productDetailId, int cost, int qty, String productName,
			String colorName, String sizeName) {
		this.productId = productId;
		this.colorId = colorId;
		this.sizeId = sizeId;
		this.productDetailId = productDetailId;
		this.cost = cost;
		this.qty = qty;
		this.productName = productName;
		this.colorName = colorName;
		this.sizeName = sizeName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public int getSizeId() {
		return sizeId;
	}

	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}

	public int getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(int productDetailId) {
		this.productDetailId = productDetailId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	// Builder class
	public static class CartItemBuilder {
		int productId;
		int colorId;
		int sizeId;
		int productDetailId;

		int cost;
		int qty;

		String productName;
		String colorName;
		String sizeName;

		public CartItemBuilder withProductId(int productId) {
			this.productId = productId;
			return this;
		}

		public CartItemBuilder withColorId(int colorId) {
			this.colorId = colorId;
			return this;
		}

		public CartItemBuilder withSizeId(int sizeId) {
			this.sizeId = sizeId;
			return this;
		}

		public CartItemBuilder withProductDetailId(int productDetailId) {
			this.productDetailId = productDetailId;
			return this;
		}

		public CartItemBuilder withCost(int cost) {
			this.cost = cost;
			return this;
		}

		public CartItemBuilder withQty(int qty) {
			this.qty = qty;
			return this;
		}

		public CartItemBuilder withProductName(String productName) {
			this.productName = productName;
			return this;
		}

		public CartItemBuilder withColorName(String colorName) {
			this.colorName = colorName;
			return this;
		}

		public CartItemBuilder withSizeName(String sizeName) {
			this.sizeName = sizeName;
			return this;
		}

		public CartItem build() {
			return new CartItem(productId, colorId, sizeId, productDetailId, cost, qty, productName, colorName,
					sizeName);
		}
	}

	@Override
	public String toString() {
		return "CartItem [productId=" + productId + ", colorId=" + colorId + ", sizeId=" + sizeId + ", productDetailId="
				+ productDetailId + ", cost=" + cost + ", qty=" + qty + ", productName=" + productName + ", colorName="
				+ colorName + ", sizeName=" + sizeName + "]";
	}
	
}
