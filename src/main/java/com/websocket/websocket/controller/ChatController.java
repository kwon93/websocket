package com.websocket.websocket.controller;

import com.websocket.websocket.object.Greeting;
import com.websocket.websocket.object.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{chatroomId}/messages")
    public void chat(@DestinationVariable Long chatroomId, HelloMessage message)throws Exception{
        simpMessagingTemplate.convertAndSend("/queue/chat"+chatroomId, message.getContent());
    }

}
