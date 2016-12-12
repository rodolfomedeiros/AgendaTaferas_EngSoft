package taskprime.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import taskprime.dao.GroupRepository;
import taskprime.model.Group;

@Service
@Transactional
public class GroupService {
	
	private GroupRepository groupRepository;
	
	public GroupService(GroupRepository groupRepository){
		this.groupRepository = groupRepository;
	}
	
	public List<Group> findAll(){
		List<Group> groups = new ArrayList<>();
		for(Group group : groupRepository.findAll()){
			groups.add(group);
		}
		return groups;
	}
	
	public List<Group> findByIdUser(int idUser){
		List<Group> groups = new ArrayList<>();
		for(Group group : groupRepository.findByIdUser(idUser)){
			groups.add(group);
		}
		return groups;
	}

	public void save(Group group) {
		groupRepository.save(group);
	}

	public void delete(int id) {
		groupRepository.delete(id);
	}
	
	public Group findOne(int groupTask){
		return groupRepository.findOne(groupTask);
	}
}
