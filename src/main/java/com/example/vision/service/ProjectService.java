package com.example.vision.service;


import com.example.vision.controller.models.Response;
import com.example.vision.payload.ReqProject;
import org.springframework.data.relational.core.sql.In;

public interface ProjectService {

    Response addProject(ReqProject reqProject);

//    Response deleteProject(Long projectId);

    Response projectsList();

    Response connectTask(Long taskId, Long projectId);

//    Response connectExecutors(Long executorId, Long projectId);
}
