package com.mark.bookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.bookstore.model.User;
import com.mark.bookstore.repository.UserRepository;


@Service
public class UserServiceImpl implements  UserService {

	@Autowired
	UserRepository userRepository;

	public User save(User user) {
		return userRepository.saveAndFlush(user);
	}

	public User update(User user) {
		return userRepository.save(user);
	}

	public User find(String userName) {
		return userRepository.findOneByUsername(userName);
	}

	public Optional<User> find(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User delete(User user) {
		userRepository.delete(user);
		return user;
	}
}
