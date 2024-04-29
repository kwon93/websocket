package com.websocket.websocket.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.support.SpringFactoriesLoader;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HelloMessage {

    private Long senderId;
    private String content;

}
