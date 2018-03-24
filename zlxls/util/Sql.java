package com.zlxls.util;
/**
 * 重复sql 提取工具类
 * 命名方式：get[表名][相关属性名]Sql
 * @ClassNmae：Sql   
 * @author zlx-雄雄
 * @date    2017-9-11 11:15:10
 * 
 */
public class Sql {
    /**
     * 根据id获取调查表数据sql语句
     * @return String sql
     */
    public static String getTablesRowSql(){
        String sql="select t.*,e.e_name as t_d_employname,e.e_phone as t_d_employphone,e.e_company as t_d_company";
        sql+=",FROM_UNIXTIME(t.t_d_firsttime/1000,'%Y/%m/%d %H:%i:%s') as t_d_firsttime";
        sql+=",FROM_UNIXTIME(t.t_d_gettime/1000,'%Y/%m/%d %H:%i:%s') as t_d_gettime";
        sql+=",FROM_UNIXTIME(t.t_d_addtime/1000,'%Y/%m/%d %H:%i:%s') as t_d_addtime";
        sql+=",FROM_UNIXTIME(t.t_d_addtime/1000,'%Y%m%d%H%i%s') as addtime";
        sql+=",FROM_UNIXTIME(t.t_zd_time/1000,'%Y/%m/%d %H:%i:%s') as t_zd_time";
        sql+=",FROM_UNIXTIME(t.t_j_qytime/1000,'%Y/%m/%d %H:%i:%s') as t_j_qytime";
        sql+=" from tables t left join employ e on e.e_id=t.t_d_employid where t.t_id=?";
        return sql;
    }
    /**
     * 根据id获取初检证明表数据sql语句
     * @return String sql
     */
    public static String getChujianRowSql(){
        String sql="select t.*,e.e_name as c_employname,c.c_name as c_customname,c.c_place,c.c_address as c_customaddress";
        sql+=",FROM_UNIXTIME(t.c_addtime/1000,'%Y年%m月%d日') as c_addtime";
        sql+=" from chujian t left join employ e on e.e_id=t.c_employid ";
        sql+=" left join custom c on c.c_id=t.c_customid where t.c_id=?";
        return sql;
    }
    /**
     * 个人统计<br>
     * 获取至今，前一个月看，前一个星期免疫牲畜数量以及疫苗使用数量<br>
     * @return String sql
     */
    public static String getMianyiCountSql1(){
        String sql = "select IFNULL(SUM(m_z_should";
        sql +="),0) as m_z_should,IFNULL(SUM(m_z_zhuwen),0) as m_z_zhuwen,IFNULL(SUM(m_z_koutiyi),0) as m_z_koutiyi,IFNULL(SUM(m_z_lanerbing";
        sql +="),0) as m_z_lanerbing,IFNULL(SUM(m_n_should),0) as m_n_should,IFNULL(SUM(m_n_koutiyi";
        sql +="),0) as m_n_koutiyi,IFNULL(SUM(m_y_should),0) as m_y_should,IFNULL(SUM(m_y_koutiyi),0) as m_y_koutiyi,IFNULL(SUM(m_y_xiaofancu";
        sql +="),0) as m_y_xiaofancu,IFNULL(SUM(m_q_jsum),0) as m_q_jsum,IFNULL(SUM(m_q_yesum),0) as m_q_yesum,IFNULL(SUM(m_q_qlg_jsum";
        sql +="),0) as m_q_qlg_jsum,IFNULL(SUM(m_q_qlg_yesum),0) as m_q_qlg_yesum,IFNULL(SUM(m_q_xcy_jsum),0) as m_q_xcy_jsum,IFNULL(SUM(m_q_xcy_yesum";
        sql +="),0) as m_q_xcy_yesum,IFNULL(SUM(m_qu_should),0) as m_qu_should,IFNULL(SUM(m_qu_kuangquanyimiao";
        sql +="),0) as m_qu_kuangquanyimiao from mianyi where m_type=1 and m_employid=?";
        return sql;
    }
    public static String getMianyiCountSql(){//同上，没有上面细
        String sql = "select IFNULL(SUM(m_z_should),0) as m_z_should,";
        sql +="IFNULL(SUM(m_z_zhuwen+m_z_lanerbing),0) as toufen,";
        sql+="IFNULL(SUM(m_z_koutiyi+m_n_koutiyi+m_y_koutiyi+m_y_xiaofancu+m_qu_kuangquanyimiao+m_q_qlg_jsum+m_q_qlg_yesum+m_q_xcy_jsum+m_q_xcy_yesum),0) as haosheng,";
        sql +="IFNULL(SUM(m_n_should),0) as m_n_should,";
        sql +="IFNULL(SUM(m_y_should),0) as m_y_should,";
        sql +="IFNULL(SUM(m_q_jsum+m_q_yesum),0) as m_q_should,";
        sql +="IFNULL(SUM(m_qu_should),0) as m_qu_should ";
        sql +=" from mianyi where m_type=1 and m_employid=?";
        return sql;
    }
}
