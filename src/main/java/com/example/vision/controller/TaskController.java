package com.example.vision.controller;

import com.example.vision.controller.models.Response;
import com.example.vision.payload.ReqTask;
import com.example.vision.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> addTask(@RequestBody ReqTask reqTask) {
        Response response = taskService.addTask(reqTask);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    public HttpEntity<?> list() {
        Response response = taskService.tasksList();
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("delete/{taskId}")
    public HttpEntity<?> delete(@PathVariable Long taskId) {
        Response response = taskService.deleteTask(taskId);
        return ResponseEntity.ok(response);
    }

}