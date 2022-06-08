package com.example.vision.repository;

import com.example.vision.entity.Role;
import com.example.vision.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
//    List<Role> findAllByName(RoleName roleName);
//
//    Role findByName(RoleName roleName);

}
