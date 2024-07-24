package com.sys.manager.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**  
 * Excel Row工具类  
 * @author dsy  
 * @version 1.0  
 */  
public class ExcelSheetRow {   
       
       
    public ExcelSheetRow() {   
        // TODO Auto-generated constructor stub   
    }   
  
    /**
     * 设置当前Sheet名字  
     */  
    private  String sheetName = null;
    private HSSFRow row = null;
       
       
    /**  
     * 创建当前标题行  
     * @return
     */  
    public HSSFRow createCurrSheetTitle(ExcelWorkBook work) {
       HSSFSheet sheet = work.getSheet();
       row = sheet.createRow(0);   
       return row;   
    }
    
    //sheetN
    public HSSFRow createCurrSheetTitleN(ExcelWorkBook work, String sheetName) {
        HSSFSheet sheet = work.getSheetN(sheetName);
        row = sheet.createRow(0);
        return row;
     }

    /**  
     * 创建当前标题行  
     * @return
     */  
    public HSSFRow createTwoSheetTitle(ExcelWorkBook work) {
       HSSFSheet sheet = work.getSheet();
       row = sheet.createRow(1);   
       return row;   
    }
   
       
    /**  
     * 创建当前excel记录内容  
     * @param i
     * @return  
     */  
    public HSSFRow createCurrSheetRecord(ExcelWorkBook work, int i) {
        HSSFSheet sheet = work.getSheet();
        row = sheet.createRow(i+1);   
        return row;   
    }
    
    public HSSFRow createComSheetRecord(ExcelWorkBook work, int i) {
        HSSFSheet sheet = work.getSheet();
        row = sheet.createRow(i+2);   
        return row;   
    }    
  
    public  String getSheetName() {
        return sheetName;   
    }   
  
    public  void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }   
  
}  

