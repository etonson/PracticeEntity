package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMPLOYEE_ID", columnDefinition = "nvarchar(30)")
	private int employeeId;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String name;

	@OneToOne(mappedBy = "manager") 
	private Department department;
	
	@OneToOne(mappedBy = "manager") 
	private Section section;
	
	
	public Person() {
	}

	public Person(int employeeId) {
		this.employeeId = employeeId;
	}
	public Person(int employeeId,String name) {
		this.employeeId = employeeId;
		this.name = name;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	
}
