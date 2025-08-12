package com.example.users.users.model;

public record User(Integer id, String name, String occupation) {
    public User(User user) {
        this(user.id, user.name, user.occupation);
    }
}
