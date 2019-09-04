package com.github.andrewapj.todoapi.service.impl;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.factory.TodoListFactory;
import com.github.andrewapj.todoapi.infrastructure.TodoListRepository;
import com.github.andrewapj.todoapi.service.TodoListPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoListPersistenceServiceImpl implements TodoListPersistenceService {

    private final TodoListRepository repository;

    @Override
    public TodoList create() {
        return repository.save(TodoListFactory.getNewTodoList(null));
    }
}
