package com.pagination.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pagination.entity.Books;
import com.pagination.repository.IBookRepository;

@Service
@Transactional
public class BookService {
	
	@Autowired
	private IBookRepository bookRepo;
		
	public Books saveBook(Books temp) {
		Books newBook = new Books();
		String title = temp.getTitle();
		String author = temp.getAuthor();
		Integer year = temp.getYear();
		Double price = temp.getPrice();
		if(!(title == null || author == null || year == null || price == null)) {
			newBook.setTitle(title);
			newBook.setAuthor(author);
			newBook.setYear(year);
			newBook.setPrice(price);
			return bookRepo.save(newBook);
		}
		return null;
	}
	
	//method to save a list of books at once
	public List<Books> saveList(List<Books> bookList) {
		List<Books> savedItems = new ArrayList<>();
		for(Books temp : bookList) {
			if(temp.getTitle() == null || temp.getAuthor() == null || temp.getYear() == null || temp.getPrice() == null ) {
				continue;
			}
			Books newItem = new Books();
			newItem.setTitle(temp.getTitle());
			newItem.setAuthor(temp.getAuthor());
			newItem.setPrice(temp.getPrice());
			newItem.setYear(temp.getYear());
			savedItems.add(bookRepo.save(newItem));
		}
		return savedItems;
	}
		
	public Books findById(Long id) {
		if(bookRepo.existsById(id)) {
			return bookRepo.findById(id).get();
		}
		return null;
	}
		
	 /*
	 Retrieves a paginated list of all books sorted by price in ascending order.
	 page - The page number (0-indexed) of the results to retrieve.
	 size - The number of books to include in each page.
	 return a page containing the list of books based on above specifications.
	 
	 We use the PagingAndSorting Interface which is one of the superclasses of the JpaRepository to achieve this
	 */
	public Page<Books> findAllBooks(int page, int size) {		
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("price")));
		return bookRepo.findAll(pageable);
	}
		
	public Books updateBook(Long id, Books temp){
		if(bookRepo.existsById(id)) {
			Books updateBook = bookRepo.findById(id).get();
			updateBook.setTitle(temp.getTitle());
			updateBook.setAuthor(temp.getAuthor());
			updateBook.setYear(temp.getYear());
			updateBook.setPrice(temp.getPrice());
			return bookRepo.save(updateBook);
		}
		return null;
	}
		
	public String deleteById(Long id) {
        if (bookRepo.existsById(id)) {
        	bookRepo.deleteById(id);
	        return "Deleted";
	    } 
        else {
	        return "Book with the given id is not found";
	    }    
	}
	 

}
