package taskprime.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import taskprime.model.Task;
import taskprime.model.TaskEnum;
import taskprime.model.User;
import taskprime.model.UserEnum;
import taskprime.service.GroupService;
import taskprime.service.TaskService;

@Controller
public class UserController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private GroupService groupService;
	
	@GetMapping("/user")
	public String homeUser(@RequestParam int groupTask, HttpServletRequest r){
		User u = (User) r.getSession().getAttribute("user");
		
		if(groupTask > 0){
			r.setAttribute("NameGroup", groupService.findOne(groupTask).getName());
			r.setAttribute("hashMapTasks", taskProcessamento(r, 1, groupTask));
		}else{
			r.setAttribute("NameGroup", "Tarefas");
			r.setAttribute("hashMapTasks", taskProcessamento(r, 0, 0));
		}
		
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("mode", UserEnum.HOME);
		return "user";
	}
	
	@GetMapping("/nova-tarefa")
	public String newTask(HttpServletRequest r){
		User u = (User) r.getSession().getAttribute("user");
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("mode", UserEnum.NEW_TASK);
		return "user";
	}
	
	@GetMapping("/alterar-tarefa")
	public String updateReminder(@RequestParam int id, HttpServletRequest r){
		User u = (User) r.getSession().getAttribute("user");
		r.setAttribute("task", taskService.findOne(id));
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("mode", UserEnum.UPDATE_TASK);
		return "user";
	}
	
	@PostMapping("/salvar-tarefa")
	public String saveTask(@RequestParam String dateFinished, @ModelAttribute Task task, BindingResult bindingResult, HttpServletRequest r){
		User u = (User) r.getSession().getAttribute("user");
		task.setDateFinished(LocalDate.parse(dateFinished));
		task.setIdUser(u.getId());
		
		taskService.save(task);
		
		r.setAttribute("NameGroup", "Tarefas");
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("hashMapTasks", taskProcessamento(r, 0, 0));
		r.setAttribute("mode", UserEnum.HOME);
		return "user";
	}
	
	@GetMapping("/concluir-tarefa")
	public String finishedTask(@RequestParam int id, HttpServletRequest r){
		Task task = taskService.findOne(id);
		
		task.setFinished(true);
		taskService.save(task);
		
		User u = (User) r.getSession().getAttribute("user");
		r.setAttribute("NameGroup", "Tarefas");
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("hashMapTasks", taskProcessamento(r, 0, 0));
		r.setAttribute("mode", UserEnum.HOME);
		return "user";
	}
	
	@PostMapping("/deletar-tarefa")
	public String deleteTask(@RequestParam int id, HttpServletRequest r){
		taskService.delete(id);
		User u = (User) r.getSession().getAttribute("user");
		r.setAttribute("NameGroup", "Tarefas");
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("hashMapTasks", taskProcessamento(r, 0, 0));
		r.setAttribute("mode", UserEnum.HOME);
		return "user";
	}
	
	@GetMapping("/tarefas-concluidas")
	public String concludedTask(HttpServletRequest r){
		User u = (User) r.getSession().getAttribute("user");
		
		r.setAttribute("NameGroup", "Concluídas");
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("tasks", taskService.findByIdUser(u.getId(),true));
		r.setAttribute("concluded", UserEnum.CONCLUDED_TASK);
		r.setAttribute("mode", UserEnum.HOME);
		return "user";
	}
	
	/**
	 * @param r, request...
	 * @param type, se 0 == usuario ou 1 == grupo
	 * @param id, caso seja por grupo, envia o id fo grupo
	 * @param finished, false == tarefa não concluida ou true == tarefa concluida 
	 * @return retorna um hashMap mapeado pela TaskEnum
	 */
	public HashMap<TaskEnum, List<Task>> taskProcessamento(HttpServletRequest r, int type, int id){
		HashMap<TaskEnum, List<Task>> hashMapFinal = new HashMap<>();
		User u = (User) r.getSession().getAttribute("user");
		List<Task> tasks;
		//type == 0 -> encontrar por usuário
		//type == 1 -> encontrar por grupo
		if(type == 0){
			tasks = taskService.findByIdUser(u.getId(), false);
		}else{
			 tasks = taskService.findByGroupTask(id, false);
		}
		
		List<Task> tasksAtrasada = new ArrayList<>(); 
		List<Task> tasksHoje = new ArrayList<>(); 
		List<Task> tasksFaltaMais = new ArrayList<>(); 
		LocalDate today = LocalDate.now();
		Period p;
		
		for(Task task : tasks){
			p = Period.between(today, task.getDateFinished());
			if(p.isNegative()){
				tasksAtrasada.add(task);
			}else if(p.isZero()){
				tasksHoje.add(task);
			}else 
				tasksFaltaMais.add(task);
		}
		
		hashMapFinal.put(TaskEnum.ATRASADA, tasksAtrasada);
		hashMapFinal.put(TaskEnum.HOJE, tasksHoje);
		hashMapFinal.put(TaskEnum.FALTA_MAIS, tasksFaltaMais);
		
		return hashMapFinal;
	}
	
}
