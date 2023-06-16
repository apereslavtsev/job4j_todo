package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "todo_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String login;

    private String password;

}
