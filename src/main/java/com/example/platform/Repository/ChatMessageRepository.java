package com.example.platform.Repository;

import com.example.platform.Model.ChatMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageModel, Long> {
    List<ChatMessageModel> findBySenderIdAndReceiverIdOrderByTimestamp(Long senderId, Long receiverId);


}
