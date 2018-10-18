package com.java.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
	public Address() {
		super();
	}

	@Id
	int hno;

	public Address(int hno) {
		super();
		this.hno = hno;
	}

	public int getHno() {
		return hno;
	}

	public void setHno(int hno) {
		this.hno = hno;
	}
}
