package com.eastzi.todolist.service.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eastzi.todolist.domain.todo.Todo;
import com.eastzi.todolist.domain.todo.TodoRepository;
import com.eastzi.todolist.web.dto.todo.CreateTodoReqDto;
import com.eastzi.todolist.web.dto.todo.TodoListRespDto;
import com.eastzi.todolist.web.dto.todo.UpdateTodoReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepository;
	
	@Override //추가
	public boolean createTodo(CreateTodoReqDto createTodoReqDto) throws Exception { //@service 로 IOC에 등록 -> controller에서 DI
//		System.out.println(createTodoReqDto);
		
		//강제 예외 발생시키기(순서 11번) 
//		if(1 == 1) {			
//			throw new RuntimeException();
//		}
		Todo todoEntity = createTodoReqDto.toEntity(); //dto를 entity로 
		
//		String content = todoEntity.getTodo_content();
//		for(int  i = 100; i < 200; i++) {
//			todoEntity.setTodo_content(content + "_" + (i + 1));
//			if(i % 2 == 0) {
//				todoEntity.setImportance_flag(1);
//			}else {
//				todoEntity.setImportance_flag(0);
//			}
//			todoRepository.save(todoEntity);
//		}
//		return true;
		return todoRepository.save(todoEntity) > 0; //entity를 save로 
	}
	
//	@Override
//	public List<TodoListRespDto> getImportanceTodoList(int page, int contentCount) throws Exception {
////		Map<String, Object> map = new HashMap<String, Object>();
////		map.put("index", (page -1) * contentCount);
////		map.put("count", contentCount); 
//		
////		=> createGetTodoListMap(page, contentCount)
//		
//		List<Todo> todoList = todoRepository.getImportanceTodoListOfIndex(createGetTodoListMap(page, contentCount));
//		
////		List<TodoListRespDto> todoListRespDtos = new ArrayList<TodoListRespDto>();
////		todoList.forEach(todo -> {
////			todoListRespDtos.add(todo.toListDto());
////		});
////		return todoListRespDtos;
//		
////		=> createTodoListRespDtos(todoList)
//		
//		return createTodoListRespDtos(todoList);
//	}

	@Override //조회
	public List<TodoListRespDto> getTodoList(String type, int page, int contentCount) throws Exception {

		List<Todo> todoList = todoRepository.getTodoList(createGetTodoListMap(type, page, contentCount)); //list를 가져옴 
		
		return createTodoListRespDtos(todoList);
	}
	
//	코드 중복코드 정리 
	private Map<String, Object> createGetTodoListMap(String type, int page, int contentCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("index", (page -1) * contentCount);
		map.put("count", contentCount);
		return map;
	}
	
	private List<TodoListRespDto> createTodoListRespDtos(List<Todo> todoList) {
		List<TodoListRespDto> todoListRespDtos = new ArrayList<TodoListRespDto>();
		
		todoList.forEach(todo -> {
			todoListRespDtos.add(todo.toListDto());
		});
		
		return todoListRespDtos;
	}
	
	//수정
	@Override
	public boolean updateTodoComplete(int todoCode) throws Exception {
		return todoRepository.updateTodoComplete(todoCode) > 0;
	}
	
	//수정
	@Override
	public boolean updateTodoImportance(int todoCode) throws Exception {
		return todoRepository.updateTodoImportance(todoCode) > 0;
	}
	
	//수정
	@Override
	public boolean updateTodo(UpdateTodoReqDto updateTodoReqDto) throws Exception {

		return todoRepository.updateTodoByTodoCode(updateTodoReqDto.toEntity()) > 0;
	}
	
	@Override
	public boolean removeTodo(int todoCode) throws Exception {
		return todoRepository.remove(todoCode) > 0;
	}

}
