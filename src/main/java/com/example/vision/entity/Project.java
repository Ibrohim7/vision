package com.example.vision.entity;

import com.example.vision.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private List<User> executors;

    @JsonFormat(pattern = "dd.MM.yyyy", timezone = "Asia/Tashkent")
    private Date deadline;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    @NotNull
    private List<ProjectStatus> status;

    @ManyToMany
    @JoinColumn(name = "category_id")
    private List<Category> categories;

    @ManyToOne
    private Task task;

}