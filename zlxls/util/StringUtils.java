package com.zlxls.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 *
 * 字符串操作工具
 * @ClassNmae：StringUtils   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class StringUtils {
      /**
       * 过滤通过页面表单提交的字符  
       */
    
       private static final String[][] FilterChars={{"<","&lt;"},{">","&gt;"},{" ","&nbsp;"},{"\"","&quot;"},{"&","&amp;"},{"/","&#47;"},{"\\","&#92;"},{"\n","<br>"}};     
       /**
        * 过滤通过javascript脚本处理并提交的字符  
        */   
       private static final String[][] FilterScriptChars={{"\n","\'+\'\\n\'+\'"},{"\r"," "},{"\\","\'+\'\\\\\'+\'"},{"\'","\'+\'\\\'\'+\'"}};     
    
       /**   
        * 用特殊的字符连接字符串   
        * @param strings 要连接的字符串数组   
        * @param spilit_sign 连接字符   
        * @return 连接字符串   
        */    
       public static String stringConnect(String[] strings,String spilit_sign){     
         String str="";     
           for (String string : strings) {
               str += string + spilit_sign;
           }     
         return str;     
       }     
    
       /**   
        * 过滤字符串里的的特殊字符   
        * @param str 要过滤的字符串   
        * @return 过滤后的字符串   
        */    
       public static String stringFilter(String str){     
         String[] str_arr=stringSpilit(str,"");     
         for(int i=0;i<str_arr.length;i++){     
             for (String[] FilterChar : FilterChars) {
                 if (FilterChar[0].equals(str_arr[i])) {
                     str_arr[i] = FilterChar[1];
                 }
             }     
         }     
         return (stringConnect(str_arr,"")).trim();     
       }     
    
       /**   
        * 过滤脚本中的特殊字符（包括回车符(\n)和换行符(\r)）   
        * @param str 要进行过滤的字符串   
        * @return 过滤后的字符串   
        * 2004-12-21 闫   
        */    
        public static String stringFilterScriptChar(String str){     
            String[] str_arr=stringSpilit(str,"");     
            for(int i=0;i<str_arr.length;i++){     
                for (String[] FilterScriptChar : FilterScriptChars) {
                    if (FilterScriptChar[0].equals(str_arr[i])) {
                        str_arr[i] = FilterScriptChar[1];
                    }
                }     
            }     
            return(stringConnect(str_arr,"")).trim();     
        }     
        /**   
        * 将字符串数组转换为逗号链接的字符串，并且去掉最后一个逗号
        * @param county_arrays
        * @return 转换好的数据a,s,d,d,q   
        * 2017-07-11 zlx   
        */ 
        public static String StringArrayToOutLastCharacterString(String[] county_arrays){ 
            String promission;
            StringBuilder sb = new StringBuilder();
            for (String array : county_arrays) {
                sb.append(array).append(",");
            }
            promission = sb.toString();
            promission = promission.substring(0, promission.length()-1);  
            return promission;     
        }     
        /**   
        * 将字符串数组转换为逗号链接的字符串，并且去掉最后一个逗号,并且转换为带引号字符串
        * @param county_arrays
        * @return 转换好的数据'a','s','d','d','q'  
        * 2017-07-11 zlx   
        */ 
        public static String StringArrayToOutLastCharacterStringToString(String[] county_arrays){ 
            String promission;
            StringBuilder sb = new StringBuilder();
            for (String array : county_arrays) {
                sb.append(array).append("','");
            }
            promission = sb.toString();
            promission = "'"+promission.substring(0, promission.length()-2);  
            return promission;     
        }     
        /**   
        * 将逗号链接的字符串去掉最后一个逗号
        * @param string
        * @return 转换好的数据a,s,d,d,q   
        * 2017-08-14 zlx   
        */ 
        public static String StringToOutLastCharacter(String string){ 
            if(string.endsWith(",")){
                return string.substring(0, string.length()-1);     
            }else{
                return string;
            }
            
        }     
   
        /**   
        * 分割字符串   
        * @param str 要分割的字符串   
        * @param spilit_sign 字符串的分割标志   
        * @return 分割后得到的字符串数组   
        */    
        public static String[] stringSpilit(String str,String spilit_sign){     
           String[] spilit_string=str.split(spilit_sign);     
           if(spilit_string[0].equals(""))     
           {     
             String[] new_string=new String[spilit_string.length-1];     
             for(int i=1;i<spilit_string.length;i++) {
                 new_string[i-1]=spilit_string[i];
             }     
               return new_string;     
           }     
           else {
               return spilit_string;
           }     
         }     
    
        /**   
         * 字符串字符集转换   
         * @param str 要转换的字符串   
         * @return 转换过的字符串   
         */    
        public static String stringTransCharset(String str){     
            String new_str=null;     
            try{     
                 new_str=new String(str.getBytes("iso-8859-1"),"GBK");     
             }catch(Exception e){     
              e.printStackTrace();     
             }     
            return new_str;     
        }  
        /**
         * sql防注入方法
         * @param sql
         * @return 
         */
        public static String TransactSQLInjection(String sql) {  
            return sql.replaceAll(".*([';]+|(--)+).*", " ");  
        }  
        /**
         * 编码转换方法
         * @param str
         * @return iso8859-1编码字符串
         * @throws UnsupportedEncodingException 
         */
        public static String stringToIso8859(String str) throws UnsupportedEncodingException{
            return new String (str.getBytes("gbk"),"iso8859-1");
        }
        /**
         * 将html文档转换为纯文本字符串方法
         * @param inputString 富文本字符串，含html标签的字符串
         * @return 返回文本字符串
         */
        public static String Html2Text(String inputString){
             String htmlStr = inputString; //含html标签的字符串
             String textStr ="";
             java.util.regex.Pattern p_script;
             java.util.regex.Matcher m_script;
             java.util.regex.Pattern p_style;
             java.util.regex.Matcher m_style;
             java.util.regex.Pattern p_html;
             java.util.regex.Matcher m_html;
            try{
                  String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
                  String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
                  String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

                  p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
                  m_script = p_script.matcher(htmlStr);
                  htmlStr = m_script.replaceAll(""); //过滤script标签

                  p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
                  m_style = p_style.matcher(htmlStr);
                  htmlStr = m_style.replaceAll(""); //过滤style标签

                  p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
                  m_html = p_html.matcher(htmlStr);
                  htmlStr = m_html.replaceAll(""); //过滤html标签
                 htmlStr= stringFilter(htmlStr);
                 htmlStr= stringFilterScriptChar(htmlStr);
                  textStr = htmlStr;
             }catch(Exception e){
             }
             return textStr;//返回文本字符串
         }  
        /**
         * 获取下划线后第一个字符串
         * @param name
         * @return 
         */
        public static String getThe_BackFirstCharacter(String name){
            return name.split("_")[1].substring(0, 1);
        }
}
