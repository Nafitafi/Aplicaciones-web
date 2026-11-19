package com.mycompany.tasksapi.controller;

import com.mycompany.tasksapi.model.Task;
import com.mycompany.tasksapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "*") // CORS setting for all origins
public class TaskController {

    @Autowired
    private TaskService taskService;

    // ---------------------------------------------------------
    // GET: Obtener lista de tareas
    // ---------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // ---------------------------------------------------------
    // POST: Crear una nueva tarea
    // ---------------------------------------------------------
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un error inesperado.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ---------------------------------------------------------
    // PUT: Actualizar una tarea (Editar texto o marcar completado)
    // ---------------------------------------------------------
    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(task);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ---------------------------------------------------------
    // DELETE: Eliminar una tarea por ID
    // ---------------------------------------------------------
    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam(required = false) Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Falta el parámetro ID.");
            }
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage() != null ? e.getMessage() : "ID inválido o no proporcionado.");
            return ResponseEntity.badRequest().body(error);
        }
    }
}
