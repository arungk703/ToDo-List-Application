package com.springbootpractice.ToDo.List.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("name")
public class ToDoController {

	public ToDoController(ToDoService todoService) {
		super();
		this.todoService = todoService;
	}

	private ToDoService todoService;

	// It will show all todos.
	@RequestMapping("list-todo")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername(model);
		List<ToDo> todos = todoService.findByUserName(username);
		model.put("todos", todos);

		return "listTodos";
	}

	
	// Shows New ToDo page.
	@RequestMapping(value = "add-todo", method = RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String username = (String) model.get("name");
		ToDo todo = new ToDo(0, username, "", LocalDate.now(), false);
		model.addAttribute("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid ToDo todo, BindingResult result) {
		if (result.hasErrors()) {
			return showNewTodoPage(model);
		}
		String username = (String) model.get("name");
		todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), false);
		return "redirect:list-todo";
	}

	// Deleting Todo
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoService.deleteById(id);

		return "redirect:list-todo";
	}

	@RequestMapping(value = "update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		ToDo todo = todoService.findById(id);
		model.addAttribute("todo", todo);

		return "todo";
	}

	@RequestMapping(value = "update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid ToDo todo, BindingResult result) {
		if (result.hasErrors()) {
			return showNewTodoPage(model);
		}
		String username = (String) model.get("name");
		todo.setUserName(username);
		todoService.updateTodo(todo);
		return "redirect:list-todo";
	}
	
	private String getLoggedInUsername(ModelMap model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication.getName();
	}


}
