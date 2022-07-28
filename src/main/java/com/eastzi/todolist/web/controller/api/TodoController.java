package com.eastzi.todolist.web.controller.api;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eastzi.todolist.service.todo.TodoService;
import com.eastzi.todolist.web.dto.CMRespDto;
import com.eastzi.todolist.web.dto.todo.CreateTodoReqDto;
import com.eastzi.todolist.web.dto.todo.TodoListRespDto;
import com.eastzi.todolist.web.dto.todo.UpdateTodoReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todolist")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@GetMapping("/list/{type}")
	public ResponseEntity<?> getTodoList(@PathVariable String type, @RequestParam int page, @RequestParam int contentCount){
		List<TodoListRespDto> list = null;
		try {
			list = todoService.getTodoList(type, page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, page + "page list on load failed", list));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, page + "page list success load", list));
	}
//	@GetMapping("/list")
//	public ResponseEntity<?> getTodoList(@RequestParam int page, @RequestParam int contentCount){
//		List<TodoListRespDto> list = null;
//		try {
//			list = todoService.getTodoList(page, contentCount);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, page + "page list on load failed", list));
//		}
//		return ResponseEntity.ok().body(new CMRespDto<>(1, page + "page list success load", list));
//	}
	
//	@GetMapping("/list/importance")
//	public ResponseEntity<?> getImportanceTodoList(@RequestParam int page, @RequestParam int contentCount) {
//		List<TodoListRespDto> list = null;
//		try {
//			list = todoService.getImportanceTodoList(page, contentCount);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, page + "page list on load failed", list));
//		}
//		return ResponseEntity.ok().body(new CMRespDto<>(1, page + "page list success load", list));
//	}
	
	
	@PostMapping("/todo")
	public ResponseEntity<?> addTodo(@RequestBody CreateTodoReqDto createTodoReqDto) { //json만 @RequestBody를 붙여야만 데이터가 들어옴
		try {
			if(!todoService.createTodo(createTodoReqDto)) {//IOC에 등록된 것을 DI
				throw new RuntimeException("DataBase Error"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Adding todo failed", createTodoReqDto));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", createTodoReqDto));
	}
	
	@PutMapping("/complete/todo/{todoCode}")
	public ResponseEntity<?> setCompleteTodo(@PathVariable int todoCode) {
		boolean status = false;
		try {
			status = todoService.updateTodoComplete(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@PutMapping("/Importance/todo/{todoCode}")
	public ResponseEntity<?> setImportanceTodo(@PathVariable int todoCode) {
		boolean status = false;
		try {
			status = todoService.updateTodoImportance(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@PutMapping("/todo/{todoCode}")
	public ResponseEntity<?> setTodo(@PathVariable int todoCode, @RequestBody UpdateTodoReqDto updateTodoReqDto) {
		boolean status = false;
		try {
			
			updateTodoReqDto.setTodoCode(todoCode);
			
			status = todoService.updateTodo(updateTodoReqDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@DeleteMapping("/todo/{todoCode}")
	public ResponseEntity<?> removeTodo(@PathVariable int todoCode) {
		boolean status = false;
		try {
			
			status = todoService.removeTodo(todoCode);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
}
