package com.example.platform.Repository;

import com.example.platform.Model.OnOffModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnOffRepository extends JpaRepository<OnOffModel, Long> {
}
