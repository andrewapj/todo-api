package com.github.andrewapj.todoapi.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Entity representing a TodoList within the system.
 */
@Data
@Entity
@Table(name = "TODOLIST")
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"id"})
public class TodoList {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "TODO_LIST_ID")
    private List<Todo> items = new ArrayList<>();

    /**
     * Adds a todo to this todo list.
     *
     * @param todo  the todo item
     * @return      a list of the todos attached to this todo list.
     */
    public List<Todo> addItem(final Todo todo) {
        items.add(todo);
        return items;
    }
}
