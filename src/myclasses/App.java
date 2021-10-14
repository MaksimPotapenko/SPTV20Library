/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myclasses;


import entity.Author;
import entity.Book;
import entity.History;
import entity.User;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author user
 */
public class App {
    public void run() {
        // System.out.println("Hello");
        Book book = new Book();
        book.setBookName("War and Peace");
        book.setReleaseYear(2010);
        Author[] authors = new Author[1];
        Author author = new Author();
        author.setFirstName("Lev");
        author.setLastName("Tolstoy");
        author.setBirthYear(1828);
        authors[0] = author;
        book.setAuthors(authors);
        System.out.printf("Создана книга: %s, автор: %s %S, год издания: %d%n",
                book.getBookName(),
                book.getAuthors()[0].getFirstName(),
                book.getAuthors()[0].getLastName(),
                book.getReleaseYear()
                );
        
        System.out.println( );
        
        User user = new User();
        user.setFirstname("Pablo");
        user.setLastname("Michael");
        user.setPhone("56681098");
        System.out.printf("Создан пользователь: %s %S, с телефоном: %s%n",
                user.getFirstname(),
                user.getLastname(),
                user.getPhone()
                );
        
        System.out.println( );
        
        History history = new History();
        history.setBook(book);
        history.setUser(user);
        Calendar c = new GregorianCalendar();
        history.setGivenBook(c.getTime());
        System.out.printf("Читатель %s %S взял читать книгу \"%s\", %s%n",
                history.getUser().getFirstname(),
                history.getUser().getLastname(),
                history.getBook().getBookName(),
                history.getGivenBook()
                );
        
        c = new GregorianCalendar();
        history.setReturnBook(c.getTime());
        System.out.printf("Читатель %s %S взял читать книгу \"%s\", %s%n",
                history.getUser().getFirstname(),
                history.getUser().getLastname(),
                history.getBook().getBookName(),
                history.getReturnBook()
                );
        
    }
}
