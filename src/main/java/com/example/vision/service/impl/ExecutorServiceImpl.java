//package com.example.vision.service.impl;
//
//import com.example.vision.controller.models.Response;
//import com.example.vision.controller.models.Status;
//import com.example.vision.entity.Executors;
//import com.example.vision.entity.Project;
//import com.example.vision.entity.Task;
//import com.example.vision.payload.ReqExecutor;
//import com.example.vision.repository.ExecutorRepository;
//import com.example.vision.service.ExecutorService;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class ExecutorServiceImpl implements ExecutorService {
//    private ExecutorRepository executorRepository;
//    private final JdbcTemplate jdbcTemplate;
//
//    public ExecutorServiceImpl(ExecutorRepository executorRepository, JdbcTemplate jdbcTemplate) {
//        this.executorRepository = executorRepository;
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
////    @Override
////    public Response addExecutor(ReqExecutor reqExecutor) {
////        Response response = new Response();
////        if (reqExecutor.getId() == null) {
////            Optional<Executors> byName = executorRepository.findByName(reqExecutor.getName());
////            if (!byName.isPresent()) {
////                Executors executors = new Executors();
////                executors.setName(reqExecutor.getName());
////                Executors save = executorRepository.save(executors);
////                response.setData(save);
////                response.setMessage(new Status(0, "Executor added"));
////            } else {
////                response.setMessage(new Status(105, "Executor exists"));
////            }
////        } else {
////            Optional<Executors> byId = executorRepository.findById(reqExecutor.getId());
////            if (byId.isPresent()) {
////                Optional<Executors> byName = executorRepository.findByName(reqExecutor.getName());
////                if (!byName.isPresent()) {
////                    Executors executors = new Executors();
////                    executors.setName(reqExecutor.getName());
////                    Executors save = executorRepository.save(executors);
////                    response.setData(save);
////                    response.setMessage(new Status(0, "Executor edited"));
////                } else {
////                    response.setMessage(new Status(105, "Executor exists"));
////                }
////            } else {
////                response.setMessage(new Status(404, "Executor not found"));
////            }
////        }
////        return response;
////    }
//
////    @Override
////    public Response deleteExecutor(Long executorId) {
////        Response response = new Response();
////        Optional<Executors> byId = executorRepository.findById(executorId);
////        if (byId.isPresent()) {
////            try {
////                executorRepository.deleteById(executorId);
////                response.setMessage(new Status(0, "Executor deleted"));
////            } catch (Exception e) {
////                response.setMessage(new Status(115, "This Executor connected to other objects"));
////            }
////        } else response.setMessage(new Status(404, "Executor not found"));
////        return response;
////    }
//
//    @Override
//    public Response list() {
//        Response response = new Response();
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select au.username,aur.roles_id from app_user au left join app_user_roles aur on au.id = aur.app_user_id");
//        response.setData(maps);
//        response.setMessage(new Status(0, "Success"));
//        return response;
//    }
//}
