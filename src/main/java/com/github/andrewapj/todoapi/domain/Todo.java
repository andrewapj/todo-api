package com.github.andrewapj.todoapi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Entity representing a Todo within the system.
 */
@Data
@Entity
@Table(name = "TODO")
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"id"})
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private boolean completed;

    /**
     * Merges in the supplied {@link Todo} with this one.
     * @param newTodo   the todo to merge in.
     * @return          this modified todo.
     */
    public Todo merge(final Todo newTodo) {
        return this
            .setText(newTodo.getText())
            .setCompleted(newTodo.isCompleted());
    }
}
