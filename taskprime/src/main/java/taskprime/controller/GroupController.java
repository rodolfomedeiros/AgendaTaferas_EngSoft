package taskprime.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import taskprime.model.Group;
import taskprime.model.User;
import taskprime.model.UserEnum;
import taskprime.service.GroupService;
import taskprime.service.TaskService;

@Controller
public class GroupController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private GroupService groupService;
	
	@PostMapping("/salvar-grupo")
	public String groupNew(@RequestParam String name, HttpServletRequest r){
		User u = (User) r.getSession().getAttribute("user");
		groupService.save(new Group(name,u.getId()));
		
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("mode", UserEnum.NEW_TASK);
		return "user";
	}
	
	@PostMapping("/excluir-grupo")
	public String groupDelete(@RequestParam int id, HttpServletRequest r){
		User u = (User) r.getSession().getAttribute("user");
		groupService.delete(id);
		r.setAttribute("groups", groupService.findByIdUser(u.getId()));
		r.setAttribute("mode", UserEnum.NEW_TASK);
		return "user";
	}
}
