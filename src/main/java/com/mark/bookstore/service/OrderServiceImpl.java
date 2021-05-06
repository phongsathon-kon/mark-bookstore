package com.mark.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.bookstore.model.Order;
import com.mark.bookstore.repository.OrderRepository;


@Service
public class OrderServiceImpl implements  OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Override
	public Optional<Order> find(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public List<Order> findByUserId(Long userid) {
		return orderRepository.findByUserId(userid);
	}

}
