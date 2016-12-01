package kn.scheduler.core;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Krishnanand
 *
 */
public class Scheduler {
	private static Scheduler scheduler = null;
	private TaskFinder dao = null;
	private TaskRunner taskRunner = null;

	/**
	 * private constructor
	 */
	private Scheduler() {
	}

	public TaskFinder getDao() {
		return dao;
	}

	public void setDao(TaskFinder dao) {
		this.dao = dao;
	}

	public TaskRunner getTaskRunner() {
		return taskRunner;
	}

	public void setTaskRunner(TaskRunner taskRunner) {
		this.taskRunner = taskRunner;
	}

	/**
	 * method setDao needs to be called after getting the instance else it is
	 * null also taskRunner needs to be set with the proper implementation
	 * 
	 * @return
	 */
	public static Scheduler getInstance() {
		if (scheduler != null) {
			scheduler = new Scheduler();
		}
		return scheduler;
	}

	/**
	 * @param schedulerPingInterval
	 *            frequency by which the scheduler will be checking for new
	 *            tasks
	 * @param timeUnit
	 *            {@link TimeUnit} for the schedulerPingInterval
	 * 
	 */
	public void startScheduler(int schedulerPingInterval, TimeUnit timeUnit) {
		Scheduler.getInstance().startSchedulesChecker(schedulerPingInterval, timeUnit);
	}

	private void startSchedulesChecker(int schedulerPingInterval, TimeUnit timeUnit) {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

		Runnable taskChecker = new Runnable() {

			@Override
			public void run() {
				ScheduledThreadPoolExecutor taskExecutor = new ScheduledThreadPoolExecutor(15);

				Date todayDate = new Date();
				List<ScheduleDto> schedules = dao.getScheduledTasks();
				if (schedules != null && schedules.size() > 0) {

				}
				for (ScheduleDto scheduleDto : schedules) {
					final String task = scheduleDto.getTask();
					Runnable taskStarter = new Runnable() {
						@Override
						public void run() {
							Scheduler.getInstance().getTaskRunner().runScheduledTests(task);
						}

					};
					// run this at its scheduled time
					long delay = scheduleDto.getScheduledTime().getTime() - todayDate.getTime();
					taskExecutor.schedule(taskStarter, delay, TimeUnit.MILLISECONDS);
				}
			}
		};

		executor.scheduleAtFixedRate(taskChecker, 0, schedulerPingInterval, TimeUnit.MINUTES);
	}

}
