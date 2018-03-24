package com.zlxls.util;

import com.common.model.Message;
import com.common.model.MessageSend;
import com.jfinal.plugin.activerecord.Db;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程定时执行操作类
 * @ClassNmae：MyRunnable   
 * @author zlx-雄雄
 * @date    2017-9-28 17:47:17
 * 
 */
public class RunnableUtil {
    /**
     * 默认构造函数开启定时任务判断推送消息
     */
    public RunnableUtil(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        /** 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间,第四个参数为时间类型，作用于第二个，第三个参数*/
        service.scheduleAtFixedRate(runnable, 0,1, TimeUnit.DAYS);
    }
    /**
     * 定时任务判断推送消息
     */
    private final Runnable runnable = () -> {
        System.err.println("*************************BEGIN推送消息定时任务*************************");
        try {
            messageRun();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        System.err.println("*************************END推送消息定时任务***************************");
    };
    /**
     * 消息定时执行方法<br>
     * 1，获取未推送，并且时间到的消息列表
     * 2，将消息加入消息推送队列
     * 3，将消息更新为以推送状态
     * @throws ParseException 
     */
    private void messageRun() throws ParseException {
        
        String sql = "select m_id,m_userid from message where m_status=0 and m_type=2 and m_starttime < ?";
        
        Message.dao.find(sql,DateUtil.getTodayToLong(0)).stream().forEach((message) -> {
            
            new MessageSend().set("ms_messageid", message.getMId()).set("ms_userid", message.getMUserid()).set("ms_type", 2).save();
            
            Db.update("update message set m_status=1 where m_id=?",message.getMId());
            
        });
    }
}
