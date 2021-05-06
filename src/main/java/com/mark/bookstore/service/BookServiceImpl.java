package com.mark.bookstore.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.bookstore.dto.BookDTO;

@Service
public class BookServiceImpl implements BookService {

	public static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Collection<BookDTO> getBooks() throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate(); 
		String urlBook = "https://scb-test-book-publisher.herokuapp.com/books"; 
		String urlRecommendBook = "https://scb-test-book-publisher.herokuapp.com/books/recommendation";
		
		ResponseEntity<ArrayList> booksRes = restTemplate.getForEntity(urlBook, ArrayList.class); 
		logger.info("Books response : "+ booksRes);
		ResponseEntity<ArrayList> recommendBooksRes = restTemplate.getForEntity(urlRecommendBook, ArrayList.class); 
		logger.info("Recommend Books response : "+ recommendBooksRes);
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<BookDTO> books = mapper.convertValue(booksRes.getBody(), new TypeReference<ArrayList<BookDTO>>() { });
		ArrayList<BookDTO> recommendBooks = mapper.convertValue(recommendBooksRes.getBody(), new TypeReference<ArrayList<BookDTO>>() { });
		
		for(BookDTO each : books) {
			if(recommendBooks.contains(each)) {
				each.setRecommend(true);
			} else {
				each.setRecommend(false);
			}
		}
		
		Collections.sort(books, new Comparator<BookDTO>() {
	        @Override
	        public int compare(BookDTO a1, BookDTO a2) {
	            return Boolean.compare(a2.isRecommend(),a1.isRecommend());
	        }
	    });
		
		books.stream().distinct().collect(Collectors.toList());
		
		return books;
	}

}
