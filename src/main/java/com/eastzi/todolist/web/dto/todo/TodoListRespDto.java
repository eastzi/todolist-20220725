package com.eastzi.todolist.web.dto.todo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TodoListRespDto {
	private int todo_code;
	private String todo;
	private int todoComplete;
	private boolean importance;
	private int totalCount;
	private int incompleteCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}