package com.mark.bookstore.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mark.bookstore.dto.BookDTO;
import com.mark.bookstore.service.BookService;

@RestController
public class BookController {

	public static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	@CrossOrigin
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ResponseEntity<?> findBooks() {
		
		Collection<BookDTO> results = null;
		try {
			results = bookService.getBooks();
		} catch (Exception e) {
			logger.error("can't find book error response : "+ e);
		}
		return new ResponseEntity<Collection<BookDTO>>(results, HttpStatus.OK);
	}
}
