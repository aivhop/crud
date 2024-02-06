package com.aivhop.crud.crudapp.repositories;

import com.aivhop.crud.crudapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);
    List<User> findByUserName(String userName);


}
