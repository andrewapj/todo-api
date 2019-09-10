package com.github.andrewapj.todoapi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Entity to represent a TodoList within the system.
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Accessors(chain = true)
public class TodoList {

    @Id
    @GeneratedValue
    private Long id;
}
