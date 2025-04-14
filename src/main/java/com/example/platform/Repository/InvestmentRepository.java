package com.example.platform.Repository;



import com.example.platform.Model.InvestmentModel;
import com.example.platform.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentModel, Long> {
    List<InvestmentModel> findAllByUser(UserModel user);
    List<InvestmentModel> findAllByOrderByUserId();

    @Query("SELECT i, u FROM InvestmentModel i JOIN i.user u")
    List<Object[]> findAllInvestmentsWithUserData();


    @Query("SELECT i, u FROM InvestmentModel i JOIN i.user u WHERE i.status = 'RUNNING'")
    List<Object[]> findAllRunningInvestmentsWithUserData();

    @Query("SELECT i, u FROM InvestmentModel i JOIN i.user u WHERE i.status = 'FINISHED'")
    List<Object[]> findAllFinishedInvestmentsWithUserData();


    Boolean findAllByUserAndStatus(UserModel user, InvestmentModel.InvestmentStatus status);

}