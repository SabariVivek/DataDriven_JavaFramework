package com.project.datadriventesting.excel;


import static com.project.component.TestBase.excelFile;

public class ExcelReader {

    public static String readExcelName() {
        return excelFile.fileName();
    }

    public static String readExcelTab() {
        return excelFile.tab();
    }
}