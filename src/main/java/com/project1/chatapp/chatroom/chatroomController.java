package com.project1.chatapp.chatroom;

import org.springframework.beans.factory.annotation.Autowired;

import com.project1.chatapp.sessions.sessionService;
import com.project1.chatapp.user.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class chatroomController {
    @Autowired
    private chatroomService chatroomService;
    private sessionService sessionService;
    @PostMapping("/app/{session_id}/createChatroom")
    public ResponseEntity<String> createChatroom(
            @PathVariable String session_id,
            @RequestBody chatroomService.newGroup newGroup) {
        String chatId = chatroomService.createChatRoom(newGroup, session_id);
        return ResponseEntity.ok(chatId); // 👈 Trả về UUID của chatroom
    }

    @GetMapping("/app/{session_id}/loadchat")
    public List<chatroomService.chatroomInfo> loadChat(@PathVariable String session_id) {
        return chatroomService.listChatRoom(session_id);
    }

    @GetMapping("/app/{session_id}/{chat_id}/delete")
    public ResponseEntity<String> deleteChat(@PathVariable String session_id, @PathVariable String chat_id) {
        chatroomService.deleteChatRoom(chat_id, session_id);
        return ResponseEntity.ok("OK");
    }
    @GetMapping("/app/{session_id}/{chat_id}/{user_id}/add")
    public ResponseEntity<String> addToChatRoom(@PathVariable String session_id, @PathVariable String chat_id, @PathVariable String user_id) {
        try {
            chatroomService.addToChatRoom(session_id, chat_id, user_id);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/app/{session_id}/{chat_id}/changename")
    public ResponseEntity<String> changeName(@PathVariable String session_id, @PathVariable String chat_id, @RequestBody chatroomService.name name) {
        chatroomService.changeChatroomName(session_id,chat_id,name);
        return ResponseEntity.ok("OK");
    }
    @GetMapping("/app/{session_id}/{chat_id}/{user_id}/kick")
    public ResponseEntity<String> kickFromChatroom(@PathVariable String session_id,@PathVariable String chat_id,@PathVariable String user_id) {
        chatroomService.kickFromChatroom(session_id,chat_id,user_id);
        return ResponseEntity.ok("OK");
    }
    @GetMapping("/app/{session_id}/{chat_id}/listUser")
    public List<userService.userPublic> listUser(@PathVariable String session_id,@PathVariable String chat_id) {
        return chatroomService.listUsersInChatroom(session_id,chat_id);
    }
    
    @GetMapping("/app/chat/existBetween/{sessionid}/{userId2}")
    public ResponseEntity<String> getPrivateChatBetweenUsers(
            @PathVariable String sessionid,
            @PathVariable String userId2) {
        try {
            String chatId = chatroomService.getPrivateChatBetweenUsers(sessionid, userId2);
            return ResponseEntity.ok(chatId != null ? chatId : "");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error: " + e.getMessage());
        }
    }
}
