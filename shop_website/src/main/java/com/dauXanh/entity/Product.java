package com.dauXanh.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotEmpty(message = "Name is required")
	String name;

	int cost;
	String description;
	String img;

	@ManyToOne
	@JoinColumn(name = "id_category")
	Category category;

	@OneToMany(/* fetch = FetchType.EAGER, */cascade = CascadeType.ALL, mappedBy = "product")
	Set<ProductDetail> productDetails;

	@ManyToMany
	@JoinTable(name = "sale_detail", joinColumns = {
			@JoinColumn(name = "id_product", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_sale", referencedColumnName = "id") })
	Set<Sale> sales;

	@Temporal(TemporalType.DATE)
	Date createdAt;

	@Temporal(TemporalType.DATE)
	Date updatedAt;
	
	public Product() {
	}
	
	public Product(@NotEmpty(message = "Name is required") String name, int cost, String description, String img,
			Category category, Set<ProductDetail> productDetails, Set<Sale> sales) {
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.img = img;
		this.category = category;
		this.productDetails = productDetails;
		this.sales = sales;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Set<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}

	public Set<Sale> getSales() {
		return sales;
	}

	public void setSales(Set<Sale> sales) {
		this.sales = sales;
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
