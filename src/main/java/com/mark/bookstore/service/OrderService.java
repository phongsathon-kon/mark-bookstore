package com.mark.bookstore.service;

import java.util.List;
import java.util.Optional;

import com.mark.bookstore.model.Order;

public interface OrderService {

	public Optional<Order> find(Long id);
	
	public List<Order> findByUserId(Long userid);

}
