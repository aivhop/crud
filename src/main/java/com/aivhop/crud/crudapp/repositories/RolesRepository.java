package com.aivhop.crud.crudapp.repositories;

import com.aivhop.crud.crudapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    List<Role> findByName(String name);
}
