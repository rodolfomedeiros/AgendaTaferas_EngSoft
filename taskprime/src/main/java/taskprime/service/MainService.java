package taskprime.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import taskprime.dao.MainRepository;
import taskprime.model.User;

@Service
@Transactional
public class MainService {
	
	private MainRepository mainRepository;
	
	public MainService(MainRepository mainRepository){
		this.mainRepository = mainRepository;
	}
	
	
	public void save(User user){
		mainRepository.save(user);
	}
	
	public User findByLogin(String login){
		return mainRepository.findByLogin(login);
	}
	
	public void delete(User user){
		mainRepository.delete(user);
	}
}
