package com.example.platform.Repository;



import com.example.platform.Model.UserModel;
import com.example.platform.Model.WithdrawModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WithdrawRepository extends JpaRepository<WithdrawModel, Long> {


    List<WithdrawModel> findByUser(UserModel user);

    List<WithdrawModel> findAllByOrderByIdAsc();

    @Query("SELECT w, u FROM WithdrawModel w JOIN w.user u")
    List<Object[]> findAllWithdrawsWithUser();

    List<WithdrawModel> findAllByStatus(WithdrawModel.Status status);
}
