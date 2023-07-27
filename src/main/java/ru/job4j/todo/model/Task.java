package ru.job4j.todo.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @EqualsAndHashCode.Include
    private String title;

    private String description;

    @EqualsAndHashCode.Include    
    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tasks_categories",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private List<Category> categories;

    public static List<Task> convertTaskListTimeFromTimeZone(List<Task> tasks, String timezone) {
        tasks.stream().forEach(t -> {
            t.convertTaskTimeFromTimezone(timezone);
        });
        return tasks;
    }

    public void convertTaskTimeFromTimezone(String timezone) {
        this.setCreated(
            ZonedDateTime.of(this.getCreated(), ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.of(timezone)).toLocalDateTime()
            );   
    }

}
