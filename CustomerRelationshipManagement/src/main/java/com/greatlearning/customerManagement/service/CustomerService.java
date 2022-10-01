package com.greatlearning.customerManagement.service;

import java.util.List;

import com.greatlearning.customerManagement.entity.Customer;

public interface CustomerService {

	public List<Customer> findAll();

	public Customer findById(int id);

	public void save(Customer theCustomer);

	public void deleteById(int theId);

	public List<Customer> searchBy(String firstName, String lastName);

}
