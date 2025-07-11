package com.example.platform.Controller.user;


import com.example.platform.Model.ProductModel;
import com.example.platform.Model.TaskModel;
import com.example.platform.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {


    @Autowired
    private TaskService taskService;

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/products/{userId}")
    public List<ProductModel> getAvailableProducts(@PathVariable Long userId) {
        System.out.println(userId);
        return taskService.getAvailableProducts(userId);
    }

//    @PostMapping("/complete/{userId}/{productId}")
//    public TaskModel completeTask(@PathVariable Long userId, @PathVariable Long productId) {
//        return "";
//    }


}
