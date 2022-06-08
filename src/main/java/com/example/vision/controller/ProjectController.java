package com.example.vision.controller;

import com.example.vision.controller.models.Response;
import com.example.vision.controller.models.Status;
import com.example.vision.entity.Project;
import com.example.vision.payload.ReqProject;
import com.example.vision.repository.ProjectRepository;
import com.example.vision.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> addProject(@RequestBody ReqProject reqProject) {
        Response response = projectService.addProject(reqProject);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("list")
    public HttpEntity<?> list() {
        Response response = projectService.projectsList();
        return ResponseEntity.ok(response);
    }

//    @PostMapping("delete/{projectId}")
//    public HttpEntity<?> delete(@PathVariable Long projectId) {
//        Response response = projectService.deleteProject(projectId);
//        return ResponseEntity.ok(response);
//    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("delete/{projectId}")
    public HttpEntity<?> delete(@PathVariable Long projectId) {
        Response response = new Response();
        try {
            Optional<Project> byId = projectRepository.findById(projectId);
            if (byId.isPresent()) {
                projectRepository.deleteById(projectId);
                response.setMessage(new Status(0, "Success"));
            } else {
                response.setMessage(new Status(404, "Project is not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(new Status(0, "project is connected to other objects"));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("connectTask")
    public HttpEntity<?> connectTask(@RequestParam Long taskId, @RequestParam Long projectId) {
        Response response = projectService.connectTask(taskId, projectId);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("connectExecutors")
//    public HttpEntity<?> connectExecutors(@RequestParam Long projectId, @RequestParam Long executorId) {
//        Response response = projectService.connectExecutors(projectId, executorId);
//        return ResponseEntity.ok(response);
//    }
}