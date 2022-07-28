package com.eastzi.todolist.web.dto.todo;

import com.eastzi.todolist.domain.todo.Todo;

import lombok.Data;

@Data
public class CreateTodoReqDto {
	private boolean importance;
	private String todo;
	
	public Todo toEntity() { //dto에서 전달받은 데이터를 entity로 전달
		return Todo.builder()
				.importance_flag(importance ? 1 : 0)
				.todo_content(todo)
				.build();
	}
}
