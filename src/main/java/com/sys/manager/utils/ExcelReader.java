package com.sys.manager.utils;

import com.sys.manager.entity.AdminInfo;
import com.sys.manager.entity.PassengerInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelReader {

    /**
     * Excel读取 操作 xls文件
     */
    public static List<List<String>> readExcelLow2007(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 得到第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();

        // 得到Excel的列数
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<List<String>> dataLst = new ArrayList<>();
        // 循环Excel的行
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<>();
            // 循环Excel的列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类
                    switch (cell.getCellType()) {
                        case NUMERIC: // 数字
                            cell.setCellType(CellType.STRING);
                            cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                            break;
                        case STRING: // 字符串
                            cellValue = String.valueOf(cell.getStringCellValue());
                            break;
                        case BOOLEAN: // Boolean
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA: // 公式
                            cellValue = String.valueOf(cell.getNumericCellValue());
                            break;
                        case BLANK: // 空值
                            cellValue = null;
                            break;
                        case ERROR: // 故障
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }
            // 保存第r行的第c列
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    /**
     * Excel读取 操作 2007+版本 xlsx文件
     */
    public static List<List<String>> readExcel2007(InputStream is) throws IOException {
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 得到第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();

        // 得到Excel的列数
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<List<String>> dataLst = new ArrayList<>();
        // 循环Excel的行
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<>();
            // 循环Excel的列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类
                    switch (cell.getCellType()) {
                        case NUMERIC: // 数字
//                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            cell.setCellType(CellType.STRING);
                            cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                            break;
                        case STRING: // 字符串
                            cellValue = String.valueOf(cell.getStringCellValue());
                            break;
                        case BOOLEAN: // Boolean
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA: // 公式
                            cellValue = String.valueOf(cell.getCellFormula());
                            break;
                        case BLANK: // 空值
                            cellValue = null;
                            break;
                        case ERROR: // 故障
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }
            // 保存第r行的第c列
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    /**
     * 根据excel生成 实体类
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static List<AdminInfo> getAdminList(InputStream is, String fileSuffix) throws IOException {
        List<List<String>> list = new ArrayList<>();
        if ("xls".equals(fileSuffix)) {
            list = ExcelReader.readExcelLow2007(is);
        } else if ("xlsx".equals(fileSuffix)) {
            list = ExcelReader.readExcel2007(is);
        }

        //遍历数据到实体集合开始
        List<AdminInfo> listBean = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) { // i=1是因为第一行不要
            AdminInfo uBean = new AdminInfo();
            List<String> listStr = list.get(i);
            for (int j = 0; j < listStr.size(); j++) {
                String str = listStr.get(j);
                switch (j) {
                    case 0:
                        //姓名
                        uBean.setName(str);
                        break;
                    case 1:
                        //身份证号
                        uBean.setCardId(str);
                        break;
                    case 2:
                        //性别
                        if (StringUtils.isNotEmpty(str)) {
                            switch (str) {
                                case "男":
                                    uBean.setGender("1");
                                    break;
                                case "女":
                                    uBean.setGender("2");
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    case 3:
                        //手机号码
                        uBean.setPhone(str);
                        break;
                    case 4:
                        //角色
                        if (StringUtils.isNotEmpty(str)) {
                            switch (str) {
                                case "超级管理员":
                                    uBean.setRoleId(1);
                                    break;
                                case "系统管理员":
                                    uBean.setRoleId(2);
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    default:
                }
            }
            listBean.add(uBean);
        } //遍历数据到实体集合结束
        return listBean;
    }

    /**
     * 获取数据所在行
     *
     * @param is
     * @param substring
     * @param columns   要寻找的列，从0开始
     * @param time      要寻找的字符串出现的册数
     * @param str       要寻找的字符串
     * @return
     */
    public static int getFirstRow(InputStream is, String substring, int columns, int time, String str) throws IOException {
        List<List<String>> list = new ArrayList<>();
        if ("xls".equals(substring)) {
            list = ExcelReader.readExcelLow2007(is);
        } else if ("xlsx".equals(substring)) {
            list = ExcelReader.readExcel2007(is);
        }
        //遍历数据找到想要的初始行号
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> listStr = list.get(i);
            String value = listStr.get(columns);
            if (str.equals(value)) {
                index++;
            }
            if (index == time) {
                return i + 1;
            }
        }
        return -1;
    }

    public static List<PassengerInfo> getPassengerList(InputStream is, String fileSuffix) throws IOException {
        List<List<String>> list = new ArrayList<>();
        if ("xls".equals(fileSuffix)) {
            list = ExcelReader.readExcelLow2007(is);
        } else if ("xlsx".equals(fileSuffix)) {
            list = ExcelReader.readExcel2007(is);
        }

        //遍历数据到实体集合开始
        List<PassengerInfo> listBean = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) { // i=1是因为第一行不要
            PassengerInfo uBean = new PassengerInfo();
            List<String> listStr = list.get(i);
            for (int j = 0; j < listStr.size(); j++) {
                String str = listStr.get(j);
                switch (j) {
                    case 0:
                        // 乘机日期
                        uBean.setFlightDateStr(str);
                        break;
                    case 1:
                        // 航班号
                        uBean.setFlightNum(str);
                        break;
                    case 2:
                        // 姓名
                        uBean.setName(str);
                        break;
                    case 3:
                        // 身份证号
                        uBean.setCardId(str);
                        break;
                    case 4:
                        // 舱位
                        uBean.setShippingSpace(str);
                        break;
                    case 5:
                        // 座位号
                        uBean.setSeat(str);
                        break;
                    default:
                }
            }
            listBean.add(uBean);
        } //遍历数据到实体集合结束
        return listBean;
    }
}