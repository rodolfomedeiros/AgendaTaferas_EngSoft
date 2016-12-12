package taskprime.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;

@Entity(name="tasks")
public class Task implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String description;
	private LocalDate dateFinished;
	private LocalTime timeFinished;
	private boolean finished;
	private int groupTask;
	private int idUser;
	
	public Task(){}
	
	public Task(String name,String description, LocalDate dateFinished, LocalTime timeFinished , boolean finished, int groupTask, int idUser) {
		setName(name);
		setDescription(description);
		setDateFinished(dateFinished);
		setTimeFinished(timeFinished);
		setFinished(finished);
		setGroupTask(groupTask);
		setIdUser(idUser);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateFinished() {
		return dateFinished;
	}

	public void setDateFinished(LocalDate dateFinished) {
		this.dateFinished = dateFinished;
	}

	public LocalTime getTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(LocalTime timeFinished) {
		this.timeFinished = timeFinished;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public int getGroupTask() {
		return groupTask;
	}

	public void setGroupTask(int groupTask) {
		this.groupTask = groupTask;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
}
