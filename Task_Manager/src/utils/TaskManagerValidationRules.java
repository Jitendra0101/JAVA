package utils;

import java.time.LocalDate;
import static java.time.LocalDate.*;

//import java.lang.reflect.Array;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.app.core.Status;
import com.app.core.TaskManger;

import custom_exceptions.CompareByName;
import custom_exceptions.TaskManagerException;
import custom_ordering.CompareByDate;

public class TaskManagerValidationRules {

	public static LocalDate parseDate(String taskDate) throws DateTimeParseException {

		return LocalDate.parse(taskDate);
	}

	public static Status parseStatus(String status) throws IllegalArgumentException {

		return Status.valueOf(status.toUpperCase());
	}

	public static void getAllPendingTasks(Map<Integer, TaskManger> taskMap) {

		for (Map.Entry<Integer, TaskManger> elem : taskMap.entrySet()) {

			Status stat = elem.getValue().getStatus();
			if (Status.PENDING == stat)
				System.out.println(elem.getValue());
		}

	}

	public static void getAllPendingTasksForToday(Map<Integer, TaskManger> taskMap) {

		for (Map.Entry<Integer, TaskManger> elem : taskMap.entrySet()) {

			Status stat = elem.getValue().getStatus();
			LocalDate task_Date = elem.getValue().getTaskDate();
			Period diff = Period.between(task_Date, now());
			if (0 == diff.getDays() && stat == Status.PENDING) {
				System.out.println(elem.getValue());
			}

		}
	}
	
	public static void getAllPendingTasksSortedByDate(ArrayList<TaskManger> list) {
		
		Collections.sort(list, new CompareByDate());
		for(TaskManger index : list) {
			if(index.getStatus().equals(Status.PENDING))
				System.out.println(index);
		}
		
	}
	
	public static void getAllTasksSortedByName(ArrayList<TaskManger> list) {
		
		Collections.sort(list, new CompareByName());
		for(TaskManger index : list) {
			System.out.println(index);
		}
	}

	public static void removeTask(int rmTask, Map<Integer, TaskManger> taskMap) throws TaskManagerException {

		Integer wrpd = rmTask;
		if(taskMap.get(wrpd)==null)
			throw new TaskManagerException("Given TaskID does NOT Exist !!");
		else
			taskMap.get(wrpd).setActive(false);
		
		
//		TaskManger removeRef = taskMap.remove(wrpd);
//		if (removeRef == null)
//			throw new TaskManagerException("Given TaskID does NOT Exist !!");

	}

	public static void updateStatus(int upTask, String stat, Map<Integer, TaskManger> taskMap)
			throws IllegalArgumentException {

		Integer wrpd = upTask;
		TaskManger updateRef = taskMap.get(wrpd);
		updateRef.setStatus(Status.valueOf(stat.toUpperCase()));

	}

	public static TaskManger validAllInputs(String taskName, String desc, String taskDate, String status,
			boolean active, Map<Integer, TaskManger> taskMap) throws DateTimeParseException, IllegalArgumentException {

		LocalDate parsedDate = parseDate(taskDate);
		Status parsedStatus = parseStatus(status);

//		String taskName, String desc, LocalDate taskDate, Status status, boolean active
		return new TaskManger(taskName, desc, parsedDate, parsedStatus, true);
	}

}
