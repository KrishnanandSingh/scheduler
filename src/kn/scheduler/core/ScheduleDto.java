package kn.scheduler.core;

import java.util.Date;

/**
 * dto class for task name and its scheduled time
 * 
 * @author Krishnanand
 */
public class ScheduleDto {
	private String task;
	private Date scheduledTime;


	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

}
