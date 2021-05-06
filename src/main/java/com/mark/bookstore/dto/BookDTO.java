package com.mark.bookstore.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BookDTO {
	private Long id;
	private String book_name;
	private BigDecimal price;
	private String author_name;
	
	@JsonIgnore
	private boolean recommend;
	
	public BookDTO() {
		super();
	}

	
	public BookDTO(Long id, String book_name, BigDecimal price, String author_name, boolean recommend) {
		super();
		this.id = id;
		this.book_name = book_name;
		this.price = price;
		this.author_name = author_name;
		this.recommend = recommend;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	
	public boolean isRecommend() {
		return recommend;
	}
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}


	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", book_name=" + book_name + ", price=" + price + ", author_name=" + author_name
				+ ", recommend=" + recommend + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author_name == null) ? 0 : author_name.hashCode());
		result = prime * result + ((book_name == null) ? 0 : book_name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + (recommend ? 1231 : 1237);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDTO other = (BookDTO) obj;
		if (author_name == null) {
			if (other.author_name != null)
				return false;
		} else if (!author_name.equals(other.author_name))
			return false;
		if (book_name == null) {
			if (other.book_name != null)
				return false;
		} else if (!book_name.equals(other.book_name))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (recommend != other.recommend)
			return false;
		return true;
	}
	
	
}
