package com.codegym.controller;

import com.codegym.model.Task;
import com.codegym.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/home")
    public ModelAndView manageTask(@RequestParam("sortByStatus") Optional<String> sortByStatus, @RequestParam("searchName") Optional<String> searchName, @PageableDefault(size = 5) Pageable pageable) {
        Page<Task> tasks;
        if (searchName.isPresent()) {
            tasks = taskService.findAllByNameStartsWith(searchName.get(), pageable);
        } else if (sortByStatus.isPresent()) {
            tasks = taskService.findAllByStatus(sortByStatus.get(), pageable);
        } else {
            tasks = taskService.finAll(pageable);
        }
        long count = tasks.getTotalElements();
        ModelAndView modelAndView = new ModelAndView("task/home", "tasks", tasks);
        modelAndView.addObject("count", count);
        return modelAndView;
    }

    @GetMapping("/create-task")
    public ModelAndView showCreateForm() {
        return new ModelAndView("task/create", "task", new Task());
    }

    @PostMapping("/save-task")
    public ModelAndView saveTask(@Validated @ModelAttribute("task") Task task, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("task/create");
        } else {
            taskService.save(task);
            ModelAndView modelAndView = new ModelAndView("task/create", "task", new Task());
            modelAndView.addObject("message", " New Task created successfully");
            return modelAndView;
        }
    }

    @GetMapping("/edit-task/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Task task = taskService.findById(id);
        if (task != null) {
            return new ModelAndView("task/edit", "task", task);
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/edit-task")
    public ModelAndView updateTask(@Validated @ModelAttribute("task") Task task, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("task/edit  ");
        } else {
            taskService.save(task);
            ModelAndView modelAndView = new ModelAndView("redirect:/home");
            modelAndView.addObject("message", "Update task successfully ");
            return modelAndView;
        }
    }

    @GetMapping("/delete-task/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Task task = taskService.findById(id);
        if (task != null) {
            return new ModelAndView("task/delete", "task", task);
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/delete-task")
    public ModelAndView deleteTask(@ModelAttribute("task") Task task) {
        taskService.remove(task.getId());
        return new ModelAndView("redirect:/home", "message", "Removed task successfully " + task.getName());
    }
}
