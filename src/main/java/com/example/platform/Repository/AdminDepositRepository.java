package com.example.platform.Repository;


import com.example.platform.Model.AdminDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDepositRepository extends JpaRepository<AdminDeposit, Long> {
}
