package taskprime.dao;

import org.springframework.data.repository.CrudRepository;

import taskprime.model.User;


public interface MainRepository extends CrudRepository<User, Integer> {
	User findByLogin(String login);
}
