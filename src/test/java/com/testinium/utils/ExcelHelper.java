package com.testinium.utils;

import com.testinium.page.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class ExcelHelper {
    protected static final Logger logger = LogManager.getLogger(LoginPage.class);
    private static final Map<Integer, String> excelData = new HashMap<>();
    private static ExcelHelper instance;

    private ExcelHelper() {
    }

    public static ExcelHelper getInstance() {
        if (instance == null) {
            instance = new ExcelHelper();
        }
        return instance;
    }

    public String getCellValue(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            logger.info("Veri okunama N/A yazılıyor.");
            return "N/A";
        }
    }

    public Map<String, String> readExcelToHashMap(String filePath) {
        Map<String, String> dataMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    excelData.put(i, getCellValue(row.getCell(i)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataMap;
    }

    public String getValueFromCell(String fileName, int rowIndex, int columnIndex) {
        String cellValue = "N/A";
        try (FileInputStream fis = new FileInputStream(getPathFromResources(fileName));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    cellValue = cell.getStringCellValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.equals(cellValue, "N/A"))
            logger.error("Verilen sütün ve satır bilgisi ile excelde veri bulunamamıştır. Sütün ve satır bilgisi 0 dan başlamaktadır. Bu bilgiye dikkat ederek seçim yapınız.");
        return cellValue;
    }

    private String getPathFromResources(String fileName) {
        String path = "";
        try {
            path = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource(fileName).toURI().getPath();
        } catch (Exception e) {

        }
        return path;
    }

}