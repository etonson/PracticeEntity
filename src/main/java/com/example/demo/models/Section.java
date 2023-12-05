package com.example.demo.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private SectionPK sectionPK;
	@ManyToMany
	private List<Order> ownOrders;
	@OneToOne
//	@JoinColumn(name = "MANAGER_EMPLOYEE_ID")
	private Person manager;
	

	@ManyToOne
	@JoinColumn(name = "departmentName", referencedColumnName = "DEPARTMENT_NAME",insertable = false,updatable = false)
	private Department department;
	
	public Section() {
	}

	public Section(SectionPK sectionPK, Person manager) {
		this.sectionPK=sectionPK;
		this.manager=manager;
	}

	public Section(SectionPK sectionPK, List<Order> ownOrders) {
		this.sectionPK=sectionPK;
		this.ownOrders=ownOrders;
	}

	public SectionPK getSectionPK() {
		return sectionPK;
	}

	public void setSectionPK(SectionPK sectionPK) {
		this.sectionPK = sectionPK;
	}

	public List<Order> getOwnOrders() {
		return ownOrders;
	}

	public void setOwnOrders(List<Order> ownOrders) {
		this.ownOrders = ownOrders;
	}

	public Person getManager() {
		return manager;
	}

	public void setManager(Person manager) {
		this.manager = manager;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
