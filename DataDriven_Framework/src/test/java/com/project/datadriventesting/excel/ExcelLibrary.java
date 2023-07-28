package com.project.datadriventesting.excel;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Iterator;

import static com.project.component.Constants.EXCEL;
import static com.project.component.Constants.FILE_PATH;

public class ExcelLibrary {

    /**
     * 1. Code for opening the Excel...
     * 2. Code for opening the Sheet...
     */
    public Object[][] readFromExcelTabWithHeaders(String excelName, String tabName) {
        Workbook wb = null;

        //1...
        try {
            wb = WorkbookFactory.create(new File(FILE_PATH + EXCEL + "/" + excelName + ".xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2...
        assert wb != null;
        Sheet sheet = wb.getSheet(tabName);
        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(rows).getPhysicalNumberOfCells();
        Object[][] array = new Object[rows + 1][cols];
        int counter = 0;

        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();
            for (int y = 0; y < cols; ) {
                while (cellIterator.hasNext()) {
                    Object value = null;
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            value = Math.round(Float.parseFloat(String.valueOf(Double.valueOf(cell.getNumericCellValue()))));
                            break;
                        case STRING:
                        case BLANK:
                            value = String.valueOf(cell.getStringCellValue());
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                    }
                    if (value != null) {
                        array[counter][y] = value;
                        y++;
                    }
                }
            }
            counter++;
        }
        return array;
    }
}