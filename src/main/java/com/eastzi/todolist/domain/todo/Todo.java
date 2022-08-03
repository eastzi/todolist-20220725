package com.eastzi.todolist.domain.todo;

import java.time.LocalDateTime;

import com.eastzi.todolist.web.dto.todo.TodoListRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo { //Entity 생성 목적 class, db의 컬럼명과 동일하게 생성
	private int todo_code;
	private String todo_content;
	private int todo_complete; //실행 완료 했을때 체크할 부분
	private int importance_flag;
	private int total_count;
	private int incomplete_count;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	
	public TodoListRespDto toListDto() {
		return TodoListRespDto.builder()
				.todoCode(todo_code)
				.todo(todo_content)
				.todoComplete(todo_complete == 1)
				.importance(importance_flag == 1)
				.totalCount(total_count)
				.incompleteCount(incomplete_count)
				.createDate(create_date)
				.updateDate(update_date)
				.build();
	}
}
