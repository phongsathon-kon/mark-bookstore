package com.mark.bookstore.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mark.bookstore.dto.BookDTO;
import com.mark.bookstore.model.Order;
import com.mark.bookstore.model.Orders;
import com.mark.bookstore.model.User;
import com.mark.bookstore.model.Users;
import com.mark.bookstore.service.BookService;
import com.mark.bookstore.service.OrderService;
import com.mark.bookstore.service.UserService;
import com.mark.bookstore.util.CustomErrorType;


@RestController
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BookService bookService;

	
	@CrossOrigin
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User newUser) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		if (userService.find(newUser.getUsername()) != null) {
			logger.error("username Already exist " + newUser.getUsername());
			return new ResponseEntity<Object>(
					new CustomErrorType("user with username " + newUser.getUsername() + " already exist "),
					HttpStatus.CONFLICT);
		}
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		newUser.setRole("USER");
		userService.save(newUser);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@JsonView(Users.View.DetailForAll.class)
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		Optional<User> user = userService.find(id);
		if (user.isEmpty()) {
			logger.error("user not found ");
			return new ResponseEntity<Object>(
					new CustomErrorType("user with user id " + id + " not found "),
					HttpStatus.NOT_FOUND);
		}
		User userDB = user.get();
		List<Order> orders = orderService.findByUserId(id);
		if (orders != null) {
			ArrayList<String> books = new ArrayList<String>();
			for(Order each : orders) {
				List<String> lst = new ArrayList<String>(Arrays.asList(each.getBooks().split(",")));
				books.addAll(lst);
			}
			userDB.setBooks(books.toString());
		}
		return new ResponseEntity<User>(userDB, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		Optional<User> user = userService.find(id);
		if (user.isEmpty()) {
			logger.error("user does not exist ");
			return new ResponseEntity<Object>(
					new CustomErrorType("user with user id " + id + " does not exist "),
					HttpStatus.NOT_FOUND);
		}
		userService.delete(user.get());
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@JsonView(Orders.View.DetailForAll.class)
	@RequestMapping(value = "/users/{id}/orders", method = RequestMethod.POST)
	public ResponseEntity<?> getUserOrdersPrice(@PathVariable Long id) {
		Optional<User> user = userService.find(id);
		if (user.isEmpty()) {
			logger.error("user not found ");
			return new ResponseEntity<Object>(
					new CustomErrorType("user with user id " + id + " not found "),
					HttpStatus.NOT_FOUND);
		}
		User userDB = user.get();
		List<Order> orders = orderService.findByUserId(id);
		ArrayList<String> booksStr = new ArrayList<String>();
		if (orders != null) {
			for(Order each : orders) {
				List<String> lst = new ArrayList<String>(Arrays.asList(each.getBooks().split(",")));
				booksStr.addAll(lst);
			}
			userDB.setBooks(booksStr.toString());
		}
		
		List<BigDecimal> bdList = new ArrayList<>();
		try {
			Collection<BookDTO> books = bookService.getBooks();
			booksStr.forEach(str -> {
				books.forEach(each -> {
					if(each.getId() == Long.parseLong(str)) {
						bdList.add(each.getPrice());
					}
				});
			});
		} catch (Exception e) {
			logger.error("can't get books :" + e);
		}
		Order result = new Order();
		BigDecimal sumPrice = bdList.stream()
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
		result.setPrice(sumPrice);
		return new ResponseEntity<Order>(result, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping("/login")
	public Principal user(Principal principal) {
		logger.info("user logged "+principal);
		return principal;
	}

	
	
}
