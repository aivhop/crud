package com.aivhop.crud.crudapp.utils;

import com.aivhop.crud.crudapp.models.Role;
import com.aivhop.crud.crudapp.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class RoleValidator implements Validator {

    private final RoleService roleService;

    @Autowired
    public RoleValidator(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Role.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Role role = (Role) target;

        Optional<Role> roleOptionalWithEqualName = roleService.findByName(role.getName());
        if (roleOptionalWithEqualName.isPresent() && role.getId() != roleOptionalWithEqualName.get().getId()) {
            errors.rejectValue("name", "", "This role name is already taken");
        }

    }
}
