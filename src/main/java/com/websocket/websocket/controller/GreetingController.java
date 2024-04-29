package com.websocket.websocket.controller;

import com.websocket.websocket.object.Greeting;
import com.websocket.websocket.object.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message)throws Exception{
        Thread.sleep(1000);
        return new Greeting("hello, "+ HtmlUtils.htmlEscape(message.getName())+ "!");
    }

}
