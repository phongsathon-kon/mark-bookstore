package com.mark.bookstore.service;

import java.util.Collection;

import com.mark.bookstore.dto.BookDTO;

public interface BookService {
	public Collection<BookDTO> getBooks() throws Exception;
}
