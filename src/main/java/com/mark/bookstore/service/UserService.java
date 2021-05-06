package com.mark.bookstore.service;

import java.util.Optional;

import com.mark.bookstore.model.User;

public interface UserService {
	public User save(User user);

	public User update(User user);
	
	public User delete(User user);

	public User find(String userName);

	public Optional<User> find(Long id);

}
