package com.virtalpairprogrammers.escapingreferences.customers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.virtalpairprogrammers.escapingreferences.customers.customerimplementation.Customer;

public class CustomerRecords implements Iterable<Customer> {
	private Map<String, Customer> records;

	public CustomerRecords() {
		this.records = new HashMap<String, Customer>();
	}

	public void addCustomer(Customer c) {
		this.records.put(c.getName(), c);
	}

	// public Map<String, Customer> getCustomers() {
	// 	return this.records;
	// }

	// public Map<String, Customer> getCustomers() {
	// 	// return copy only List
	// 	return new HashMap<String,Customer>(this.records);
	// }

	// public Map<String, Customer> getCustomers() {
	// 	return not modifiable copy Java8
	// 	return Collections.unmodifiableMap(this.records);
	// }

	public Map<String, Customer> getCustomers() {
		// return not modifiable copy Java10+ (better then in Java 8)
		return Map.copyOf(this.records);
	}

	@Override
	public Iterator<Customer> iterator() {
		return records.values().iterator();
	}

	// public Customer find(String name) {
	// 	return records.get(name);
	// }

	public ReadonlyCustomer find(String name) {
		// Copy constructor collection
		return new Customer(records.get(name));
	}
}
