package com.greatlearning.customerManagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Transaction;

import com.greatlearning.customerManagement.entity.Customer;

@Repository
public class CustomerServiceImpl implements CustomerService {

	private SessionFactory sessionFactory;

	private Session session;

	@Autowired
	public CustomerServiceImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Customer> findAll() {

		Transaction tx = session.beginTransaction();

		List<Customer> customer = session.createQuery("from Customer").list();

		tx.commit();

		return customer;
	}

	public Customer findById(int id) {
		Customer customer = new Customer();

		Transaction tx = session.beginTransaction();

		customer = session.get(Customer.class, id);

		tx.commit();

		return customer;
	}

	public void save(Customer theCustomer) {
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(theCustomer);

		tx.commit();

	}

	public void deleteById(int theId) {
		Customer customer = new Customer();

		Transaction tx = session.beginTransaction();

		customer = session.get(Customer.class, theId);

		session.delete(customer);

		tx.commit();

	}

	public List<Customer> searchBy(String firstName, String lastName) {
		Transaction tx = session.beginTransaction();
		String query = "";
		if (firstName.length() != 0 && lastName.length() != 0)
			query = "from Customer where firstname like '%" + firstName + "%' or lastName like '%" + lastName + "%'";
		else if (firstName.length() != 0)
			query = "from Customer where firstname like '%" + firstName + "%'";
		else if (lastName.length() != 0)
			query = "from Customer where lastName like '%" + lastName + "%'";
		else
			System.out.println("Cannot search without input data");

		List<Customer> theCustomer = session.createQuery(query).list();

		tx.commit();

		return theCustomer;
	}

}
