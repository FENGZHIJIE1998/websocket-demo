package com.itaem.dazhi.websocketdemo.modules.websocket.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.itaem.dazhi.websocketdemo.modules.websocket.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;


/**
 * @author Dazhi
 * @date 2019/4/25 20:50
 */


@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocket {
    /**
     * String : 用户ID
     * Session： 回话
     */
    public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        log.info("====>WebSocketService onOpen: " + userId);
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<String, Session>();
        }
        sessionMap.put(userId, session);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        log.info("====>WebSocketService OnClose: " + userId);
        sessionMap.remove(userId);
    }

    @OnMessage
    public void onMessage(@PathParam("userId") String userId, Session session, String message) {
        log.info("====>WebSocketService OnMessage: " + message);
        MessageVo vo = JSON.parseObject(message, MessageVo.class);
        if (vo.getComsumerId() == null || vo.getComsumerId().isEmpty()) {
            toAll(message);
        } else {
            one2one(vo, message);
        }
    }

    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }

    /**
     * 点对点
     */
    public static void one2one(MessageVo vo, String message) {
        Session session = sessionMap.get(vo.getComsumerId());
        if(session==null){
            log.info("消息消费者不存在");
        }
        session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发
     */
    public static void toAll(String message) {
        for (Session session : sessionMap.values()) {
            session.getAsyncRemote().sendText(message);
        }

    }
}