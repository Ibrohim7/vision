package com.example.vision.entity;

import com.example.vision.entity.enums.StatusName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "status")
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusName name;
}
