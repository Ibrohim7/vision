package com.example.vision.repository;

import com.example.vision.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<ProjectStatus, Integer> {

    Optional<ProjectStatus> findByName(String name);

}
