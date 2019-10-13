package com.virtalpairprogrammers.escapingreferences.customers.customerimplementation;

import com.virtalpairprogrammers.escapingreferences.customers.ReadonlyCustomer;

public class Customer implements ReadonlyCustomer {
	private String name;

	public Customer(String name) {
		this.name = name;
	}

	public Customer(ReadonlyCustomer c) {
		this.name = c.getName();
	}
	

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
