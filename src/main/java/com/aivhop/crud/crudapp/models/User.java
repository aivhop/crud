package com.aivhop.crud.crudapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_entity")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Your username can't be empty")
    @Size(max = 50, message = "Your username must not be longer than 50 characters")
    @Column(name = "username")
    private String userName;//todo validate(without control symbols for ex
    @NotEmpty(message = "Your email can't be empty")
    @Size(max = 70, message = "Your email must not be longer than 70 characters")
    @Email(message = "Your email must be correct")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "Your full name can't be empty")
    @Size(max = 100, message = "Your full name must not be longer than 70 characters")
    @Column(name = "full_name")
    private String fullName;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
    ;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<>();

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roles=" + roles +
                ", items=" + items +
                '}';
    }


}
