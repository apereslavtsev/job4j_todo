package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.TimeZone;

import javax.persistence.*;

@Data
@Entity
@Table(name = "todo_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String login;

    private String password;

    @Column(name = "user_zone")
    private String timezone = TimeZone.getDefault().getID();

    public String getTimezone() {
        if (this.timezone == null
                || this.timezone.isEmpty()) {
            return TimeZone.getDefault().getID();
        }
        return this.timezone;
    }
}
