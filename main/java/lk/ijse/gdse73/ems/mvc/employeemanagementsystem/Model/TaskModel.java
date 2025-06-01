package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TaskDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskModel {

    public ArrayList<TaskDTO> getAllTasks() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Task");

        ArrayList<TaskDTO> taskList = new ArrayList<>();

        while (resultSet.next()) {
            TaskDTO taskDTO = new TaskDTO(
                    resultSet.getString("task_id"),
                    resultSet.getString("description"),
                    resultSet.getString("deadline"),
                    resultSet.getString("status"),
                    resultSet.getString("employee_id")
            );
            taskList.add(taskDTO);
        }

        return taskList;
    }

    public boolean deleteTask(String taskId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Task WHERE task_id = ?",
                taskId
        );
    }

    public boolean updateTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Task SET description = ?, deadline = ?, status = ?, employee_id = ? WHERE task_id = ?",
                taskDTO.getDescription(),
                taskDTO.getDeadline(),
                taskDTO.getStatus(),
                taskDTO.getEmployeeId(),
                taskDTO.getTaskId()
        );
    }

    public boolean saveTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Task (task_id, description, deadline, status, employee_id) VALUES (?, ?, ?, ?, ?)",
                taskDTO.getTaskId(),
                taskDTO.getDescription(),
                taskDTO.getDeadline(),
                taskDTO.getStatus(),
                taskDTO.getEmployeeId()
        );
    }

    public String getNextTaskId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT task_id FROM Task ORDER BY task_id DESC LIMIT 1");
        char prefix = 'T';

        if (resultSet.next()) {
            String lastId = resultSet.getString("task_id");
            int number = Integer.parseInt(lastId.substring(1));
            number++;
            return String.format("%c%03d", prefix, number);
        }
        return prefix + "001";
    }
}
