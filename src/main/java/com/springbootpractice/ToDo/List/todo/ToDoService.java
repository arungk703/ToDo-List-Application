package com.springbootpractice.ToDo.List.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class ToDoService {
	
	// Stores all todos.
	private static List<ToDo> todos = new ArrayList<>();
	public static int count=0;
	
	// Adding static todos
	static {
		todos.add(new ToDo(++count, "Arun", "AWS", LocalDate.now().plusYears(1), false));
		todos.add(new ToDo(++count, "Arun", "GCP", LocalDate.now().plusYears(2), false));
		todos.add(new ToDo(++count, "Arun", "Full Stack Development", LocalDate.now().plusYears(3), false));
	}
	
	// Retriving all todos
	public List<ToDo> findByUserName(String userName){
		
		Predicate<? super ToDo> predicate = todo -> todo.getUserName().equalsIgnoreCase(userName);
		
		return todos.stream().filter(predicate).toList();
	}
	
	// Adding dynamic todo to todos list.
	public void addTodo(String userName,String description,LocalDate targetDate, boolean done) {
		
		ToDo todo = new ToDo(++count,userName,description,targetDate,done);
		todos.add(todo);
		
	}
	
	//Deleting todo
	public void deleteById(int id) {
		Predicate<? super ToDo> predicate = todo -> todo.getId() == id;
		todos.removeIf(predicate);
	}

	public ToDo findById(int id) {
		Predicate<? super ToDo> predicate = todo -> todo.getId() == id;
		ToDo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodo(@Valid ToDo todo) {
		
		deleteById(todo.getId());
		todos.add(todo);
		
	}

}
