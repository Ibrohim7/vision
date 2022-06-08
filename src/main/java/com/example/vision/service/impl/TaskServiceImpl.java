package com.example.vision.service.impl;

import com.example.vision.controller.models.Response;
import com.example.vision.controller.models.Status;
import com.example.vision.entity.Project;
import com.example.vision.entity.ProjectStatus;
import com.example.vision.entity.Task;
import com.example.vision.payload.ReqTask;
import com.example.vision.repository.ProjectRepository;
import com.example.vision.repository.StatusRepository;
import com.example.vision.repository.TaskRepository;
import com.example.vision.service.TaskService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final JdbcTemplate jdbcTemplate;
    private final StatusRepository statusRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository, JdbcTemplate jdbcTemplate, StatusRepository statusRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.statusRepository = statusRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Response addTask(ReqTask reqTask) {
        Response response = new Response();
        List<ProjectStatus> statuses = new ArrayList<>();
        for (int i = 0; i < reqTask.getTaskStatusId().size(); i++) {
            statuses.add(statusRepository.findById(reqTask.getTaskStatusId().get(i)).get());
        }
        if (reqTask.getId() == null) {

            Optional<Task> byName = taskRepository.findByName(reqTask.getName());
            if (!byName.isPresent()) {
                Task task = new Task();
                task.setName(reqTask.getName());
                task.setExecutor(reqTask.getExecutor());
                task.setDescription(reqTask.getDescription());
                task.setDeadline(reqTask.getDeadline());
                task.setStatus(statuses);
                Task save = taskRepository.save(task);
                response.setData(save);
                response.setMessage(new Status(0, "Task added"));
            } else {
                response.setMessage(new Status(105, "Task exists"));
            }
        } else {
            Optional<Task> byId = taskRepository.findById(reqTask.getId());
            if (byId.isPresent()) {
                Optional<Task> byName = taskRepository.findByName(reqTask.getName());
                if (!byName.isPresent()) {
                    Task task = byId.get();
                    task.setName(reqTask.getName());
                    task.setExecutor(reqTask.getExecutor());
                    task.setDescription(reqTask.getDescription());
                    task.setDeadline(reqTask.getDeadline());
                    task.setStatus(statuses);
                    Task save = taskRepository.save(task);
                    response.setData(save);
                    response.setMessage(new Status(0, "Task edited"));
                } else {
                    response.setMessage(new Status(105, "Task exists"));
                }
            } else {
                response.setMessage(new Status(404, "Task not found"));
            }
        }
        return response;
    }

    @Override
    public Response deleteTask(Long taskId) {
        Response response = new Response();
        Optional<Task> byId = taskRepository.findById(taskId);
        if (byId.isPresent()) {
            List<Project> allByTaskId = projectRepository.findAllByTaskId(taskId);
            if (allByTaskId.size()>0) {
                projectRepository.deleteAll(allByTaskId);
            }
            taskRepository.deleteById(taskId);
            response.setMessage(new Status(0, "Task deleted"));
        } else response.setMessage(new Status(404, "Task not found"));
        return response;
    }

    @Override
    public Response tasksList() {
        Response response = new Response();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select t.id,t.name,t.description,t.description,t.executor,ts.status_id from tasks t left join tasks_status ts on t.id = ts.tasks_id order by t.id");
        response.setData(maps);
        response.setMessage(new Status(0, "Success"));
        return response;
    }
}