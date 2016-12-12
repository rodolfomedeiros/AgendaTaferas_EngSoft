package taskprime.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import taskprime.model.Task;

public interface TaskRepository extends CrudRepository<Task, Integer>{
	List<Task> findByIdUser(int idUser);
	List<Task> findByGroupTask(int groupTask);
}
