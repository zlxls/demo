package com.zlxls.util;

import com.jfinal.upload.UploadFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 文件操作工具类
 * @ClassNmae：FIleUtil   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class FIleUtil {
    /**
     * 文件上传
     * @param upfile 文件
     * @param path 路径
     * @param deletePath 删除路径
     * @return 
     */
    public static String uploadFile(UploadFile upfile,String path,String deletePath) {
        
        String flag = null;
        
        File file = upfile.getFile();
        
        String filename = file.getName();
        
        if(Validate.isNotNull(filename)){
            
            filename = filename.split(".do")[0];//android上传产生.do文件，需要先将他处理
            
            filename = new Date().getTime()+filename.substring(filename.lastIndexOf("."));
            //新保存的位置
            //没有则新建目录
            File floder = new File(path);
            if (!floder.exists()) {
                floder.mkdirs();
            }
            //保存新文件
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try{
                File savePath = new File(path + filename);

                if(!savePath.isDirectory()) savePath.createNewFile();

                fis = new FileInputStream(file);
                fos = new FileOutputStream(savePath);

                byte[] bt = new byte[300];
                while (fis.read(bt, 0, 300) != -1) {
                    fos.write(bt, 0, 300);
                }
                flag = filename;
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    if(null!=fis) fis.close();
                    if(null!=fos) fos.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            File delFile = new File(deletePath);
            if(delFile.exists()){
                delFile.delete();
            }
        }
        return flag;
    }
    /**
     * @Description：删除文件夹
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description：删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
    /**
     * Excel导出图片
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void addPictureToExcel() throws FileNotFoundException, IOException{
             //create a new workbook
        Workbook workbook = new XSSFWorkbook(); //or new HSSFWorkbook();
        //add picture data to this workbook.
        // 打开图片
        InputStream is = new FileInputStream("D:/aaa.jpg");
        byte[] bytes = IOUtils.toByteArray(is);
        // 增加图片到 Workbook
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        is.close();
        CreationHelper helper = workbook.getCreationHelper();
        //create sheet
        Sheet sheet = workbook.createSheet();
        // Create the drawing patriarch.  This is the top level container for all shapes.
        Drawing drawing = sheet.createDrawingPatriarch();
        //add a picture shape
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner of the picture,
        //subsequent call of Picture#resize() will operate relative to it
        // 设置图片位置
        anchor.setCol1(3);
        anchor.setRow1(2);
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        //auto-size picture relative to its top-left corner
        pict.resize();
        //save workbook
        String file = "picture.xls";
        if(workbook instanceof XSSFWorkbook) file += "x";
        // 输出文件
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
    }      
}  
