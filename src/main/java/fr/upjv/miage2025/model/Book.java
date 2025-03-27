package fr.upjv.miage2025.model;

public class Book {
    private String title ;
    private String author;
    private int nbPages;
    public Book(){}
    public Book(String Title, String Author, int NbPages){
        this.title = Title;
        this.author = Author;
        this.nbPages = NbPages;
    }

    public String getTitle(){
        return this.title;
    }
    public String getAuthor(){
        return this.author;
    }
    public int getNbPages(){
        return this.nbPages;
    }
}
