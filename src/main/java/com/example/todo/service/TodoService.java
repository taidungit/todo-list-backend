package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo handleCreate(Todo todo){
       return this.todoRepository.save(todo);
    }
    public List<Todo> handleGetTodo(){
        return this.todoRepository.findAll();
    }

    public void handleUpdateTodo(Long id,Todo input){
     
        Optional<Todo>todoOptional = this.todoRepository.findById(id);
        if(todoOptional.isPresent()){
            Todo currentTodo=todoOptional.get();
            currentTodo.setUsername(input.getUsername());
            currentTodo.setCompleted(input.isCompleted());
            this.todoRepository.save(currentTodo);
        }
    }

    @Transactional
    public void handleDeleteTodo(Long id){
       
        // Optional<Todo>todoOptional = this.todoRepository.findByUsername("dungmount3");
        // if(todoOptional.isPresent()){
        //     Todo currentTodo=todoOptional.get();
       
        //     this.todoRepository.deleteByUsername(currentTodo.getUsername());

        // }
        this.todoRepository.deleteById(id);
    }

   public Todo getTodoById(Long id){
        Optional<Todo>optional= this.todoRepository.findById(id);
        return optional.isPresent()? optional.get():null;
    }
}
