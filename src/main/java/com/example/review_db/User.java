package com.example.review_db;

public class User {

    private Long id;
    private String username;
    private String email;
    private Byte age;

    public User(Long id, String username, String email, Byte age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }
}
