package com.aivhop.crud.crudapp.controllers;

import com.aivhop.crud.crudapp.models.Role;
import com.aivhop.crud.crudapp.models.User;
import com.aivhop.crud.crudapp.services.ItemService;
import com.aivhop.crud.crudapp.services.RoleService;
import com.aivhop.crud.crudapp.services.UserService;
import com.aivhop.crud.crudapp.utils.UserValidator;
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
@RequestMapping("/users")
public class UsersController {
    // todo add json
    private final UserService userService;

    private final UserValidator userValidator;

    private final RoleService roleService;
    private final ItemService itemService;

    @Autowired
    public UsersController(UserService userService, UserValidator userValidator, RoleService roleService, ItemService itemService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
        this.itemService = itemService;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(Model model) {
        model.addAttribute("users", userService.findAll());
        return new ModelAndView("users/index");
    }

    @GetMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return new ModelAndView("users/show");
    }

    @GetMapping(path = "/new", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView newUser(@ModelAttribute("user") User user, @RequestParam(value = "roleId", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("roleId", id);
        }
        return new ModelAndView("users/new");
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam(value = "roleId", required = false) Integer id) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("users/new");
        }
        if (id == null) {
            userService.save(user);
            return new ModelAndView("redirect:/users");
        }
        userService.saveWithRoleById(user, id);
        return new ModelAndView("redirect:/roles/" + id + "/users");
    }

    @GetMapping(path = "/{id}/edit", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return new ModelAndView("users/edit");
    }

    @PatchMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView update(@PathVariable("id") int id, @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/users/edit");
        }
        userService.update(id, user);
        return new ModelAndView("redirect:/users");
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView delete(@PathVariable("id") int id) {
        userService.delete(id);
        return new ModelAndView("redirect:/users");
    }
    //доработать контроллеры

    @GetMapping(path = "/{id}/roles", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView showUserRoles(@PathVariable("id") int id, Model model, @ModelAttribute("role") Role role) {
        List<Role> roleList = roleService.findAll();
        roleList.add(new Role("New role..."));
        model.addAttribute("roles", roleList);
        model.addAttribute("user", userService.findOne(id));
        return new ModelAndView("users/roles");
    }

    @PatchMapping(path = "/{id}/roles", produces = MediaType.TEXT_HTML_VALUE)
    public RedirectView newUsersRole(@PathVariable("id") int id,
                                     @ModelAttribute("role") Role role, RedirectAttributes attributes) {
        if (role.getId() == 0) {
            attributes.addAttribute("userId", id);
            return new RedirectView("/roles/new");
        }
        userService.addRole(id, role.getId());
        return new RedirectView("/users");
    }


}
