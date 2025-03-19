package com.librarymanager.controller;

import com.librarymanager.model.Role;
import com.librarymanager.model.User;
import com.librarymanager.service.RoleService;
import com.librarymanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/new")
    @Operation(summary = "Create new user", description = "Form to create a new user")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/create_user";
    }

    @PostMapping
    @Operation(summary = "Save new user", description = "Save a new user to the database")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam List<Long> roleIds) {
        userService.saveUser(user, roleIds);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    @Operation(summary = "Edit user", description = "Form to edit an existing user")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/edit_user";
    }

    @PostMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user in the database")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, @RequestParam List<Long> roleIds) {
        userService.updateUser(id, user, roleIds);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "Delete user", description = "Delete an existing user from the database")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}