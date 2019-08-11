package com.codegym.service;

import com.codegym.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<Task> findAllByStatus(String status, Pageable pageable);
    Page<Task>findAllByNameStartsWith(String name, Pageable pageable);

    Page<Task>finAll(Pageable pageable);

    Task findById(Long id);

    void save(Task task);

    void remove(Long id);
}
