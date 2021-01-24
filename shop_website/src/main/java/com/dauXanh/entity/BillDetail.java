package com.dauXanh.entity;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dauXanh.entity.embeddedId.BillDetail_Id;

@Entity
@Table(name = "bill_detail")
public class BillDetail {

	@EmbeddedId
	BillDetail_Id id;
	
	int cost;
	int qty;
	
	@Temporal(TemporalType.DATE)
	Date createdAt;
	
	@Temporal(TemporalType.DATE)
	Date updatedAt;

	public BillDetail_Id getId() {
		return id;
	}

	public void setId(BillDetail_Id id) {
		this.id = id;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
