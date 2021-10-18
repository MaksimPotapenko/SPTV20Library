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
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class App {
    private Book[] books = new Book[10];
    private User[] users = new User[10];
    private History[] histories = new History[10];
    public void run() {
        String repeat = "yes";
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("Выберите номер задачи: ");
            System.out.println("0 - закрыть программу");
            System.out.println("1 - добавить читателя");
            System.out.println("2 - добавить книгу");
            System.out.println("3 - список книг");
            System.out.println("4 - выдать книгу читателю");
            int task = scanner.nextInt(); scanner.nextLine();
            switch (task) {
                case 0:
                    repeat = "no";
                    break;
                case 1:
                    User user = new User();
                    System.out.println("Введите имя читателя: ");
                    user.setFirstname(scanner.nextLine());
                    System.out.println("Введите фамилию читателя: ");
                    user.setLastname(scanner.nextLine());
                    System.out.println("введите телефон читателя: ");
                    user.setPhone(scanner.nextLine());
                    System.out.println("Читатель инициализирован: "+user.toString());
                    for (int i = 0; i < users.length; i++) {
                        if (users[i] == null) {
                            users[i] = user;
                        }
                        break;
                    }
                    break;
                case 2:
                    Book book = new Book();
                    System.out.println("Введите название книги: ");
                    book.setBookName(scanner.nextLine());
                    System.out.println("Введите количество авторов книги: ");
                    int countAuthors = (scanner.nextInt());scanner.nextLine();
                    Author[] authors = new Author[countAuthors];
                    for (int i = 0; i < countAuthors; i++) {
                        Author author = new Author();
                        System.out.print("Введите имя автора: " +(i+1)+": " );
                        author.setFirstName(scanner.nextLine());
                        System.out.print("Введите фамилию автора: ");
                        author.setLastName(scanner.nextLine());
                        System.out.print("введите год рождения автора: ");
                        author.setBirthYear(scanner.nextInt());scanner.nextLine();
                        authors[i] = author;
                    }
                    book.setAuthors(authors);
                    for (int i = 0; i < books.length; i++) {
                        if(books[i] == null) {
                            books[i] = book;
                            break;
                        }
                    }
                    
                    
                    System.out.println("Введите год издания книги: ");
                    book.setReleaseYear(scanner.nextInt());scanner.nextLine();
                    System.out.println("Книга инициирована: "+book.toString());
                    break;
                case 3:
                    System.out.println("Список книг: ");
                    for (int i = 0; i < books.length; i++) {
                        if(books[i] != null) {
                            System.out.printf("%n. %s. %s %s. %d%n"
                                ,i+1
                                ,books[i].getBookName()
                                ,Arrays.toString(books[i].getAuthors())
                                ,books[i].getReleaseYear()
                            );  
                        }
                        
                    }
                    break;
                case 4:     
                    System.out.println("Выдать книгу");
                    System.out.println("Выберите номер книги: ");
                    int numberBook = scanner.nextInt(); scanner.nextInt();
                    
                    System.out.println("Список читателей: ");
                    for (int i = 0; i < users.length; i++) {
                        if(users[i] != null) {
                            System.out.printf("%n. %s. %s %s. %d%n"
                                ,i+1
                                ,users[i].getFirstname()
                                ,users[i].getLastname()
                                ,users[i].getPhone()
                            );  
                        }
                        
                    }
                    System.out.println("Выберите номер книги: ");
                    int numberUser = scanner.nextInt(); scanner.nextInt();
                    
                    History history = new History();
                    history.setBook(books[numberBook - 1]);
                    history.setUser(users[numberUser - 1]);
                    Calendar c = new GregorianCalendar();
                    history.setGivenBook(c.getTime());
                    for (int i = 0; i < histories.length; i++) {
                        if(histories[i] == null) {
                            histories[i] = history;
                            break;
                        }
                    }
                    System.out.println("------------");
                    break;
                    
                default:
                    System.out.println("Выберите номер из списка!");
            }
        }while("yes".equals(repeat));
        System.out.println("Пока!");
        
    }
}
