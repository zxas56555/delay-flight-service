package com.sys.manager.utils.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 创建Excel工具类
 * @author Administrator
 *
 */
public class ExcelUtilFactory {


    private static ExcelUtilFactory instance = null;
    private static HttpServletRequest excelRequest = null;
    private static HttpServletResponse excelResponse = null;

    public static ExcelUtilFactory getInstance(HttpServletRequest request,
                                               HttpServletResponse response) {
        if(instance == null) {
            instance = new ExcelUtilFactory();
        }
        excelRequest = request;
        excelResponse = response;
        return instance;
    }

    public  void outputExcel(String excelName, List list, String[] firstRowValue) {
        //如果list的大小 大于65535  则增加sheet
        double m = Math.ceil(list.size()/65535);//xls格式  最大65536行，第一行为表头


        ExcelWorkBook work = new ExcelWorkBook();
        work.setWorkbookName(excelName);
        ExcelSheetRow sheetRow = new ExcelSheetRow();
        ExcelSheetCell sheetCell = new ExcelSheetCell();
        ExcelCellStyleUtils util = new ExcelCellStyleUtils(work);
        for(int i = 0;i<m+1;i++){
            work.createSheet();
            sheetCell.createCurrRowTitle(sheetRow, work, firstRowValue, util.titleStyle);
            sheetCell.createCurrRowRecord(sheetRow, work, list, util.nameStyle,i);
        }
        String realPath = getExcelRealPath(excelName);
        work.writerFileStream(realPath);
        downloadFile(realPath);
    }
    public  void outputExcel(String excelName,String[] titles,String[] keys, List<Map<String,Object>> list) {
        //如果list的大小 大于65535  则增加sheet
        double m = Math.ceil(list.size()/3000);//xls格式  最大65536行，第一行为表头

        ExcelWorkBook work = new ExcelWorkBook();
        work.setWorkbookName(excelName);
        ExcelSheetRow sheetRow = new ExcelSheetRow();
        ExcelSheetCell sheetCell = new ExcelSheetCell();
        ExcelCellStyleUtils util = new ExcelCellStyleUtils(work);
        for(int i = 0;i<m+1;i++){
            work.createSheet();
            sheetCell.createCurrRowTitle(sheetRow, work, titles, util.titleStyle);
            sheetCell.createCurrRowRecordByListMap(sheetRow, work, list, keys, util.nameStyle);
        }
        String realPath = getExcelRealPath(excelName);
//      String realPath = "e:/temp/testRealPath_2.xls";
        work.writerFileStream(realPath);
        downloadFile(realPath);
    }

    public  void outputExcelWithSheet(String excelName,String[] titles,String[] keys, List<Map<String,Object>> list) {
        //如果list的大小 大于65535  则增加sheet
        double m = Math.ceil(list.size()/65535);//xls格式  最大65536行，第一行为表头

        ExcelWorkBook work = new ExcelWorkBook();
        work.setWorkbookName(excelName);
        ExcelSheetRow sheetRow = new ExcelSheetRow();
        ExcelSheetCell sheetCell = new ExcelSheetCell();
        ExcelCellStyleUtils util = new ExcelCellStyleUtils(work);
        for(int i = 0;i<m+1;i++){
            work.createSheet();
            sheetCell.createCurrRowTitle(sheetRow, work, titles, util.titleStyle);
            sheetCell.createCurrRowRecordByListMapWithSheet(sheetRow, work, list, keys, util.nameStyle ,i);
        }
        String realPath = getExcelRealPath(excelName);
//      String realPath = "e:/temp/testRealPath_2.xls";
        work.writerFileStream(realPath);
        downloadFile(realPath);
    }

    public  void outputComExcel(String excelName, List list, String[] firstRowValue) {
        ExcelWorkBook work = new ExcelWorkBook();
        work.setWorkbookName(excelName);
        ExcelSheetRow sheetRow = new ExcelSheetRow();
        ExcelSheetCell sheetCell = new ExcelSheetCell();
        ExcelCellStyleUtils util = new ExcelCellStyleUtils(work);
        sheetCell.createComRowTitle(sheetRow, work, firstRowValue, util.titleStyle);
        sheetCell.createComRowRecord(sheetRow, work, list, util.nameStyle);
        String realPath = getExcelRealPath(excelName);
        work.writerFileStream(realPath);
        downloadFile(realPath);
    }


    /**
     * 生成excel并保存到服务器
     * firstRowValue：excel表头
     * list:excel内容
     * @author
     *  return:返回保存路径
     */
    public  String saveExcel(List list, String[] firstRowValue,String path,String fileName){

        ExcelWorkBook work = new ExcelWorkBook();
        work.setWorkbookName(fileName);
        ExcelSheetRow sheetRow = new ExcelSheetRow();
        ExcelSheetCell sheetCell = new ExcelSheetCell();
        ExcelCellStyleUtils util = new ExcelCellStyleUtils(work);
        sheetCell.createCurrRowTitle(sheetRow, work, firstRowValue, util.titleStyle);
        sheetCell.createCurrRowRecord(sheetRow, work, list, util.nameStyle);

        String realPath = excelRequest.getSession().getServletContext().getRealPath(path);
        File excelFile = new File(realPath);
        if(!excelFile.exists()) {
            excelFile.mkdirs();
        }
        path = path+"/"+fileName+".xls";
        work.writerFileStream(realPath+"/"+fileName+".xls");
        return path;
    }

    private  String getExcelRealPath(String excelName) {
        String realPath = excelRequest.getSession().getServletContext().getRealPath("/UploadFile");
        System.out.println("realPath=====" + realPath);
        File excelFile = new File(realPath);
        if(!excelFile.exists()) {
            excelFile.mkdirs();
        }
        excelName = realPath+ "/" + excelName+".xls";
        return  excelName;
    }

    private  void downloadFile(String strfileName) {
        try {
            // 获得ServletContext对象
            if(excelFileNotFund(strfileName)) {
                throw new IllegalArgumentException("File=["+strfileName+"] not fund file path");
            }
            // 取得文件的绝对路径
            File excelFile = getExcelDownloadPath(strfileName);
            putResponseStream(strfileName, excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  File getExcelDownloadPath(String excelName) {
//      String realPath = excelRequest.getRealPath("/UploadFile");
//      excelName = realPath+ "\\" + excelName;
//      excelName = replaceRNAll(excelName);
        File excelFile = new File(excelName);
        return  excelFile;
    }

    //用传入参数的判断
    private  boolean excelFileNotFund(String strfileName) {
        return strfileName ==  null|| strfileName.equals("");
    }

    /**
     *
     * @param strfileName : 文件名称
     * @param excelName  : 文件的相对路径或绝对路径
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private  void putResponseStream(String strfileName, File excelName)
            throws UnsupportedEncodingException{
        String filename = URLEncoder.encode(excelName.getName(), "UTF-8");
        excelResponse.setHeader("Content-disposition","attachment; filename=" + filename);
        excelResponse.setContentLength((int) excelName.length());
        excelResponse.setContentType("application/x-download");
        byte[] buffer = new byte[1024];
        int i = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(excelName);
            while ((i = fis.read(buffer)) > 0) {
                excelResponse.getOutputStream().write(buffer, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert fis != null;
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /** wb = dropDownList2003(wb, sheet, departSelectList, 2, 2, "hidden_depart", 1);
     * @param wb HSSFWorkbook对象
     * @param realSheet 需要操作的sheet对象
     * @param datas 下拉的列表数据
     * @param startCol 开始列
     * @param endCol 结束列
     * @param hiddenSheetName 隐藏的sheet名
     * @param hiddenSheetIndex 隐藏的sheet索引
     * @return
     * @throws Exception
     */
    public HSSFWorkbook dropDownList2003
    (Workbook wb, Sheet realSheet, String[] datas, int startCol, int endCol, String hiddenSheetName, int hiddenSheetIndex)throws Exception {

        HSSFWorkbook workbook = (HSSFWorkbook) wb;
        // 创建一个数据源sheet
        HSSFSheet hidden = workbook.createSheet(hiddenSheetName);
        // 数据源sheet页不显示
        workbook.setSheetHidden(hiddenSheetIndex, true);
        // 将下拉列表的数据放在数据源sheet上
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = 0, length = datas.length; i < length; i++) {
            row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(datas[i]);
        }
        //2016-12-15更新，遇到问题：生成的excel下拉框还是可以手动编辑，不满足
        //HSSFName namedCell = workbook.createName();
        //namedCell.setNameName(hiddenSheetName);
        // A1 到 Adatas.length 表示第一列的第一行到datas.length行，需要与前一步生成的隐藏的数据源sheet数据位置对应
        //namedCell.setRefersToFormula(hiddenSheetName + "!$A$1:$A" + datas.length);
        // 指定下拉数据时，给定目标数据范围 hiddenSheetName!$A$1:$A5   隐藏sheet的A1到A5格的数据
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(hiddenSheetName + "!$A$1:$A" + datas.length);
        CellRangeAddressList addressList = null;
        HSSFDataValidation validation = null;
        row = null;
        cell = null;
        // 单元格样式
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        row = (HSSFRow) realSheet.createRow(1);
        // 循环指定单元格下拉数据,可以单独设置下拉样式
        /*for (int i = startRow; i <= endRow; i++) {
            cell = row.createCell(startCol);
            cell.setCellStyle(style);
        }*/
        // 创建下拉四个参数分别是：起始行、终止行、起始列、终止列
        addressList = new CellRangeAddressList(1, 65535, startCol, endCol);
        validation = new HSSFDataValidation(addressList, constraint);
        realSheet.addValidationData(validation);
        return workbook;
    }

    /**
     * 导出多个sheet
     * @param excelName 	文件名
     * @param sheetNames sheeet名称
     * @param dataLists sheet表格数据
     * @param firstRowValueList sheet表头
     */
    @SuppressWarnings({ "static-access", "unused", "rawtypes" })
    public  void outputExcelSheetN(String excelName,String[] sheetNames, List<List> dataLists, List<String[]> firstRowValueList) {
        ExcelWorkBook work = new ExcelWorkBook();
        work.setWorkbookName(excelName);

        ExcelSheetCell sheetCellN = new ExcelSheetCell();
        ExcelCellStyleUtils util = new ExcelCellStyleUtils(work);
        ExcelSheetRow sheetRowN = new ExcelSheetRow();

        for(int i=0;i<sheetNames.length;i++) {
            String sheetName = sheetNames[i];
            List dataList = dataLists.get(i);
            String[] firstRowValue = firstRowValueList.get(i);
            sheetRowN.setSheetName(sheetName);
            sheetCellN.createCurrRowTitleN(sheetRowN, work, firstRowValue, util.titleStyle,sheetName);
            sheetCellN.createCurrRowRecord(sheetRowN, work, dataList, util.nameStyle);
        }

        String realPath = getExcelRealPath(excelName);
//       String realPath = "e:/temp/testRealPath_2.xls";
        work.writerFileStream(realPath);
        downloadFile(realPath);
    }
}  

