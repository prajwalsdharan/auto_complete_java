package com.powerschool.assignment.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_names")
public class USName {
	
	public USName()
	{
		super();
	}
	
	public USName(String firstName) {
		super();
		
		if(firstName!=null && firstName.trim().length()!=0)
			this.firstName = firstName;
	}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="firstname")
	private String firstName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName!=null && firstName.trim().length()!=0)
			this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "[" + id + ", " + firstName + "]";
	}
	
	
}