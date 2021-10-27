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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import tools.SaverToFiles;

/**
 *
 * @author user
 */
public class App {
    private Scanner scanner = new Scanner(System.in);
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
    private SaverToFiles saverToFiles = new SaverToFiles();
    
    public App(){
        books = saverToFiles.loadBooks();
        users = saverToFiles.loadUsers();
        histories = saverToFiles.loadHistories();
    }
    
    public void run() {
        String repeat = "yes";
        do{
            System.out.println("Выберите номер задачи: ");
            System.out.println("0 - закрыть программу");
            System.out.println("1 - добавить читателя");
            System.out.println("2 - добавить книгу");
            System.out.println("3 - список книг");
            System.out.println("4 - выдать книгу читателю");
            System.out.println("5 - вернуть книгу обратно");
            System.out.println("6 - список читателей");
            System.out.println("7 - список выданных книг");
            int task = scanner.nextInt(); scanner.nextLine();
            switch (task) {
                case 0:
                    repeat = "no";
                    break;
                case 1:
                    System.out.println(" ");
                    System.out.println("Создать пользователя: ");
                    AddUser();
                    break;
                case 2:
                    System.out.println(" ");
                    System.out.println("Добавить книгу: ");
                    AddBook();
                    break;
                case 3:
                    System.out.println(" ");
                    System.out.println("Список книг: ");
                    BookList();
                    break;
                case 4:
                   System.out.println(" ");
                   System.out.println("Выдача книги: ");
                   GivenBook();
                   break;
                case 5:
                    System.out.println(" ");
                    System.out.println("Возвращение книги: ");
                    ReturnBook();
                    break;
                case 6:
                    System.out.println(" ");
                    System.out.println("Список читателей: ");
                    UsersList();
                    break;
                case 7:
                    System.out.println(" ");
                    System.out.println("Список выданных книг: ");
                    printListGivenBooks();
                    break;
                    
                default:
                    System.out.println(" ");
                    System.out.println("Выберите номер из списка!");
            }
        }while("yes".equals(repeat));
        System.out.println(" ");
        System.out.println("Пока!");
        
        
        
    }
    private boolean printListGivenBooks() {
        System.out.println(" ");
        System.out.println("Список выданных книг");
        int n = 0;
        for (int i = 0; i < histories.size(); i++) {
            if(histories.get(i) != null 
                               && histories.get(i).getReturnBook() == null
                               && (
                                     histories.get(i).getBook().getCount() 
                                    < histories.get(i).getBook().getQuantity()+1
                                  )
                               ){
                System.out.printf("%d. Книгу \"%s\" читает %s %s%n"
                        ,i+i
                        ,histories.get(i).getBook().getBookName()
                        ,histories.get(i).getUser().getFirstname()
                        ,histories.get(i).getUser().getLastname()
                );
                n++;
            }
        }
        if(n<1) {
            System.out.println(" ");
            System.out.println("Нет выданных книг...");
            return false;
        }
        return true;
    }

    private void AddUser() {
        System.out.println(" ");
                    User user = new User();
                    System.out.println("Введите имя читателя: ");
                    user.setFirstname(scanner.nextLine());
                    System.out.println("Введите фамилию читателя: ");
                    user.setLastname(scanner.nextLine());
                    System.out.println("Введите телефон читателя: ");
                    user.setPhone(scanner.nextLine());
                    System.out.println("Читатель инициализирован: "+user.toString());
                    users.add(user);
                    saverToFiles.saveUsers(users);
    }

    private void AddBook() {
        System.out.println(" ");
                    Book book = new Book();
                    System.out.print("Введите название книги: ");
                    book.setBookName(scanner.nextLine());
                    System.out.print("Введите год публикации книги: ");
                    book.setReleaseYear(scanner.nextInt()); scanner.nextLine();
                    System.out.print("Введите количество экземпляров книги: ");
                    book.setQuantity(scanner.nextInt()); scanner.nextLine();
                    book.setCount(book.getQuantity());
                    System.out.println("Автор книги: ");
                    System.out.print("Введите количество авторов: ");
                    int countAutors = scanner.nextInt(); scanner.nextLine();
                    Author[] authors = new Author[countAutors];
                    for (int i = 0; i < authors.length; i++) {
                        Author author = new Author();
                        System.out.print("Введите имя автора "+(i+1)+": ");
                        author.setFirstName(scanner.nextLine());
                        System.out.print("Введите фамилию автора: ");
                        author.setLastName(scanner.nextLine());
                        System.out.print("Введите год рождения автора: ");
                        author.setBirthYear(scanner.nextInt());scanner.nextLine();
                        authors[i] = author;
                    }
                    book.setAuthors(authors);
                    System.out.println("Введите год издания книги: ");
                    book.setReleaseYear(scanner.nextInt());scanner.nextLine();
                    System.out.println("Книга инициирована: "+book.toString());
                    books.add(book);
                    saverToFiles.saveBooks(books);
    }

    private void BookList() {
        for (int i = 0; i < books.size(); i++) {
                        if(books.get(i) != null 
                    && books.get(i).getCount() > 0
                    && books.get(i).getCount() < books.get(i).getQuantity() + 1){
                            System.out.printf("%n. %s. %s %s. %d%n"
                                ,i+1
                                ,books.get(i).getBookName()
                                ,Arrays.toString(books.get(i).getAuthors())
                                ,books.get(i).getReleaseYear()
                            );  
                        }
                    }
    }

    private void GivenBook() {
        System.out.println("Список книг: ");
                    int n = 0;
                   for (int i = 0; i < books.size(); i++) {
                       if(books.get(i) != null && books.get(i).getCount() > 0){
                           System.out.printf("%d. %s. %s. %d.%n"
                                   ,i+1
                                   ,books.get(i).getBookName()
                                   ,Arrays.toString(books.get(i).getAuthors())
                                   ,books.get(i).getReleaseYear()
                           );
                           n++;
                      }
                   }
                   if(n < 1){
                   System.out.println("Нет книг для чтения");
                   return;
       }
                   System.out.println("Выберите номер книги: ");
                   int numberBook = scanner.nextInt(); scanner.nextLine();
                   
                   System.out.println("Список читателей: ");
                   for (int i = 0; i < users.size(); i++) {
                      if(users.get(i) != null){
                           System.out.printf("%d. ИМЯ:  %s; ФАМИЛИЯ:  %s; ТЕЛЕФОН: %s.%n"
                                   ,i+1
                                   ,users.get(i).getFirstname()
                                   ,users.get(i).getLastname()
                                   ,users.get(i).getPhone()
                           );
                      }
                   }
                   System.out.println("Выберите номер читателя: ");
                   int numberReader = scanner.nextInt(); scanner.nextLine();
                   
                   History history = new History();
                   history.setBook(books.get(numberBook - 1));
                   history.setUser(users.get(numberReader - 1));
                   Calendar c = new GregorianCalendar();
                   history.setGivenBook(c.getTime());
                   histories.add(history);
                   history.getBook().setCount(history.getBook().getCount() - 1);
                   saverToFiles.saveBooks(books);
                   saverToFiles.saveHistories(histories);
                   System.out.println("--------------------");
    }

    private void ReturnBook() {
        System.out.println("Список выданных книг");
                    
                    for (int i = 0; i < histories.size(); i++) {
                        if(histories.get(i) != null && histories.get(i).getReturnBook() == null) {
                            System.out.printf("%d. Книгу \"%s\" читает %s %s%n"
                                    ,i+i
                                    ,histories.get(i).getBook().getBookName()
                                    ,histories.get(i).getUser().getFirstname()
                                    ,histories.get(i).getUser().getLastname()
                            );
                        }
                    }
                    System.out.print("Выберите номер возвращаемой книги: ");
                    int numberHistory = scanner.nextInt(); scanner.nextLine();
                    Calendar c = new GregorianCalendar();
                    histories.get(numberHistory - 1).setReturnBook(c.getTime());
                    histories.get(numberHistory - 1).getBook().setCount(
                    histories.get(numberHistory - 1)
                             .getBook()
                             .getCount() + 1
                    );
                    saverToFiles.saveHistories(histories);
    }

    private void UsersList() {
        for (int i = 0; i < users.size(); i++) {
                        if(users.get(i) != null){
                            System.out.printf("%d. ИМЯ:  %s; ФАМИЛИЯ:  %s; ТЕЛЕФОН: %s.%n"
                                    ,i+1
                                    ,users.get(i).getFirstname()
                                    ,users.get(i).getLastname()
                                    ,users.get(i).getPhone()
                            );
                        }    
                    }
    }
    
}