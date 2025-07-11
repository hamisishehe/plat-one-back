package com.example.platform.Repository;

import com.example.platform.Model.TaskModel;
import com.example.platform.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {

}
