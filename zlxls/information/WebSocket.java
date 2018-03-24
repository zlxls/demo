package com.zlxls.information;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket
 * @ClassNmae：WebSocket   
 * @author zlx-雄雄
 * @date    2017-8-29 11:21:13
 * 
 */
@ServerEndpoint("/websocket.ws")//这里需要加.ws
public class WebSocket {
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {

        // Print the client message for testing purposes
        System.out.println("session.getId()"+session.getId());
        System.out.println("session.getMaxBinaryMessageBufferSize()"+session.getMaxBinaryMessageBufferSize());
        System.out.println("session.getMaxIdleTimeout()"+session.getMaxIdleTimeout());
        System.out.println("收到消息: " + message);
        System.out.println("session: " + session);

        // Send the first message to the client
        session.getBasicRemote().sendText("收到您的消息: " + message);

        //Send 3 messages to the client every 5 seconds
        int sentMessages = 0;
        while (sentMessages < 3) {
            Thread.sleep(5000);
            session.getBasicRemote().sendText("正在发送的消息. Count: " + sentMessages);
            sentMessages++;
        }
        // Send a final message to the client
        session.getBasicRemote().sendText("This is the last server message");
    }

    @OnOpen
    public void onOpen() {
        System.out.println("服务已连接");
    }

    @OnClose
    public void onClose() {
        System.out.println("服务已关闭");
    }
}
