package com.springbootpractice.ToDo.List.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<ToDo, Integer> {

	List<ToDo> findByUserName(String username);

}
