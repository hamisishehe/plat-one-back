package com.example.platform.Repository;

import com.example.platform.Model.TaskProductModel;
import com.example.platform.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskProductRepository extends JpaRepository<TaskProductModel, Long> {

    List<TaskProductModel> findByUser(UserModel user);

    List<TaskProductModel> findAllByUser(UserModel user);

    @Query("SELECT t FROM TaskProductModel t WHERE t.user = :user AND t.finish_time < :now")
    List<TaskProductModel> findExpiredTasksByUser(
            @Param("user") UserModel user,
            @Param("now") LocalDateTime now);


    @Query("SELECT COUNT(t) FROM TaskProductModel t WHERE t.user = :user AND t.finish_time < :now")
    long countExpiredTasksByUser(
            @Param("user") UserModel user,
            @Param("now") LocalDateTime now);



}
