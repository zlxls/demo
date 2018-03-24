package com.zlxls.download;

import com.common.model.Chujian;
import com.common.model.TableBingzoon;
import com.common.model.TableChuanbo;
import com.common.model.TableCome;
import com.common.model.TableComezoon;
import com.common.model.TableCountryzoon;
import com.common.model.TableDanyuanzoon;
import com.common.model.TableFengxian;
import com.common.model.TableGuocheng;
import com.common.model.TableYunshuzoon;
import com.common.model.TableZdzoon;
import com.common.model.TableZoon;
import com.common.model.Tables;
import com.jfinal.plugin.activerecord.Db;
import com.zlxls.util.Validate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 调查表中所有附表的需要导出的查询信息在这里生成
 * 用于将相关表查询到的数据和字段对应生成到map集合中
 * 1，根据表名获取表的所有字段信息
 * 2，根据表类型生成导出word的名称
 * 3，生成主表tables的map键值对
 * 4，生成附表table_的map键值对，并且存入list集合当中
 * @ClassNmae：Validate   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class TableMaps {
    /**
     * 查找对应表中所有的字段用来迭代
     * 注意：需要过滤掉系统表information_schema查询出的相关字段
     * @param table_name
     * @return 字段对应集合
    */
    public List getColumnList(String table_name) {
        return Db.query("select column_name from information_schema.columns where table_name =? AND TABLE_SCHEMA <> 'information_schema'",table_name);
    }
    /**
     * 查找对应表中所有的字段用来迭代
     * 注意：需要过滤掉系统表information_schema查询出的相关字段
     * @param table_name
     * @param firstCharacter
     * @return 字段对应集合,除去id主键
    */
    public List getColumnList(String table_name,String firstCharacter) {
        String sql ="select column_name from information_schema.columns where table_name =? AND TABLE_SCHEMA <> 'information_schema' and  column_name<>? and column_name<>?";
        if(!table_name.equals("tables")){
            return Db.query(sql,table_name,firstCharacter+"_id",firstCharacter+"_tableid");
        }else{
            return Db.query(sql,table_name,firstCharacter+"_id",firstCharacter+"_tid");
        }
    }
    /**
     * 获取流行病学调查表六个word名
     * @param type
     * @return String
    */    
    public String getWordName(int type) {
        String name="紧急流行病学调查表";
        switch (type) {
            case 1:name="附表1_反刍动物(牛、羊、骆驼、鹿)(病)紧急流行病学调查表";
                break;
            case 2:name="附表2_猪(病)紧急流行病学调查表";
                break;
            case 3:name="附表3_禽(鸡、鸭、鹅)(病)紧急流行病学调查表";
                break;
            case 4:name="附表4_农贸市场、畜禽批发市场(病)紧急流行病学调查表";
                break;
            case 5:name="附表5_运输途中(病)紧急流行病学调查表";
                break;
            case 6:name="附表6_屠宰场(点)(病)紧急流行病学调查表";
                break;
        }
        return name;
    }
    /**
     * 获取检疫证明word中疫苗名称对应项
     * @param str
     * @return String
    */ 
    public String getYimiaoName(String str) {
        String name="口蹄疫□ 高致病性猪蓝耳病□ 猪瘟□ 高致病性禽流感□\n其他免疫病种:"+str;
        switch (str) {
            case "口蹄疫":
                name="口蹄疫■ 高致病性猪蓝耳病□ 猪瘟□ 高致病性禽流感□\n其他免疫病种:";
                break;
            case "高致病性猪蓝耳病":
                name="口蹄疫□ 高致病性猪蓝耳病■ 猪瘟□ 高致病性禽流感□\n其他免疫病种:";
                break;
            case "猪瘟":
                name="口蹄疫□ 高致病性猪蓝耳病□ 猪瘟■ 高致病性禽流感□\n其他免疫病种:";
                break;
            case "高致病性禽流感":
                name="口蹄疫□ 高致病性猪蓝耳病□ 猪瘟□ 高致病性禽流感■\n其他免疫病种:";
                break;
            default: 
                break;
        }
        return name;
    }
    /**
     * 将检疫证明word中导出编号统一格式化
     * 1，保留七位编号
     * 2，多余的截取后七位
     * 3，不足的前位补零
     * @param str
     * @return 7位字符串
    */ 
    public String getCardNumber(String str) {
        if(str.length()>7){
            str= str.substring(str.length()-7);//截取后七位
        }else if(str.length()<7){
            switch (str.length()) {
                case 6:
                    str="0"+str;
                    break;
                case 5:
                    str="00"+str;
                    break;
                case 4:
                    str="000"+str;
                    break;
                case 3:
                    str="0000"+str;
                    break;
                case 2:
                    str="00000"+str;
                    break;
                case 1:
                    str="000000"+str;
                    break;
            }
        }
        return str;
    }
    /**
     * 获取流行病学主表一条数据的所有键值 tables表
     * @param tables
     * @return 返回数据字段和数据列对应的键值对集合
    */ 
    public Map<String,Object> getTables(Tables tables) {
        List columnList = getColumnList("tables");
        
        System.err.println(columnList);
        Map<String,Object> map = new HashMap<>();
        columnList.stream().forEach((columnList1) -> {
            String column = columnList1.toString();
            Object value = tables.get(column);
            map.put(column, Validate.isNull(value)?"":value);//全部提升为String，否则数据会四舍五入
        });
        return map;
    }
    /**
     * 获取初检证明一条数据的所有键值 Chujian表
     * @param tables
     * @return 返回数据字段和数据列对应的键值对集合
    */     
    public Map<String,Object> getTables(Chujian tables) {
        List columnList = getColumnList("chujian");
        Map<String,Object> map = new HashMap<>();
        columnList.stream().forEach((columnList1) -> {
            String column = columnList1.toString();
            Object value = tables.get(column);
            map.put(column, Validate.isNull(value)?"":value);//全部提升为String，否则数据会四舍五入
        });
        return map;
    }
    /**
     * 获取流行病学关联表对应数据列
     * 1，根据关联主表id查询数据列
     * 2，将数据字段和数据列对应插入集合中
     * @param table_name
     * @param table_field_name
     * @param tables_id
     * @return 数据字段和数据列(多记录)对应的键值对集合
    */    
    public List<Map<String,Object>> getList(String table_name,String table_field_name,String tables_id) {
        List<Map<String,Object>> mapList = new ArrayList<>();  
        List columnList = getColumnList(table_name);
        switch (table_name){
            case "tables":
                List<Tables> tables=Tables.dao.find("select * from "+table_name+" where "+table_field_name+"=?",tables_id);
                tables.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_bingzoon":
                List<TableBingzoon> table_bingzoon=TableBingzoon.dao.find("select * from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_bingzoon.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_chuanbo":
                List<TableChuanbo> table_chuanbo=TableChuanbo.dao.find("select *,FROM_UNIXTIME(c_time/1000,'%Y/%m/%d %H:%i:%s') as c_time from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_chuanbo.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_come":
                List<TableCome> table_come=TableCome.dao.find("select * from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_come.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_comezoon":
                List<TableComezoon> table_comezoon=TableComezoon.dao.find("select * from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_comezoon.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_countryzoon":
                List<TableCountryzoon> table_countryzoon=TableCountryzoon.dao.find("select * from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_countryzoon.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_danyuanzoon":
                List<TableDanyuanzoon> table_danyuanzoon=TableDanyuanzoon.dao.find("select *,FROM_UNIXTIME(d_z_time/1000,'%Y/%m/%d %H:%i:%s') as d_z_time from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_danyuanzoon.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_fengxian":
                List<TableFengxian> table_fengxian=TableFengxian.dao.find("select *,FROM_UNIXTIME(f_time/1000,'%Y/%m/%d %H:%i:%s') as f_time from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_fengxian.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_guocheng":
                List<TableGuocheng> table_guocheng=TableGuocheng.dao.find("select * from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_guocheng.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_yunshuzoon":
                List<TableYunshuzoon> table_yunshuzoon=TableYunshuzoon.dao.find("select *,FROM_UNIXTIME(y_time/1000,'%Y/%m/%d %H:%i:%s') as y_time from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_yunshuzoon.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_zdzoon":
                List<TableZdzoon> table_zdzoon=TableZdzoon.dao.find("select *,FROM_UNIXTIME(z_time/1000,'%Y/%m/%d %H:%i:%s') as z_time from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_zdzoon.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
            case "table_zoon":
                List<TableZoon> table_zoon=TableZoon.dao.find("select * from "+table_name+" where "+table_field_name+"=?",tables_id);
                table_zoon.stream().map((list) -> {
                    Map<String,Object> map = new HashMap<>();
                    columnList.stream().forEach((columnList1) -> {
                        map.put(columnList1.toString(), list.get(columnList1.toString()));
                    });
                    return map;
                }).forEach((map) -> {  
                    mapList.add(map);
                });
                break;
        }
        return mapList;
    }
}
