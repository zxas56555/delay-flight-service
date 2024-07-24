package com.sys.manager.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;

/**
 * Excel Cell工具类
 * @author dsy
 * @version 1.0
 */
public class ExcelSheetCell {

    private HSSFRow row = null;
    private HSSFCell cell = null;



    /**
     * 用于产生当前excel标题
     * @param firstRowValue [标题数组]
     * @param style [当前单元格风格]
     */
    public  void createCurrRowTitle(ExcelSheetRow sheetRow, ExcelWorkBook work , String[] firstRowValue, HSSFCellStyle style) {
        row = sheetRow.createCurrSheetTitle(work);
        for (int i = 0; i < firstRowValue.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellStyle(style);
//            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(firstRowValue[i]);
        }
    }

    public  void createComRowTitle(ExcelSheetRow sheetRow, ExcelWorkBook work , String[] firstRowValue, HSSFCellStyle style) {
        row = sheetRow.createCurrSheetTitle(work);
        HSSFCell cell=row.createCell((short)7);
        style.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(style);
        cell.setCellValue(firstRowValue[0]);
        HSSFSheet sheet=work.getSheet();
        sheet.addMergedRegion(new CellRangeAddress(0,(short)7,0,(short)10));

        HSSFCell cell1=row.createCell((short)11);
        cell1.setCellStyle(style);
        cell1.setCellValue(firstRowValue[1]);
        sheet.addMergedRegion(new CellRangeAddress(0,(short)11,0,(short)14));

        HSSFCell cell2=row.createCell((short)15);
        cell2.setCellStyle(style);
        cell2.setCellValue(firstRowValue[2]);
        sheet.addMergedRegion(new CellRangeAddress(0,(short)15,0,(short)18));

        row=sheetRow.createTwoSheetTitle(work);
        HSSFCell cell3=row.createCell((short)0);
        cell3.setCellStyle(style);
        cell3.setCellValue(firstRowValue[3]);

        HSSFCell cell4=row.createCell((short)1);
        cell4.setCellStyle(style);
        cell4.setCellValue(firstRowValue[4]);

        HSSFCell cell5 =row.createCell((short)2);
        cell5.setCellStyle(style);
        cell5.setCellValue(firstRowValue[5]);

        HSSFCell cell6 =row.createCell((short)3);
        cell6.setCellStyle(style);
        cell6.setCellValue(firstRowValue[6]);

        HSSFCell cell7 =row.createCell((short)4);
        cell7.setCellStyle(style);
        cell7.setCellValue(firstRowValue[7]);

        HSSFCell cell8 =row.createCell((short)5);
        cell7.setCellStyle(style);
        cell7.setCellValue(firstRowValue[8]);

        HSSFCell cell9 =row.createCell((short)6);
        cell7.setCellStyle(style);
        cell7.setCellValue(firstRowValue[9]);

        for(int i=10;i<firstRowValue.length;i++){
            cell = row.createCell((short) i-3);
            cell.setCellStyle(style);
            cell.setCellValue(firstRowValue[i]);
        }


    }
    /**
     * 用于生成excel当前记录内容,标题除外
     * @param beanList [当前数据列表,i=Object[]]
     * @param style [当前单元格风格]
     */
    public  void createCurrRowRecord(ExcelSheetRow sheetRow, ExcelWorkBook work, List beanList, HSSFCellStyle style) {
        Object[] obj = null;
        if(beanList==null) {
            return;
        }
        for (int i = 0; i < beanList.size(); i++) {
            row = sheetRow.createCurrSheetRecord(work,i);
            obj = (Object[]) beanList.get(i);
            if (obj != null) {
                createExcelCell(row, obj,style);
            }
        }
    }
    public  void createCurrRowTitleN(ExcelSheetRow sheetRow, ExcelWorkBook work , String[] firstRowValue, HSSFCellStyle style, String sheetName) {
        row = sheetRow.createCurrSheetTitleN(work,sheetName);
        for (int i = 0; i < firstRowValue.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellStyle(style);
//            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(firstRowValue[i]);
        }
    }
    /**
     * 用于生成excel当前记录内容,标题除外
     * @param beanList [当前数据列表,i=Object[]]
     * @param style [当前单元格风格]
     */
    public  void createCurrRowRecord(ExcelSheetRow sheetRow, ExcelWorkBook work, List beanList, HSSFCellStyle style, int sheetNum) {
        Object[] obj = null;
        int ListSize = 0;
        if(beanList.size() < 65535*(sheetNum+1)){
            ListSize = beanList.size();
        }else{
            ListSize = 65535*(sheetNum+1);
        }
        for (int i = 65535*sheetNum; i < ListSize; i++) {
            row = sheetRow.createCurrSheetRecord(work,i-(65535*sheetNum));
            obj = (Object[]) beanList.get(i);
            if (obj != null) {
                createExcelCell(row, obj,style);
            }
        }
    }


    public  void createCurrRowRecordByListMap(ExcelSheetRow sheetRow, ExcelWorkBook work, List<Map<String,Object>> beanList, String[] keys, HSSFCellStyle style) {

        for (int i = 0; i < beanList.size(); i++) {
            row = sheetRow.createCurrSheetRecord(work,i);
            Map map = beanList.get(i);
            if (map != null) {
                Object[] obj = new Object[keys.length];
                for(int j=0;j<keys.length;j++){
                    obj[j] = map.get(keys[j]);
                }
                createExcelCell(row, obj,style);
            }
        }
    }

    public  void createComRowRecord(ExcelSheetRow sheetRow, ExcelWorkBook work, List beanList, HSSFCellStyle style) {
        Object[] obj = null;
        for (int i = 0; i < beanList.size(); i++) {
            row = sheetRow.createComSheetRecord(work,i);
            obj = (Object[]) beanList.get(i);
            if (obj != null) {
                createExcelCell(row, obj,style);
            }
        }
    }

    /**
     * 需要以数组的方式提供当前每条记录  
     * 通过数组自动判断有多少列,生成当前行  
     */
    private  void createExcelCell(HSSFRow row, Object[] obj, HSSFCellStyle style) {
        try {
            for (int i = 0; i < obj.length; i++) {
                try {
                    if (obj[i].toString() != null) {

                        cell = row.createCell(i);
                        cell.setCellStyle(style);
//                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);   
                        cell.setCellValue(obj[i].toString());
                    }
                } catch (NullPointerException e) {
                    continue;
                }

            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void createCurrRowRecordByListMapWithSheet(ExcelSheetRow sheetRow, ExcelWorkBook work, List<Map<String, Object>> beanList, String[] keys, HSSFCellStyle style, int sheetNum) {
        int ListSize = 0;
        if(beanList.size() < 65535*(sheetNum+1)){
            ListSize = beanList.size();
        }else{
            ListSize = 65535*(sheetNum+1);
        }

        for (int i = 65535*sheetNum; i < ListSize; i++) {
            row = sheetRow.createCurrSheetRecord(work,i-(65535*sheetNum));
            Map map = beanList.get(i);
            if (map != null) {
                Object[] obj = new Object[keys.length];
                for(int j=0;j<keys.length;j++){
                    obj[j] = map.get(keys[j]);
                }
                createExcelCell(row, obj,style);
            }
        }
    }
}

