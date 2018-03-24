package com.zlxls.util;

//package com.zzz.util;
//
////import com.common.model.TMessage;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import org.json.simple.JSONObject;
//
//@ServerEndpoint("/websocket.ws")
//public class Chat {
//
//    /**
//     * 连接对象集合
//     */
//    private static final Map<Integer, Session> connections = new HashMap<>();
//
//    /**
//     * 打开连接
//     *
//     * @param session
//     */
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("++++++++++++sessionID:" + session.getId());
//    }
//
//    /**
//     * 关闭连接
//     *
//     * @param session
//     */
//    @OnClose
//    public void onClose(Session session) {
//        System.out.println("++++++++++++Close sessionID:" + session.getId());
//        Integer key = 0;
//        for (Map.Entry<Integer, Session> entry : connections.entrySet()) {
//            if (entry.getValue().getId().equals(session.getId())) {
//                key = entry.getKey();
//            }
//        }
//        if (key > 0) {
//            connections.remove(key);
//        }
//        System.out.println("++++++++++++ids:" + connections.keySet().toString());
//        Chat.broadCast(buildCallBackMessge("logout", connections.keySet().toString()));
//    }
//
//    /**
//     * 接收信息
//     *
//     * @param message
//     * @param session
//     * @throws org.json.JSONException
//     * @throws java.io.IOException
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) throws JSONException, IOException {
//        System.out.println("++++++++++++++message:" + message);
//        /*
//         {
//         action:     动作,
//         data:{}       数据,
//         user:{userid:id,username:名称}       用户
//         }
//         */
//        JSONObject jSONObject = new JSONObject(message);
//        switch (jSONObject.getString("action")) {
//            case "login":
//                JSONObject user = jSONObject.getJSONObject("user");
//                connections.put(user.getInt("userid"), session);
//                session.getBasicRemote().sendText(buildTaskMessge(jSONObject.getInt("taskKey") + "", connections.keySet().toString()));
//                Chat.broadCast(buildCallBackMessge("login", connections.keySet().toString()));
//                break;
//            case "message":
////                try {
////                    JSONObject messagedata = jSONObject.getJSONObject("data");
////                    TMessage tMessage = new TMessage();
////                    tMessage.set("content", messagedata.getString("content"));
////                    tMessage.set("fromuid", messagedata.getInt("fromuid"));
////                    tMessage.set("fromuname", messagedata.getString("fromuname"));
////                    tMessage.set("createtime", System.currentTimeMillis() / 1000);
////                    tMessage.set("touid", messagedata.getInt("touid"));
////                    tMessage.set("touname", messagedata.getString("touname"));
////                    if (tMessage.save()) {
////                        Map<String, Object> result = new HashMap<>();
////                        result.put("success", true);
////                        result.put("message", tMessage);
////                        session.getBasicRemote().sendText(buildTaskMessge(jSONObject.getInt("taskKey") + "", JsonKit.toJson(result)));
////                        Session toSession = connections.get(tMessage.getTouid());
////                        if (toSession == null || !toSession.isOpen()) {
////                            Jpush.sendMessage(tMessage.getTouid() + "", tMessage.getFromuname() + "给你发送了消息,请在通知里面查看");
////                        } else {
////                            toSession.getBasicRemote().sendText(buildCallBackMessge("message", JsonKit.toJson(tMessage)));
////                        }
////                    } else {
////                        Map<String, Object> result = new HashMap<>();
////                        result.put("success", false);
////                        result.put("message", "消息发送失败");
////                        session.getBasicRemote().sendText(buildTaskMessge(jSONObject.getInt("taskKey") + "", JsonKit.toJson(result)));
////                    }
////                } catch (JSONException | IOException e) {
////                    System.out.println("+++++++++++++++++++++++++++错误：" + e.getLocalizedMessage());
////                    Map<String, Object> result = new HashMap<>();
////                    result.put("success", false);
////                    result.put("message", "消息发送失败");
////                    session.getBasicRemote().sendText(buildTaskMessge(jSONObject.getInt("taskKey") + "", JsonKit.toJson(result)));
////                }
//                break;
//        }
//    }
//
//    /**
//     * 错误信息响应
//     *
//     * @param throwable
//     */
//    @OnError
//    public void onError(Throwable throwable) {
//        System.out.println(throwable.getMessage());
//    }
//
//    /**
//     * 发送或广播信息
//     *
//     * @param message
//     */
//    private static void broadCast(String message) {
//        System.out.println("++++++++++++++++++++发送广播内容:" + message);
//        connections.entrySet().stream().forEach((entry) -> {
//            try {
//                synchronized (entry) {
//                    System.out.println("++++++++++++++++++++发送广播" + entry.getKey());
//                    entry.getValue().getBasicRemote().sendText(message);
//                }
//            } catch (IOException e) {
//                System.out.println("++++++++++++++++++++发送广播异常" + e.getMessage());
//                connections.remove(entry.getKey());
//                try {
//                    entry.getValue().close();
//                } catch (IOException e1) {
//                    System.out.println("++++++++++++++++++++关闭连接异常" + e1.getMessage());
//                }
//            }
//        });
//    }
//
//    private static String buildCallBackMessge(String callback, String message) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("var callBack=self.");
//        sb.append(callback);
//        sb.append(";resultData=");
//        sb.append(message);
//        sb.append(";");
//        return sb.toString();
//    }
//
//    private static String buildTaskMessge(String taskKey, String message) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("var taskKey=");
//        sb.append(taskKey);
//        sb.append(";resultData=");
//        sb.append(message);
//        sb.append(";");
//        return sb.toString();
//    }
//}
