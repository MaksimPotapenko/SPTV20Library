/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myclasses;

import entity.Author;
import entity.Book;
import entity.History;
import facade.AuthorFacade;
import facade.BookFacade;
import facade.HistoryFacade;
import facade.UserFacade;
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
    private UserFacade userFacade;
    private BookFacade bookFacade;
    private AuthorFacade authorFacade;
    private HistoryFacade historyFacade;
//    private List<Book> books = new ArrayList<>();
//    private List<Author> authors = new ArrayList<>();
//    private List<User> users = new ArrayList<>();
//    private List<History> histories = new ArrayList<>();
 //   private Keeping keeper;
    
    public App(){
//        if(toFile){
//            keeper = new SaverToFiles();
//        }else{
//            keeper = new SaverToBase();
//        }
//        books = keeper.loadBooks();
//        authors = keeper.loadAuthors();
//        users = keeper.loadUsers();
//        histories = keeper.loadHistories();
        userFacade = new UserFacade(User.class);
        bookFacade = new BookFacade(Book.class);
        authorFacade = new AuthorFacade(Author.class);
        historyFacade = new HistoryFacade(History.class);
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
            System.out.println("10 - поменять книгу");
            System.out.println("11 - поменять читателя");
            System.out.println("12 - поменять автора");
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
                    
                case 10:
                    System.out.println(" ");
                    System.out.println("Поменять книгу: ");
                    changeBook();
                    break;
                    
                case 11:
                    System.out.println(" ");
                    System.out.println("Поменять читателя: ");
                    changeUser();
                    break;
                    
                case 12:
                    System.out.println(" ");
                    System.out.println("Поменять автора: ");
                    changeAuthor();
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
        List<History> histories = historyFacade.findListGiverBooks();
        for (int i = 0; i < histories.size(); i++) {
                System.out.printf("%d. Книгу \"%s\" читает %s %s. Срок возврата: %s%n"
                       ,histories.get(i).getId()
                       ,histories.get(i).getBook().getBookName()
                       ,histories.get(i).getUser().getFirstname()
                       ,histories.get(i).getUser().getLastname()
                       ,showReturnDateBook(histories.get(i).getBook())
                );
                setNumbersGivenBooks.add(histories.get(i).getId().intValue());
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
                    userFacade.create(user);
//                    users.add(user);
//                    keeper.saveUsers(users);
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
                        authorsBook.add(authorFacade.find((long)insertNumber(setNumbersAuthors)));
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
                    bookFacade.create(book);
//                    books.add(book);
//                    keeper.saveBooks(books);
    }

    private Set<Integer> BookList() {
        Set<Integer> setNumbersBooks = new HashSet<>();
        List<Book> books = bookFacade.findAll();
        System.out.println("Список книг: ");
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i) != null 
                    && books.get(i).getCount() > 0
                    && books.get(i).getCount() < books.get(i).getQuantity() + 1){
                System.out.printf("%d. %s. %s. %d. В наличии: %d%n"
                       ,books.get(i).getId()
                       ,books.get(i).getBookName()
                       ,Arrays.toString(books.get(i).getAuthors().toArray())
                       ,books.get(i).getReleaseYear()
                       ,books.get(i).getCount()
                );
                setNumbersBooks.add(books.get(i).getId().intValue());
           }else if(books.get(i) != null){
                System.out.printf("%d. %s. %s. %d. - все экземпляры на руках до: %s%n"
                       ,books.get(i).getId()
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
        History history = historyFacade.findHistoryByBook(book);
        if(history == null){
            return "";
        }
        givenDate= history.getGivenBook().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
                    Book book = bookFacade.find((long)numberBook);
                    history.setBook(book);
                    history.setUser(userFacade.find((long)numberReader));
                    Calendar c = new GregorianCalendar();
                    history.setGivenBook(c.getTime());
                    book.setCount(book.getCount() - 1);
                    bookFacade.edit(book);
                    historyFacade.edit(history);
                    System.out.println("--------------------");
    }

    private void ReturnBook() {
        Set<Integer> setNumbersHistories = printListGivenBooks();
        if(setNumbersHistories.isEmpty()){
            System.out.println("Нет выданных книг.");
           return;
        }
        System.out.print("Выберите номер возвращаемой книги: ");
        int numberHistory=insertNumber(setNumbersHistories);
        History history = historyFacade.find((long)numberHistory);
        Calendar c = new GregorianCalendar();
        history.setReturnBook(c.getTime());
        Book book = bookFacade.find(history.getBook().getId());
        book.setCount(book.getCount() + 1);
        bookFacade.edit(book);
        historyFacade.edit(history);
    }

    private Set<Integer> UsersList() {
        System.out.println("Список читателей");
        Set<Integer> setNumbersUsers = new HashSet<>();
        List<User> users = userFacade.findAll();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i) != null){
                System.out.printf("%d. %s %s. тел.: %s%n"
                       ,users.get(i).getId()
                       ,users.get(i).getFirstname()
                       ,users.get(i).getLastname()
                       ,users.get(i).getPhone()
                );
                setNumbersUsers.add(users.get(i).getId().intValue());
            }
        }
        if(setNumbersUsers.isEmpty()){
            System.out.println("Список читателей пуст.");
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
        Set<Integer> setNumbersAuthors = new HashSet<>();
        List<Author> authors = authorFacade.findAll();
        for (int i = 0; i < authors.size(); i++) {
            if(authors.get(i) != null){
                System.out.printf("%d. %s %s. %d%n"
                       ,authors.get(i).getId()
                       ,authors.get(i).getFirstName()
                       ,authors.get(i).getLastName()
                       ,authors.get(i).getBirthYear()
                );
                setNumbersAuthors.add(authors.get(i).getId().intValue());
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
        authorFacade.create(author);
//        keeper.saveAuthors(authors);
//        authors.add(author);
    }

    private void changeBook() {
        Set<Integer> changeNumber = new HashSet<>();
        changeNumber.add(1);
        changeNumber.add(2);
        Set<Integer> setNumbersBooks = BookList();
        if(setNumbersBooks.isEmpty()){
            System.out.println("В базе нет книг");
            return;
        }
        System.out.println("Выберите номер книги: ");
        int numberBook = insertNumber(setNumbersBooks);
        Book book = bookFacade.find((long) numberBook);
        System.out.println("Название книги: "+book.getBookName());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        int change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новое название книги: ");
            book.setBookName(scanner.nextLine());
        }
        System.out.println("Год издания книги: "+book.getReleaseYear());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новый год издания книги: ");
            book.setReleaseYear(getNumber());
        }
        System.out.println("Количество экземпляров книги: "+book.getQuantity());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новое количество экземпляров книги: ");
            int newQuantity = getNumber();
            int oldQuantity = book.getQuantity();
            int oldCount = book.getCount();
            int newCount = oldCount + (newQuantity - oldQuantity);
            book.setQuantity(newQuantity);
            book.setCount(newCount);
        }
        Set<Integer> setNumbersAuthors = printListAuthors();
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите количество авторов: ");
            int countAuthors = getNumber();
            book.getAuthors().clear();
            for (int i = 0; i < countAuthors; i++) {
                System.out.println("Введите номер "+(i+1)+" автора из списка: ");
                int numberAuthor = insertNumber(setNumbersAuthors);
                book.getAuthors().add(authorFacade.find((long)numberAuthor));
            }
        }
        bookFacade.edit(book);
    }

    private void changeUser() {
        Set<Integer> changeNumber = new HashSet<>();
        changeNumber.add(1);
        changeNumber.add(2);
        Set<Integer> setNumbersReaders = UsersList();
        if(setNumbersReaders.isEmpty()){
            return;
        }
        System.out.println("Выберите номер читателя: ");
        int numberReader = insertNumber(setNumbersReaders);
        User user = userFacade.find((long)numberReader);
        System.out.println("Имя читателя: "+user.getFirstname());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        int change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новое имя читателя: ");
            user.setFirstname(scanner.nextLine());
        }
        System.out.println("Фамилия читателя: "+user.getLastname());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новую фамилию читателя: ");
            user.setLastname(scanner.nextLine());
        }
        System.out.println("Телефон читателя: "+user.getPhone());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новый телефон читателя: ");
            user.setPhone(scanner.nextLine());
        }
        userFacade.edit(user);
    }

    private void changeAuthor() {
        Set<Integer> changeNumber = new HashSet<>();
        changeNumber.add(1);
        changeNumber.add(2);
        Set<Integer> setNumbersAuthors = printListAuthors();
        if(setNumbersAuthors.isEmpty()){
            return;
        }
        System.out.println("Выберите номер автора: ");
        int numberAuthor = insertNumber(setNumbersAuthors);
        Author author = authorFacade.find((long) numberAuthor);
        System.out.println("Имя автора: "+author.getFirstName());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        int change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новое имя автора: ");
            author.setFirstName(scanner.nextLine());
        }
        System.out.println("Фамилия автора: "+author.getLastName());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новую фамилию читателя: ");
            author.setLastName(scanner.nextLine());
        }
        System.out.println("Год рождения автора: "+author.getBirthYear());
        System.out.println("Для изменения введите 1, чтобы пропустить нажмите 2");
        change = insertNumber(changeNumber);
        if(1 == change){
            System.out.println("Введите новый год рождения автора: ");
            author.setBirthYear(getNumber());
        }
        authorFacade.edit(author);
    }
}