package data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="robot")
public class Robot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int speed;
	private int turn;
	private int duration;
	private String task;
	
	public Robot() {
		super();
	}
	public Robot(int speed, int turn) {
		super();
		this.speed = speed;
		this.turn = turn;
	}
	public Robot(int id, int speed, int turn) {
		super();
		this.id = id;
		this.speed = speed;
		this.turn = turn;
	}
	public Robot(int speed, int turn, int duration, String task) {
    super();
    this.speed = speed;
    this.turn = turn;
    this.duration = duration;
    this.task = task;
}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public int getDuration() {
    return duration;
    }
    public void setDuration(int duration) {
	this.duration = duration;
	}
	public String getTask() {
    return task;
   }

    public void setTask(String task) {
    this.task = task;
}    
	public String toString() {
		return id+": "+speed+": "+turn;
	}
}