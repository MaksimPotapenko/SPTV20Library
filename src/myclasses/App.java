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
import interfaces.Keeping;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import tools.SaverToBase;
import tools.SaverToFiles;

/**
 *
 * @author user
 */
public class App {
    public static boolean toFile = false;
    
    private Scanner scanner = new Scanner(System.in);
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
    private Keeping keeper;
    
    public App(){
        if(toFile){
            keeper = new SaverToFiles();
        }else{
            keeper = new SaverToBase();
        }
        books = keeper.loadBooks();
        authors = keeper.loadAuthors();
        users = keeper.loadUsers();
        histories = keeper.loadHistories();
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
            System.out.println("8 - список авторов");
            System.out.println("9 - добавить автора");
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
                case 8:
                    System.out.println(" ");
                    System.out.println("Список авторов: ");
                    printListAuthors();
                    break;
                case 9:
                    System.out.println(" ");
                    System.out.println("Добавить автора: ");
                    addAuthor();
                    break;
                    
                default:
                    System.out.println(" ");
                    System.out.println("Выберите номер из списка!");
            }
        }while("yes".equals(repeat));
        System.out.println(" ");
        System.out.println("Пока!");
        
        
        
    }
    private Set<Integer> printListGivenBooks(){
        Set<Integer> setNumbersGivenBooks = new HashSet<>();
        System.out.println("Список выданных книг: ");
        for (int i = 0; i < histories.size(); i++) {
           //печатем книгу если она !null, выдана
            if(histories.get(i) != null && histories.get(i).getReturnBook() == null){
                System.out.printf("%d. Книгу \"%s\" читает %s %s. Срок возврата: %s%n"
                       ,i+1
                       ,histories.get(i).getBook().getBookName()
                       ,histories.get(i).getUser().getFirstname()
                       ,histories.get(i).getUser().getLastname()
                       ,showReturnDateBook(histories.get(i).getBook())
                );
                setNumbersGivenBooks.add(i+1);
            }
        }
        if(setNumbersGivenBooks.isEmpty()){
            System.out.println("Список выданных книг пуст.");
        }
       return setNumbersGivenBooks;
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
                    keeper.saveUsers(users);
    }

    private void AddBook() {
        System.out.println(" ");
                    Book book = new Book();
                    Set<Integer> setNumbersAuthors = printListAuthors();
                    if(setNumbersAuthors.isEmpty()){
                        System.out.println("Нет авторов, добавьте");
                        return;
                    }
                    System.out.println("Если в списке есть автор для книги введите 1: ");
                    if(getNumber() != 1){
                        System.out.println("Добавьте автора");
                        return;
                    }
                    System.out.print("Введите количество авторов книги: ");
                    int countAuthors = getNumber();
                    List<Author> authorsBook = new ArrayList<>(countAuthors);
                    for (int i = 0; i < countAuthors; i++) {
                        Author author = new Author();
                        System.out.println("Введите номер автора "+(i+1)+" из списка");
                        authorsBook.add(authors.get(insertNumber(setNumbersAuthors)-1));
                    }
                    book.setAuthors(authorsBook);
                    System.out.print("Введите название книги: ");
                    book.setBookName(scanner.nextLine());
                    System.out.print("Введите год издания книги: ");
                    book.setReleaseYear(getNumber());
                    System.out.print("Введите количество экземпляров книги: ");
                    book.setQuantity(getNumber());
                    book.setCount(book.getQuantity());
                    System.out.println("Книга инициирована: "+book.toString());    
                    books.add(book);
                    keeper.saveBooks(books);
    }

    private Set<Integer> BookList() {
        Set<Integer> setNumbersBooks = new HashSet<>();
        System.out.println("Список книг: ");
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i) != null 
                    && books.get(i).getCount() > 0
                    && books.get(i).getCount() < books.get(i).getQuantity() + 1){
                System.out.printf("%d. %s. %s. %d. В наличии: %d%n"
                       ,i+1
                       ,books.get(i).getBookName()
                       ,Arrays.toString(books.get(i).getAuthors().toArray())
                       ,books.get(i).getReleaseYear()
                       ,books.get(i).getCount()
                );
                setNumbersBooks.add(i+1);
           }else if(books.get(i) != null){
                System.out.printf("%d. %s. %s. %d. - все экземпляры на руках до: %s%n"
                       ,i+1
                       ,books.get(i).getBookName()
                       ,Arrays.toString(books.get(i).getAuthors().toArray())
                       ,books.get(i).getReleaseYear()
                       ,showReturnDateBook(books.get(i))//"5.10.2021"
               );
           }

       }
        return setNumbersBooks;
    }
    private String showReturnDateBook(Book book){
        LocalDate givenDate = null;
        for (int i = 0; i < histories.size(); i++) {
            if((histories.get(i).getBook().getBookName()).equals(book.getBookName()) && histories.get(i).getReturnBook() == null){
                if(givenDate == null){
                    givenDate= histories.get(i).getGivenBook().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
                if(givenDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli() < histories.get(i).getGivenBook().getTime()){
                    givenDate= histories.get(i).getGivenBook().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
            }
        }
        LocalDate returnDateBook = givenDate.plusDays(14);
        return  returnDateBook.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private void GivenBook() {
                    System.out.println("Выдать книгу: ");
                    Set<Integer> setNumbersBooks = BookList();
                    if(setNumbersBooks.isEmpty()){
                        System.out.println("Нет книг для выдачи");
                       return;
                    }
                    System.out.println("Выберите номер книги: ");
                    int numberBook= insertNumber(setNumbersBooks);

                    Set<Integer> setNumbersReaders = UsersList();
                    System.out.println("Выберите номер читателя: ");
                    if(setNumbersReaders.isEmpty()){
                        System.out.println("Нет читателей");
                       return;
                    }
                    int numberReader = scanner.nextInt(); scanner.nextLine();

                    History history = new History();
                    history.setBook(books.get(numberBook - 1));
                    history.setUser(users.get(numberReader - 1));
                    Calendar c = new GregorianCalendar();
                    history.setGivenBook(c.getTime());
                    histories.add(history);
                    history.getBook().setCount(history.getBook().getCount() - 1);
                    keeper.saveBooks(books);
                    keeper.saveHistories(histories);
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
//                  histories.get(numberHistory - 1).getBook().setCount(
//                  histories.get(numberHistory - 1)
//                           .getBook()
//                           .getCount() + 1
//                    );
                        // Здесь объясняется что значит передача по ссылке в Java
                        // https://coderoad.ru/40480/%D0%AD%D1%82%D0%BE-Java-pass-by-reference-%D0%B8%D0%BB%D0%B8-pass-by-value
                        // Постарайтесь понять
                        for (int i = 0; i < books.size(); i++) {
                            if(books.get(i).getBookName().equals(histories.get(numberHistory - 1).getBook().getBookName())){
                                books.get(i).setCount(books.get(i).getCount() + 1);
                                break;
                            }

                        }
                    keeper.saveBooks(books);
                    keeper.saveHistories(histories);
    }

    private Set<Integer> UsersList() {
        Set<Integer> setNumbersUsers = new HashSet<>();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i) != null){
                System.out.printf("%d. %s %s. тел.: %s%n"
                       ,i+1
                       ,users.get(i).getFirstname()
                       ,users.get(i).getLastname()
                       ,users.get(i).getPhone()
                );
                setNumbersUsers.add(i+1);
            }
        }
        return setNumbersUsers;
    }
    
    private int getNumber() {
        int number;
        do{
           String strNumber = scanner.nextLine();
           try {
               number = Integer.parseInt(strNumber);
               return number;
           } catch (NumberFormatException e) {
               System.out.println("Введено \""+strNumber+"\". Выбирайте номер ");
           }
       }while(true);
    }

    private int insertNumber(Set<Integer> setNumbers) {
        int number=0;
        do{
           number = getNumber();
           if(setNumbers.contains(number)){
               break;
           }
           System.out.println("Попробуй еще раз: ");
       }while(true);
       return number; 
    }
    
    private Set<Integer> printListAuthors() {
        System.out.println("----- Список авторов -----");
        Set<Integer> setNumbersAuthors = new HashSet<>();
        for (int i = 0; i < authors.size(); i++) {
            if(authors.get(i) != null){
                System.out.printf("%d. %s %s. %d%n"
                       ,i+1
                       ,authors.get(i).getFirstName()
                       ,authors.get(i).getLastName()
                       ,authors.get(i).getBirthYear()
                );
                setNumbersAuthors.add(i+1);
            }
        }
        if(setNumbersAuthors.isEmpty()){
            System.out.println("Список авторов пуст");
        }
        return setNumbersAuthors;

    }

    private void addAuthor() {
        Author author = new Author();
        System.out.print("Введите имя автора: ");
        author.setFirstName(scanner.nextLine());
        System.out.print("Введите фамилию автора: ");
        author.setLastName(scanner.nextLine());
        System.out.print("Введите год рождени автора: ");
        author.setBirthYear(getNumber());
        System.out.println("Автор инициирован: "+author.toString());
        keeper.saveAuthors(authors);
        authors.add(author);
    }
}