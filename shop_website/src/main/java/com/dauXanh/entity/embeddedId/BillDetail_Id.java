package com.dauXanh.entity.embeddedId;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillDetail_Id implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_bill")
	protected int billId;

	@Column(name = "id_product_detail")
	protected int productDetailId;

	public BillDetail_Id() {
	}
	
	public BillDetail_Id(int billId, int productDetailId) {
		this.billId = billId;
		this.productDetailId = productDetailId;
	}

	public int getBillId() {
		return billId;
	}

	public int getProductDetailId() {
		return productDetailId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (! (obj instanceof BillDetail_Id)) return false;
		BillDetail_Id that = (BillDetail_Id) obj;
		
		boolean result1 = Objects.equals(this.getBillId(), that.getBillId());
		boolean result2 = Objects.equals(this.getProductDetailId(), that.getProductDetailId());
		return result1 && result2;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getBillId(), this.getProductDetailId());
	}
}
