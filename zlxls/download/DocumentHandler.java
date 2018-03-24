package com.zlxls.download;

import com.jfinal.kit.PathKit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * word模板生成类
 * @ClassNmae：DocumentHandler   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class DocumentHandler {
    private Configuration configuration = null;  
    public DocumentHandler() {  

        configuration = new Configuration();  

        configuration.setDefaultEncoding("utf-8");  

    }  
    /**
     * word生成类
     * @param dataMap
     * @param filePath
     * @param fileName
     * @param type
     * @throws java.io.UnsupportedEncodingException
    */
    public void createDoc(Map<String,Object> dataMap,String filePath,String fileName,int type) throws UnsupportedEncodingException {  
        Template t=null;  
        File dir = new File(filePath);  
        if (!dir.exists()) dir.mkdir();
        try { 

//            configuration.setDirectoryForTemplateLoading(new File("D:"));  
            
            configuration.setClassForTemplateLoading(this.getClass(),"");  //FTL文件所存在的位置  
            
            configuration.setDefaultEncoding("utf-8");  

            configuration.setObjectWrapper(new DefaultObjectWrapper());       

            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);       

            t = configuration.getTemplate("table_"+type+".ftl");  

        } catch (IOException e) {  

            e.printStackTrace();  

        }  

        File outFile = new File(filePath+fileName);  

        Writer out = null;  

        FileOutputStream fos=null;  

        try {  

            fos = new FileOutputStream(outFile);  

            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  

            out = new BufferedWriter(oWriter);   

        } catch (FileNotFoundException e1) {  

            e1.printStackTrace();  

        }  

        try {  

            t.process(dataMap, out);  

            out.close();  

            fos.close();  

        } catch (TemplateException e) {  

            e.printStackTrace();  

        } catch (IOException e) {  

            e.printStackTrace();  

        }  

    } 
    /**
     * 模板
     * 根据实际需要定义在DownloadWord类中
     * 一般不在此类中定义
     * @param fileName
     * @throws java.io.UnsupportedEncodingException
    */
    public static void downloadWord(String fileName) throws UnsupportedEncodingException{
    	DocumentHandler mdoc = new DocumentHandler();  

	Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("nian", "2012");  
        dataMap.put("yue", "2");  
        dataMap.put("ri", "13");  
        dataMap.put("bianzhi", "唐鑫");  
        dataMap.put("dianhua", "13020265912");  
        dataMap.put("shenheren", "占文涛");  
          
        List<Map<String,Object>> list = new ArrayList<>();  
        for (int i = 0; i < 10; i++) {  
            Map<String,Object> map = new HashMap<>();  
            map.put("xuhao", i);  
            map.put("neirong", "内容"+i);  
            list.add(map);  
        }  
        dataMap.put("list", list);  
        mdoc.createDoc(dataMap, PathKit.getWebRootPath()+File.separator,fileName,1);
    }
}
