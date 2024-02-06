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
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public UserService(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    //todo another methods
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(int id) {
        return usersRepository.findById(id).orElse(null); //todo exception mb
    }

    @Transactional
    public void saveWithRoleById(User user, Integer roleId) {
        Optional<Role> role = rolesRepository.findById(roleId);
        if (role.isPresent()) {
            user.getRoles().add(role.get());
            usersRepository.save(user);
            role.get().getUsers().add(user);
        } else {
            //todo throw exception
        }
    }
    @Transactional
    public void addRole(int idUser, int idRole) {
        Optional<User> user = usersRepository.findById(idUser);
        Optional<Role> role = rolesRepository.findById(idRole);
        if (user.isPresent() && role.isPresent()) {
            List<Role> roles = user.get().getRoles();
            Role roleExist = role.get();
            if (roles == null || !roles.contains(roleExist)) {
                User userExist = user.get();
                userExist.getRoles().add(roleExist);
                usersRepository.save(userExist);
            }
        }
    }

    @Transactional
    public void addRole(User user, int idRole) {
        Optional<Role> role = rolesRepository.findById(idRole);
        if (role.isPresent()) {
            List<Role> roles = user.getRoles();
            Role roleExist = role.get();
            if (roles == null || !roles.contains(roleExist)) {
                user.getRoles().add(roleExist);
                usersRepository.save(user);
            }
        }
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        user.setId(id);
        usersRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email).stream().findAny();
    }

    public Optional<User> findByUserName(String userName) {
        return usersRepository.findByUserName(userName).stream().findAny();
    }

}
