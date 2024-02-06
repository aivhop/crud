package com.aivhop.crud.crudapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "The item name must not be empty")
    @Size(min = 2, max = 30, message = "The item name must be between 2 and 30 long")
    @Column(name = "item_name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;
    //todo время покупки?

    public Item() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name=" + name +
                ", owner=" + owner.getUserName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
