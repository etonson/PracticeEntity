package com.example.demo.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "TraingOrder")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", columnDefinition = "nvarchar(270)")
	private String id;

	private int quantity;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ManyToMany(mappedBy = "ownOrders")
	private List<Section> ownSections;

	@ManyToOne
	@JoinColumn(name = "WAREHOUSE_ID")
	private WareHouse toWareHouse;
	
	public Order() {}
	
	public Order(String id,int quantity,OrderStatus status) {
		this.id=id;
		this.quantity=quantity;
		this.status=status;
	}
	
	public enum OrderStatus {
		DEDICATED("DEDICATED", "預計投入"), 
		WIP("WIP", "work in process 已經投入"), 
		FG("FG", "完成");

		private String status;
		private String desc;

		private OrderStatus(String status, String desc) {
			this.status = status;
			this.setDesc(desc);
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<Section> getOwnSections() {
		return ownSections;
	}

	public void setOwnSections(List<Section> ownSections) {
		this.ownSections = ownSections;
	}

	public WareHouse getToWareHouse() {
		return toWareHouse;
	}

	public void setToWareHouse(WareHouse toWareHouse) {
		this.toWareHouse = toWareHouse;
	}
	
}
