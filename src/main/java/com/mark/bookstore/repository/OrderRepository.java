package com.mark.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mark.bookstore.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

public List<Order> findByUserId(Long userId);
}
