package com.Sneekes.backend.dto;
import com.Sneekes.backend.entity.User;

public class UserDto {
    private String id;
    private String username;
    private String email;

    public UserDto (User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

