package com.sys.manager.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**  
 * Excel Style风格工具类  
 * @author dsy  
 * @version 1.0  
 */  
public class ExcelCellStyleUtils {   
  
    //标题样式   
    public HSSFCellStyle titleStyle;
    //时间样式   
    public HSSFCellStyle dataStyle;
    //单元格样式   
    public HSSFCellStyle nameStyle;
    //超链接样式   
    public HSSFCellStyle linkStyle;
    public HSSFFont font;
       
    public ExcelCellStyleUtils(ExcelWorkBook work) {   
        titleStyle = titleStyle(work.getWorkbook());   
        dataStyle = dataStyle(work.getWorkbook());   
        nameStyle = nameStyle(work.getWorkbook());   
        linkStyle = linkStyle(work.getWorkbook());   
    }   
    /**  
     * 超链接样式  
     * @return HSSFCellStyle  
     */  
    private HSSFCellStyle linkStyle(HSSFWorkbook work) {
        HSSFCellStyle linkStyle = work.createCellStyle();
          linkStyle.setBorderBottom(BorderStyle.THIN);
          linkStyle.setBorderLeft(BorderStyle.THIN);
          linkStyle.setBorderRight(BorderStyle.THIN);
          linkStyle.setBorderTop(BorderStyle.THIN);
          linkStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
          linkStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
          HSSFFont font = work.createFont();
          font.setFontName(HSSFFont.FONT_ARIAL);
          font.setUnderline((byte)1);   
          font.setColor(IndexedColors.BLUE.index);
          linkStyle.setFont(font);   
          return linkStyle;   
    }   
       
    /**s  
     * 单元格样式  
     * @return HSSFCellStyle  
     */  
    private HSSFCellStyle nameStyle(HSSFWorkbook work) {
        HSSFCellStyle nameStyle = work.createCellStyle();
          nameStyle.setBorderBottom(BorderStyle.THIN);
          nameStyle.setBorderLeft(BorderStyle.THIN);
          nameStyle.setBorderRight(BorderStyle.THIN);
          nameStyle.setBorderTop(BorderStyle.THIN);
          nameStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
          nameStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
          return nameStyle;   
    }   
       
    /**  
     * 时间样式  
     * @return HSSFCellStyle  
     */  
    private HSSFCellStyle dataStyle(HSSFWorkbook work) {
        HSSFCellStyle dataStyle = work.createCellStyle();
          dataStyle.setBorderBottom(BorderStyle.THIN);
          dataStyle.setBorderLeft(BorderStyle.THIN);
          dataStyle.setBorderRight(BorderStyle.THIN);
          dataStyle.setBorderTop(BorderStyle.THIN);
          dataStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
          dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
          dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
          return dataStyle;   
    }   
       
    /**  
     * 标题样式  
     * @return HSSFCellStyle  
     */  
    private HSSFCellStyle titleStyle(HSSFWorkbook work) {
        HSSFCellStyle titleStyle = work.createCellStyle();
        font = work.createFont();   
        font.setItalic(true);   
        font.setBold(true);
        font.setColor(IndexedColors.BLUE.index);
          titleStyle.setBorderBottom(BorderStyle.DOUBLE);
          titleStyle.setBorderLeft(BorderStyle.THIN);
          titleStyle.setBorderRight(BorderStyle.THIN);
          titleStyle.setBorderTop(BorderStyle.DOUBLE);
          titleStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
          titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
          return titleStyle;   
    }   
}  

