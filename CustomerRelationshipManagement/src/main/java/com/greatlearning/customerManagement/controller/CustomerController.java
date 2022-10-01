package com.greatlearning.customerManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.customerManagement.entity.Customer;
import com.greatlearning.customerManagement.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/list")
	public String listCustomer(Model theModel) {

		List<Customer> theCustomer = customerService.findAll();

		theModel.addAttribute("Customers", theCustomer);

		return "list-Customers";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Customer theCustomer = new Customer();
		theModel.addAttribute("Customer", theCustomer);
		return "Customer-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theID, Model theModel) {
		Customer theCustomer = customerService.findById(theID);
		theModel.addAttribute("Customer", theCustomer);
		return "Customer-form";
	}

	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {

		System.out.println(id);
		Customer theCustomer;

		if (id != 0) {
			theCustomer = customerService.findById(id);
			theCustomer.setFirstName(firstName);
			theCustomer.setLastName(lastName);
			theCustomer.setEmail(email);

		} else {
			theCustomer = new Customer(firstName, lastName, email);
		}

		customerService.save(theCustomer);

		return "redirect:/customer/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {

		customerService.deleteById(theId);

		return "redirect:/customer/list";
	}

	@RequestMapping("/print")

	public String listCustomers(Model theModel) {

		List<Customer> theCustomer = customerService.findAll();

		theModel.addAttribute("Customers", theCustomer);

		return "Print-List";
	}

	@RequestMapping("/search")
	public String search(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			Model theModel) {

		// check names, if both are empty then just give list of all Customers

		if (firstName.trim().isEmpty() && lastName.trim().isEmpty()) {
			return "redirect:/customer/list";
		} else {
			// else, search by first name and last name
			List<Customer> theCustomer = customerService.searchBy(firstName, lastName);

			// add to the spring model
			theModel.addAttribute("Customers", theCustomer);

			// send to list-Books
			return "list-Customers";
		}
	}
}
