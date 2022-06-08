package com.example.vision.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class ReqTask {
    private Long id;

    private String name;

    private String executor;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private List<Integer> taskStatusId;

}
