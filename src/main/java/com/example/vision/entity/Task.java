package com.example.vision.entity;

import com.example.vision.entity.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task extends BaseEntity {

    private String name;

    private String executor;

    private String description;

    private Date deadline;

    @ManyToMany
    @JoinColumn(name = "taskStatus_id")
    private List<ProjectStatus> status;
}