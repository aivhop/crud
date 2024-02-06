package com.aivhop.crud.crudapp.services;

import com.aivhop.crud.crudapp.models.Role;
import com.aivhop.crud.crudapp.models.User;
import com.aivhop.crud.crudapp.repositories.RolesRepository;
import com.aivhop.crud.crudapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RolesRepository rolesRepository;
    private final UsersRepository usersRepository;


    @Autowired
    public RoleService(RolesRepository rolesRepository, UsersRepository usersRepository) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
    }
    //todo another methods

    public List<Role> findAll() {
        return rolesRepository.findAll();
    }

    public Role findOne(int id) {
        return rolesRepository.findById(id).orElse(null); //todo exception mb
    }

    public Optional<Role> findByName(String name) {
        return rolesRepository.findByName(name).stream().findFirst();
    }


    @Transactional
    public void saveWithUser(Role role, User user) {
        role.getUsers().add(user);
        rolesRepository.save(role);
    }

    @Transactional
    public void saveWithUserById(Role role, Integer userId) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            role.getUsers().add(user.get());
            rolesRepository.save(role);
            user.get().getRoles().add(role);
        } else {
            //todo throw exception
        }
    }

    @Transactional
    public void save(Role role) {
        rolesRepository.save(role);
    }

    @Transactional
    public void update(int id, Role role) {
        role.setId(id);
        rolesRepository.save(role);
    }

    @Transactional
    public void delete(int id) {
        rolesRepository.deleteById(id);
    }
}
