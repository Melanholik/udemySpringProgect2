package springProject.DTO;

import springProject.models.Book;

import java.util.Date;

public class ResponseBookDAO {
    private int id;

    private String name;

    private String author;

    private int releaseYear;

    boolean isOverdue;

    public ResponseBookDAO() {
    }

    public ResponseBookDAO(int id, String name, String author, int releaseYear, boolean isOverdue) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.releaseYear = releaseYear;
        this.isOverdue = isOverdue;
    }

    public static ResponseBookDAO convertBookToResponseBookDAO(Book book) {
        ResponseBookDAO responseBookDAO = new ResponseBookDAO();
        responseBookDAO.id = book.getId();
        responseBookDAO.name = book.getName();
        responseBookDAO.author = book.getAuthor();
        responseBookDAO.releaseYear = book.getReleaseYear();
        responseBookDAO.isOverdue = isDateOlderThan10Days(book.getTakeTame());
        return responseBookDAO;
    }

    private static boolean isDateOlderThan10Days(Date date) {
        Date currentDate = new Date();
        long differenceInMilliseconds = currentDate.getTime() - date.getTime();
        long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);
        return differenceInDays > 10;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }
}
