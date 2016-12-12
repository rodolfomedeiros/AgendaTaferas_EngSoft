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

import taskprime.model.MainEnum;
import taskprime.model.Task;
import taskprime.model.TaskEnum;
import taskprime.model.User;
import taskprime.model.UserEnum;
import taskprime.service.GroupService;
import taskprime.service.MainService;
import taskprime.service.TaskService;

@Controller
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private GroupService groupService;
	
	@GetMapping("/")
	public String home(HttpServletRequest r){
		r.setAttribute("mode", MainEnum.LOGIN);
		return "main";
	}
	
	@GetMapping("/cadastrar")
	public String create(HttpServletRequest r){
		r.setAttribute("mode", MainEnum.CREATE);
		return "main";
	}
	
	@PostMapping("/salvar-usuario")
	public String create(@ModelAttribute User user, BindingResult bindingResult,HttpServletRequest r){
		User u = mainService.findByLogin(user.getLogin());
		if(u != null && u.getLogin().equals(user.getLogin())){
			r.setAttribute("alert", MainEnum.ALERT_CREATE);
			r.setAttribute("mode", MainEnum.CREATE);
			return "main";
		}
		mainService.save(user);
		r.setAttribute("mode", MainEnum.LOGIN);
		return "main";
	}
	
	@PostMapping("/entrar")
	public String login(@RequestParam String login, @RequestParam String password, HttpServletRequest r){
		User u = mainService.findByLogin(login);
		
		if(u != null && u.getLogin().equals(login) && u.getPassword().equals(password)){
			r.getSession().setAttribute("user", u);
			r.setAttribute("NameGroup", "Tarefas");
			r.setAttribute("groups", groupService.findByIdUser(u.getId()));
			r.setAttribute("hashMapTasks", taskProcessamento(r, 0, 0));
			r.setAttribute("mode", UserEnum.HOME);
			return "user";
		}
		r.setAttribute("alert", MainEnum.ALERT_LOGIN);
		r.setAttribute("mode", MainEnum.LOGIN);
		return "main";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest r){
		r.getSession().invalidate();
		r.setAttribute("mode", MainEnum.LOGIN);
		return "main";
	}
	
	@GetMapping("/meus-dados")
	public String meusDados(HttpServletRequest r){
		r.setAttribute("user",(User) r.getSession().getAttribute("user"));
		r.setAttribute("mode", UserEnum.MEUS_DADOS);
		return "user";
	}
	
	@GetMapping("/deletar-usuario")
	public String userDelete(HttpServletRequest r){
		mainService.delete((User) r.getSession().getAttribute("user"));
		r.getSession().invalidate();
		r.setAttribute("mode", MainEnum.LOGIN);
		return "main";
	}
	
	@PostMapping("/salvar-meus-dados")
	public String salvarMeusDados(@ModelAttribute User user, BindingResult bindingResult,HttpServletRequest r){
		mainService.save(user);
		r.setAttribute("mode", MainEnum.LOGIN);
		return "main";
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
