public class Livros {
    private String author;
    private String name;
    private String genre;
    private int copies;

    public Livros() {
      
    }

    public Livros(String author, String name, String genre, int copies) {
        this.author = author;
        this.name = name;
        this.genre = genre;
        this.copies = copies;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}