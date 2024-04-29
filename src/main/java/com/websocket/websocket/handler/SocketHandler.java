package com.websocket.websocket.handler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Slf4j
public class SocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    //최초 연결
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("최초 연결 / {}", session.getHandshakeHeaders());

        final String sessionID = session.getId();
        final String eneterdMessage = sessionID + "님이 입장하셨습니다.";
        sessions.put(sessionID, session);

        sessions.values().forEach((s) -> {
            try {
                if (!s.getId().equals(sessionID) && s.isOpen() && session.getHandshakeHeaders().containsKey("chat")){
                    s.sendMessage(new TextMessage(eneterdMessage));
                }

            }catch (IOException e){}
        });
    }

    //양방향 데이터 통신시 call됨.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("양방향 통신 연결 {}", session.toString() );

        final String sessionID = session.getId();
        sessions.values().forEach((s) -> {
            try {
                if (!s.getId().equals(sessionID) && s.isOpen() && session.getHandshakeHeaders().containsKey("chat")){
                    s.sendMessage(message);
                }

            }catch (IOException e){
                throw new RuntimeException(e);
            }
        });
    }

    //웹소켓 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("통신 종료");
        final String sessionID = session.getId();
        final String leftMessage = sessionID + "님이 퇴장하셨습니다.";

        sessions.values().forEach((s) -> {
            try {
                if (!s.getId().equals(sessionID) && s.isOpen() && session.getHandshakeHeaders().containsKey("chat")){
                    s.sendMessage(new TextMessage(leftMessage));
                }
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        });
        super.afterConnectionClosed(session, status);
    }

    //에러 발생시
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("통신 중 오류 발생.");
        super.handleTransportError(session, exception);
    }
}
