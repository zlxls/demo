package com.zlxls.util;

import com.bcloud.msg.http.HttpSender;
import com.common.model.Setting;
import com.jfinal.kit.PropKit;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * 短信发送处理类
 * @ClassNmae：Sendsms   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class Sendsms {
    private static final Map smsMessage = new HashMap<>();
    /**
     * 发送短信后，处理返回状态方法
     */
    private static void smsMessage(){
        smsMessage.put("0","提交成功");
        smsMessage.put("101","无此用户");
        smsMessage.put("102","密码错");
        smsMessage.put("103","提交过快（提交速度超过流速限制）");
        smsMessage.put("104","系统忙（因平台侧原因，暂时无法处理提交的短信）");
        smsMessage.put("105","敏感短信（短信内容包含敏感词）");
        smsMessage.put("106","消息长度错（>536或<=0）");
        smsMessage.put("107","包含错误的手机号码");
        smsMessage.put("108","手机号码个数错（群发>50000或<=0;单发>200或<=0）");
        smsMessage.put("109","无发送额度（该用户可用短信数已使用完）");
        smsMessage.put("110","不在发送时间内");
        smsMessage.put("111","超出该账户当月发送额度限制");
        smsMessage.put("112","无此产品，用户没有订购该产品");
        smsMessage.put("113","extno格式错（非数字或者长度不对）");
        smsMessage.put("115","自动审核驳回");
        smsMessage.put("116","签名不合法，未带签名（用户必须带签名的前提下）");
        smsMessage.put("117","IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
        smsMessage.put("118","用户没有相应的发送权限");
        smsMessage.put("119","用户已过期");
        smsMessage.put("120","内容不在白名单模板中");
        smsMessage.put("121","相同内容短信超限");
    }
    /**
     * 发送短信方法
     * @param content 短信类容
     * @param mobile 号码，使用逗号隔开
     * @return 
     */
    public static String sendMessage(String content, String mobile) {
        smsMessage();
        /**
         *单发的URL地址为：http://http://118.178.188.81//msg/HttpSendSM，
         * 群发的URL地址为：http://http://118.178.188.81//msg/HttpBatchSendSM。
         * HttpSender.send单发
         * HttpSender.batchSend群发
         */
        
        Setting setting = Setting.dao.findById(1);
        String uri = PropKit.get("sms_url");//应用地址比如：【鼎华】祝您周末愉快。退订回T
        String account = setting.get("sms_name");//账号
        String pswd = setting.get("sms_pass");//密码
        
        content ="【"+PropKit.get("sms_title")+"】"+ content +"退订回T";//短信内容
        boolean needstatus = true;//是否需要状态报告，需要true，不需要false
        String product = "";//产品ID
        String extno = "";//扩展码
        try { 
            String returnString = HttpSender.batchSend(uri, account, pswd, mobile, content, needstatus, product, extno);
            String sms_return_mes = returnString.split(",")[1].split("\n")[0];
            System.out.println(smsMessage.get(sms_return_mes).toString());
            return smsMessage.get(sms_return_mes).toString();
            //TODO 处理返回值,参见HTTP协议文档
        } catch (Exception e) {
            return "发送失败";
        }
    }
}
