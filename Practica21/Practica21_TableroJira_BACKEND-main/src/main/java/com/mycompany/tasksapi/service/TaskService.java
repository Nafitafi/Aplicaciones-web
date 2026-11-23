package com.mycompany.tasksapi.service;

import com.mycompany.tasksapi.model.Task;
import com.mycompany.tasksapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        if (task.getText() == null || task.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El texto de la tarea no puede estar vacío.");
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("El ID de la tarea es requerido para actualizar.");
        }
        if (task.getText() == null || task.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El texto de la tarea no puede estar vacío.");
        }
        if (!taskRepository.existsById(task.getId())) {
            throw new IllegalArgumentException("La tarea con ID " + task.getId() + " no existe.");
        }
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("La tarea con ID " + id + " no existe.");
        }
        taskRepository.deleteById(id);
    }
}
