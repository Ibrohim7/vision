package com.example.vision.service.impl;

import com.example.vision.controller.models.Response;
import com.example.vision.controller.models.Status;
import com.example.vision.entity.*;
import com.example.vision.payload.ReqProject;
import com.example.vision.repository.*;
import com.example.vision.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final JdbcTemplate jdbcTemplate;
    private final StatusRepository statusRepository;
    private final TaskRepository taskRepository;
    //    private final ExecutorRepository executorRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, JdbcTemplate jdbcTemplate, StatusRepository statusRepository, TaskRepository taskRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.projectRepository = projectRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.statusRepository = statusRepository;
        this.taskRepository = taskRepository;
//        this.executorRepository = executorRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Response addProject(ReqProject reqProject) {
        Response response = new Response();

        List<ProjectStatus> statuses = new ArrayList<>();
        for (int i = 0; i < reqProject.getStatusId().size(); i++) {
            statuses.add(statusRepository.findById(reqProject.getStatusId().get(i)).get());
        }

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < reqProject.getCategoryId().size(); i++) {
            categories.add(categoryRepository.findById(reqProject.getCategoryId().get(i)).get());
        }


        if (reqProject.getId() == null) {
            Optional<Project> byName = projectRepository.findByName(reqProject.getName());
            if (!byName.isPresent()) {
//                Project save = projectRepository.save(new Project(reqProject.getName(), reqProject.getDescription(), reqProject.getPm(), reqProject.getDeadline(), statuses));
                Project project = new Project();
                project.setName(reqProject.getName());
                project.setDescription(reqProject.getDescription());

                List<User> users = new ArrayList<>();
                for (int i = 0; i < reqProject.getExecutorsId().size(); i++) {
                    users.add(userRepository.findById(Long.valueOf(reqProject.getExecutorsId().get(i))).get());
                }

                if (reqProject.getExecutorsId() == null) {
                    response.setMessage(new Status(404, "Executor not found"));
                } else {
                    project.setExecutors(users);
                }

                if (reqProject.getDeadline() != null) {
                    project.setDeadline(reqProject.getDeadline());
                } else {
                    Date date = new Date();
                    project.setDeadline(date);
                }
//                if (reqProject.getStatusId() == null) {
//                    project.setStatusId(reqProject.getStatusId());
//                }
                project.setStatus(statuses);
                project.setCategories(categories);
                project.setTask(reqProject.getTask());
                Project save = projectRepository.save(project);
                response.setData(save);
                response.setMessage(new Status(0, "Project added"));
            } else {
                response.setMessage(new Status(105, "Project exists"));
            }
        } else {
            Optional<Project> byId = projectRepository.findById(reqProject.getId());
            if (byId.isPresent()) {
                Optional<Project> byName = projectRepository.findByName(reqProject.getName());
                if (!byName.isPresent()) {
                    Project project = byId.get();
                    project.setName(reqProject.getName());
                    project.setDescription(reqProject.getDescription());
                    List<User> users = new ArrayList<>();
                    for (int i = 0; i < reqProject.getExecutorsId().size(); i++) {
                        users.add(userRepository.findById(Long.valueOf(reqProject.getExecutorsId().get(i))).get());
                    }
                    project.setExecutors(users);
                    project.setDeadline(reqProject.getDeadline());
                    project.setTask(reqProject.getTask());
                    project.setStatus(statuses);
                    project.setCategories(categories);
                    Project save = projectRepository.save(project);
                    response.setData(save);
                    response.setMessage(new Status(0, "Project edited"));
                } else {
                    response.setMessage(new Status(105, "Project exists"));
                }
            } else {
                response.setMessage(new Status(404, "Project not found"));
            }
        }
        return response;
    }

//    @Override
//    public Response deleteProject(Long projectId) {
//        Response response = new Response();
//        Optional<Project> byId = projectRepository.findById(projectId);
//        if (byId.isPresent()) {
//            try {
//                projectRepository.deleteById(projectId);
//                response.setMessage(new Status(0, "Project deleted"));
//            } catch (Exception e) {
//                response.setMessage(new Status(115, "This project connected to other objects"));
//            }
//        } else response.setMessage(new Status(404, "Project not found"));
//        return response;
//    }

    @Override
    public Response projectsList() {
        Response response = new Response();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select p.id,p.name, p.deadline, p.description, p.task_id, ps.status_id,pc.categories_id,pe.executors_id from projects p left join projects_status ps on p.id = ps.projects_id left join projects_categories pc on p.id = pc.projects_id left join projects_executors pe on p.id = pe.projects_id order by p.id");
        response.setData(maps);
        response.setMessage(new Status(0, "Success"));
        return response;
    }

    @Override
    public Response connectTask(Long taskId, Long projectId) {
        Response response = new Response();
        Optional<Project> projectById = projectRepository.findById(projectId);
        if (projectById.isPresent()) {
            Optional<Task> taskById = taskRepository.findById(taskId);
            if (taskById.isPresent()) {
                Project project = projectById.get();
                project.setTask(taskById.get());
                projectRepository.save(project);
                response.setMessage(new Status(0, "Success"));
            } else response.setMessage(new Status(404, "Task not found"));
        } else response.setMessage(new Status(404, "Project not found"));
        return response;
    }

//    @Override
//    public Response connectExecutors(Long executorId, Long projectId) {
//        Response response = new Response();
//        Optional<Project> projectById = projectRepository.findById(projectId);
//        if (projectById.isPresent()) {
//            Optional<User> executorById = userRepository.findById(executorId);
//            if (executorById.isPresent()) {
//                Project project = projectById.get();
//                project.setExecutors(executorById.get());
//                projectRepository.save(project);
//                response.setMessage(new Status(0, "Success"));
//            } else response.setMessage(new Status(404, "Executor not found"));
//        } else response.setMessage(new Status(404, "Project not found"));
//        return response;
//    }
}