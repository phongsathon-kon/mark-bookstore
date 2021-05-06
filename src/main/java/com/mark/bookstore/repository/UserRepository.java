package com.mark.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mark.bookstore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

public User findOneByUsername(String username);
}
