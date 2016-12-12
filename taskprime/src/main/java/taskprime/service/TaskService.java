package taskprime.service;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import java.util.ArrayList;

import taskprime.dao.TaskRepository;
import taskprime.model.Task;

@Service
@Transactional
public class TaskService {

	private TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository){
		this.taskRepository = taskRepository;
	}
	
	public List<Task> findAll(){
		List<Task> tasks = new ArrayList<>();
		for(Task t : taskRepository.findAll()){
			tasks.add(t);
		}
		return tasks;
	}
	
	public void save(Task task){
		taskRepository.save(task);
	}
	
	public Task findOne(int id){
		return taskRepository.findOne(id);
	}
	
	public void delete(int id){
		taskRepository.delete(id);
	}
	
	public List<Task> findByIdUser(int idUser){
		List<Task> tasks = new ArrayList<>();
		for(Task t : taskRepository.findByIdUser(idUser)){
			tasks.add(t);
		}
		return tasks;
	}
	
	public List<Task> findByIdUser(int idUser, boolean finished){
		List<Task> tasksTrue = new ArrayList<>();
		List<Task> tasksFalse = new ArrayList<>();
		for(Task t : taskRepository.findByIdUser(idUser)){
			if(t.isFinished()){
				tasksTrue.add(t);
			}else{
				tasksFalse.add(t);
			}
		}
		
		if(finished){
			return tasksTrue;
		}else return tasksFalse;
	}
	
	public List<Task> findByGroupTask(int groupTask, boolean finished){
		List<Task> tasksTrue = new ArrayList<>();
		List<Task> tasksFalse = new ArrayList<>();
		for(Task t : taskRepository.findByGroupTask(groupTask)){
			if(t.isFinished()){
				tasksTrue.add(t);
			}else{
				tasksFalse.add(t);
			}
		}
		
		if(finished){
			return tasksTrue;
		}else return tasksFalse;
	}
	
}
