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
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
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
                "UPDATE Task SET employee_id = ?, description = ?, deadline = ?, status = ? WHERE task_id = ?",
                taskDTO.getEmployeeId(),
                taskDTO.getDescription(),
                taskDTO.getDeadline(),
                taskDTO.getStatus(),
                taskDTO.getTaskId()
        );
    }

    public boolean saveTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Task VALUES (?, ?, ?, ?, ?)",
                taskDTO.getTaskId(),
                taskDTO.getEmployeeId(),
                taskDTO.getDescription(),
                taskDTO.getDeadline(),
                taskDTO.getStatus()
        );
    }


    public String getNextTaskId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT task_id FROM Task ORDER BY task_id DESC LIMIT 1");
        char tableCharacter = 'T';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber);
            return nextIdString;
        }


        return tableCharacter + "001";
    }
    }
