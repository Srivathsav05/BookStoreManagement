package com.BookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.BookStore.entity.Book;
import com.BookStore.entity.MyBookList;
import com.BookStore.service.BookService;
import com.BookStore.service.MyBookListService;

import ch.qos.logback.core.model.Model;

@Controller
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private MyBookListService mybookservice;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/book_register")
    public String bookRegister() {
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBooks() {
        List<Book> list = service.getAllBooks();
        return new ModelAndView("bookList", "book", list);
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        service.save(b);
        return "redirect:/available_books";
    }

    @GetMapping("/my_books")
    public ModelAndView getMyBooks(Model model) {
        List<MyBookList> list = mybookservice.getAllMyBooks();
        return new ModelAndView("myBooks", "book", list);
    }

    @GetMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Book b = service.getBookById(id);
        MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
        mybookservice.saveMyBooks(mb);
        return "redirect:/my_books";
    }
//    @GetMapping("/mylist/{id}")
//    public String getMyList(@PathVariable("id") int id) {
//        Book b = service.getBookById(id);
//        MyBookList mb = new MyBookList(b.getName(), b.getAuthor(), b.getPrice());
//        mybookservice.saveMyBooks(mb);
//        return "redirect:/my_books";
//    }


    @GetMapping("/editBook/{id}")
    public ModelAndView editBook(@PathVariable("id") int id, Model model) {
        Book b = service.getBookById(id);
        return new ModelAndView("bookEdit", "book", b);  // Updated view name here
    }
    
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}
}




