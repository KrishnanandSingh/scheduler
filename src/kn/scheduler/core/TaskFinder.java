package kn.scheduler.core;

import java.util.List;

/**
 * @author Krishnanand
 *
 */
public interface TaskFinder {

	/**
	 * checks for the next tasks to run
	 * 
	 * @return List of ScheduleDto
	 */
	public List<ScheduleDto> getScheduledTasks();

}
