package com.example.platform.Controller.user;


import com.example.platform.Model.ProductModel;
import com.example.platform.Model.TaskModel;
import com.example.platform.Model.TaskProductModel;
import com.example.platform.Service.TaskProductService;
import com.example.platform.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {


    @Autowired
    private TaskProductService taskProductService;

    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @PostMapping("/doTask")
    public ResponseEntity<String> doTask(@RequestBody Map<String, String> TasksDetails) {
        Long userId = Long.parseLong(TasksDetails.get("userId"));
        int amount = Integer.parseInt(TasksDetails.get("amount"));

        System.out.println(amount);
        String task = taskProductService.DoTask(userId, amount);
        return ResponseEntity.ok(task);
    }




    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/get-tasks/{userId}")
    public ResponseEntity<Optional<TaskProductModel>> GetTasks(@PathVariable Long userId) {

        System.out.println(userId);

        Optional<TaskProductModel> tasks = taskProductService.GetTask(userId);

        return ResponseEntity.ok(tasks);
    }



    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/check-tasks/{userId}")
    public ResponseEntity<Boolean> checkTask(@PathVariable Long userId) {

        System.out.println(userId);

        Boolean tasks = taskProductService.hasExpiredTask(userId);

        System.out.println(tasks);

        return ResponseEntity.ok(tasks);
    }



    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/get-tasks")
    public ResponseEntity<List<TaskProductModel>> getAllTask() {
        List<TaskProductModel> tasks = taskProductService.GetAllTask();
        return ResponseEntity.ok(tasks);
    }




}
