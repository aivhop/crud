package com.aivhop.crud.crudapp.controllers;

import com.aivhop.crud.crudapp.models.Role;

import com.aivhop.crud.crudapp.models.User;
import com.aivhop.crud.crudapp.services.RoleService;
import com.aivhop.crud.crudapp.services.UserService;
import com.aivhop.crud.crudapp.utils.RoleValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    // todo add json
    private final RoleService roleService;
    private final RoleValidator roleValidator;

    private final UserService userService;


    @Autowired
    public RolesController(RoleService roleService, RoleValidator roleValidator, UserService userService) {
        this.roleService = roleService;
        this.roleValidator = roleValidator;
        this.userService = userService;
    }


    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return new ModelAndView("roles/index");
    }

    @GetMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView showRole(@PathVariable("id") int id, Model model) {
        model.addAttribute("role", roleService.findOne(id));
        return new ModelAndView("roles/show");
    }

    @GetMapping(path = "/new", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView newRole(@ModelAttribute("role") Role role, @RequestParam(value = "userId", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("userId", id);
        }
        return new ModelAndView("roles/new");
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView create(@ModelAttribute("role") @Valid Role role, BindingResult bindingResult, @RequestParam(value = "userId", required = false) Integer id) {
        roleValidator.validate(role, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("roles/new");
        }
        if (id == null) {
            roleService.save(role);
            return new ModelAndView("redirect:/roles");
        }
        roleService.saveWithUserById(role, id);
        return new ModelAndView("redirect:/users/" + id + "/roles");
    }


    @GetMapping(path = "/{id}/edit", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("role", roleService.findOne(id));
        return new ModelAndView("roles/edit");
    }

    @PatchMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView update(@PathVariable("id") int id, @Valid Role role, BindingResult bindingResult) {
        roleValidator.validate(role, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/roles/edit");
        }
        roleService.update(id, role);
        return new ModelAndView("redirect:/roles");
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView delete(@PathVariable("id") int id) {
        roleService.delete(id);
        return new ModelAndView("redirect:/roles");
    }



    @GetMapping(path = "/{id}/users", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView showRoleUsers(@PathVariable("id") int id, Model model, @ModelAttribute("user") User user) {
        List<User> userList = userService.findAll();
        userList.add(new User("New user..."));
        model.addAttribute("users", userList);
        model.addAttribute("role", roleService.findOne(id));
        return new ModelAndView("roles/users");
    }

    @PatchMapping(path = "/{id}/users", produces = MediaType.TEXT_HTML_VALUE)
    public RedirectView newRolesUser(@PathVariable("id") int id,
                                     @ModelAttribute("user") User user, RedirectAttributes attributes) {
        if (user.getId() == 0) {
            attributes.addAttribute("roleId", id);
            return new RedirectView("/users/new");
        }
        userService.addRole(user.getId(), id);
        return new RedirectView("/roles");
    }

}
