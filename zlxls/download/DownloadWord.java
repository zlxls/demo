package com.zlxls.download;

import com.jfinal.kit.PathKit;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 *
 * word数据填充类
 * ftl命名方式统一使用table_数字
 * 例如：table_1 table_2,数字作为参数传递
 * @ClassNmae：DownloadWord   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 */
// 表所有字段集合
public class DownloadWord {
    /**             
     * 顺序bingzoon chuanbo come comezoon countryzoon danyuanzoon fengxian guocheng yunshuzoon zdzoon zoon
     * 参数类型全部按照字母顺序排列，在形参中根据字母顺序排列参数
     * @param dataMap
     * @param type
     * @param fileName
     * @param path
     * @param list1
     * @param list2
     * @param list3
     * @param list4
     * @param list5
     * @param list6
     * @throws java.io.UnsupportedEncodingException 
     */
    public static void downloadWord(Map<String, Object> dataMap,int type,String fileName,String path,List<Map<String,Object>> list1,List<Map<String,Object>> list2,List<Map<String,Object>> list3,List<Map<String,Object>> list4,List<Map<String,Object>> list5,List<Map<String,Object>> list6) throws UnsupportedEncodingException{
        DocumentHandler mdoc = new DocumentHandler();          
        //参数中的dataMap已经将主表中的键值数据获取
        //下面是关联表集合形式
        dataMap.put("zdzoons", list1); //公共方法
        switch (type){
            case 1:
            case 2:
            case 3:
                dataMap.put("chuanbos", list2); 
                dataMap.put("countryzoons", list3);
                dataMap.put("danyuanzoons", list4); 
                dataMap.put("guochengs", list5); 
                dataMap.put("zoons", list6); 
                break;
            case 4:
                dataMap.put("bingzoons", list2); 
                dataMap.put("comezoons", list3); 
                dataMap.put("fengxians", list4);
                dataMap.put("zoons", list5);  
                break;
            case 5:
                dataMap.put("countryzoons", list2);
                dataMap.put("yunshuzoons", list3); 
                break;
            case 6:
                dataMap.put("bingzoons", list2); 
                dataMap.put("comes", list3); 
                dataMap.put("countryzoons", list4);
                break;
            case 7://产地检疫初检证明
                break;
        }
        mdoc.createDoc(dataMap, PathKit.getWebRootPath()+File.separator+"download"+File.separator+path+File.separator,fileName,type);
    }
}
