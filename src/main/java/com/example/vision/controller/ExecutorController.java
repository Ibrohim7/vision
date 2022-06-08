//package com.example.vision.controller;
//
//import com.example.vision.controller.models.Response;
//import com.example.vision.payload.ReqExecutor;
//import com.example.vision.service.ExecutorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/executor")
//public class ExecutorController {
//    private final ExecutorService executorService;
//
//    @Autowired
//    public ExecutorController(ExecutorService executorService) {
//        this.executorService = executorService;
//    }
//
////    @PostMapping
////    public HttpEntity<?> addExecutor(@RequestBody ReqExecutor reqExecutor) {
////        Response response = executorService.addExecutor(reqExecutor);
////        return ResponseEntity.ok(response);
//
//    @GetMapping("/listUsers")
//    public HttpEntity<?> list() {
//        Response response = executorService.list();
//        return ResponseEntity.ok(response);
//    }
//}
