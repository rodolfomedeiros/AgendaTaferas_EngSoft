package taskprime.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import taskprime.model.Group;

public interface GroupRepository extends CrudRepository<Group, Integer>{
	List<Group> findByIdUser(int idUser);
}
