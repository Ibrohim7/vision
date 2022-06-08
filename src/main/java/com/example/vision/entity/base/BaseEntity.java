package com.example.vision.entity.base;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class
BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private boolean active = true;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

//    public void setStatusId(List<Integer> statusId) {
//        this.statusId = statusId;
//    }
//
//    public List<Integer> getStatusId() {
//        return statusId;
//    }
}
