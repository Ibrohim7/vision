package com.example.vision.service;

import com.example.vision.controller.models.Response;
import com.example.vision.payload.ReqProject;
import com.example.vision.payload.ReqTask;
import org.springframework.stereotype.Service;


public interface TaskService {

    Response addTask(ReqTask reqTask);

    Response deleteTask(Long taskId);

    Response tasksList();

}
