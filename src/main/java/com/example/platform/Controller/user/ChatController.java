package com.example.platform.Controller.user;

import com.example.platform.Model.ChatMessageModel;
import com.example.platform.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatRepo;

    @PostMapping("/send")
    public ChatMessageModel sendMessage(@RequestBody ChatMessageModel message) {
        message.setTimestamp(LocalDateTime.now());
        message.setReplied(false);
        return chatRepo.save(message);
    }

    @GetMapping("/history")
    public List<ChatMessageModel> getChatHistory(@RequestParam Long userId, @RequestParam Long adminId) {
        return chatRepo.findBySenderIdAndReceiverIdOrderByTimestamp(userId, adminId);
    }

//    @GetMapping("/unreplied-count")
//    public List<Map<String, Object>> getUnrepliedCounts(@RequestParam Long adminId) {
//        List<Object[]> result = chatRepo.findUnrepliedMessagesGroupedBySender(adminId);
//        List<Map<String, Object>> response = new ArrayList<>();
//
//        for (Object[] row : result) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("userId", row[0]);
//            map.put("count", row[1]);
//            response.add(map);
//        }
//
//        return response;
//    }

    @PostMapping("/mark-replied")
    public String markMessagesReplied(@RequestParam Long userId, @RequestParam Long adminId) {
        List<ChatMessageModel> messages = chatRepo.findBySenderIdAndReceiverIdOrderByTimestamp(userId, adminId);
        messages.forEach(m -> m.setReplied(true));
        chatRepo.saveAll(messages);
        return "Marked as replied";
    }
}
