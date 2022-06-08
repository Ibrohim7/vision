package com.example.vision.payload;

import com.example.vision.entity.Task;
import com.example.vision.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ReqProject {

    private Long id;
    @NotNull
    private String name;

    @NotNull
    private String description;

    private List<Integer> executorsId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private List<Integer> statusId;

    private List<Integer> categoryId;

    private Task task;

}
