package com.example.review_db;


public class Movie {

    private Long id;
    private String title;
    private String director;
    private int released;
    private Long genreId;

    public Movie(Long id, String title, String director, int released, Long genreId) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.released = released;
        this.genreId = genreId;
    }

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleased() {
        return released;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", genreId=" + genreId +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", released=" + released +
                '}';
    }
}
