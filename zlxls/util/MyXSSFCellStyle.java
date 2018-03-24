package com.zlxls.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * Excel 样式，格式设置类
 * @ClassNmae：MyXSSFCellStyle   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class MyXSSFCellStyle {
    /**
     * 获取设置的风格
     * @param workbook
     * @return 
     */
    public static XSSFCellStyle getStyle(XSSFWorkbook workbook) {
//     设置字体;
        XSSFFont font = workbook.createFont();
        //设置字体大小;
        font.setFontHeightInPoints((short) 9);
        //设置字体名字;
        font.setFontName("Courier New");
        //font.setItalic(true);
        //font.setStrikeout(true);
//     设置样式;
        org.apache.poi.xssf.usermodel.XSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
}
