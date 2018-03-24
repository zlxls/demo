package com.zlxls.util;

//package com.zzz.util;
//
//import cn.jiguang.common.resp.APIConnectionException;
//import cn.jiguang.common.resp.APIRequestException;
//import cn.jpush.api.push.PushResult;
//import cn.jpush.api.push.model.Message;
//import cn.jpush.api.push.model.Platform;
//import cn.jpush.api.push.model.PushPayload;
//import cn.jpush.api.push.model.audience.Audience;
//import cn.jpush.api.push.model.audience.AudienceTarget;
//import cn.jpush.api.push.model.notification.Notification;
//import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;
//import com.common.DefaultConfig;
//
///**
// *
// * @author root
// */
//public class Jpush {
//
//    //快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
//    public static PushPayload buildPushObject_all_all_alert() {
//        return PushPayload.alertAll(ALERT);
//    }
//
//    //构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
//    public static PushPayload buildPushObject_all_alias_alert(String alias) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias(alias))
//                .setNotification(Notification.alert(ALERT))
//                .build();
//    }
//    public static boolean sendMessage(String alias,String content) {
//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload =PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias(alias))
//                .setNotification(Notification.alert(content))
//                .build();
//        try {
//            PushResult result = DefaultConfig.jpushClient.sendPush(payload);
//            System.err.println("Got result - " + result);
//            return true;
//
//        } catch (APIConnectionException e) {
//            // Connection error, should retry later
//            System.err.println("Connection error, should retry later");
//
//        } catch (APIRequestException e) {
//            // Should review the error, and fix the request
//            System.err.println("Should review the error, and fix the request");
//            System.err.println("HTTP Status: " + e.getStatus());
//            System.err.println("Error Code: " + e.getErrorCode());
//            System.err.println("Error Message: " + e.getErrorMessage());
//        }
//        return false;
//    }
//
//    public static boolean sendMessage(String alias,String content, Integer type) {
//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.alias(alias))
//                        .build())
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(content)
//                        .addExtra("type", type)
//                        .build())
//                .build();
//        try {
//            PushResult result = DefaultConfig.jpushClient.sendPush(payload);
//            System.err.println("Got result - " + result);
//            return true;
//
//        } catch (APIConnectionException e) {
//            // Connection error, should retry later
//            System.err.println("Connection error, should retry later");
//
//        } catch (APIRequestException e) {
//            // Should review the error, and fix the request
//            System.err.println("Should review the error, and fix the request");
//            System.err.println("HTTP Status: " + e.getStatus());
//            System.err.println("Error Code: " + e.getErrorCode());
//            System.err.println("Error Message: " + e.getErrorMessage());
//        }
//        return false;
//    }
//
//}
