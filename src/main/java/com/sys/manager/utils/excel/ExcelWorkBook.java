package com.sys.manager.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Excel WorkBook工具类  
 * @author dsy
 * @version 1.0
 */
public class ExcelWorkBook {

    private HSSFWorkbook workbook = null;
    //设置当前workbookName   
    private String workbookName = null;
    private HSSFSheet sheet = null;
    private FileOutputStream fileOut;

    public ExcelWorkBook() {
        if(workbook == null) {
            workbook = new HSSFWorkbook();
        }
    }

    public ExcelWorkBook(String workbookName) {
        setWorkbookName(workbookName);
        if(workbook == null) {
            workbook = new HSSFWorkbook();
        }
    }
    //创建sheetn
    public HSSFSheet getSheetN(String sheetName) {
        sheet = workbook.createSheet(sheetName);
        return sheet;
    }
    public String getWorkbookName() {
        return workbookName;
    }

    public void setWorkbookName(String workbookName) {
        this.workbookName = workbookName;
    }

    public HSSFSheet getSheet() {
        if (sheet == null) {
            sheet = workbook.createSheet();
        }else{
            int sheetNum = workbook.getNumberOfSheets();
            sheet = workbook.getSheetAt(sheetNum-1);
        }
        return sheet;
    }

    public HSSFSheet createSheet() {
        sheet = workbook.createSheet();
        return sheet;
    }

    /**
     * 输入当前WorkBook为下载临时文件记录  
     * @param excelName
     */
    public void writerFileStream(String excelName) {
        try {
            fileOut = new FileOutputStream(excelName);
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
                if(workbook != null) {
                    workbook = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block   
                e.printStackTrace();
            }
        }
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }


}  

