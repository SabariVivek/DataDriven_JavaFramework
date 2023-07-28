package com.project.datadriventesting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.project.datadriventesting.excel.ExcelLibrary;
import com.project.datadriventesting.excel.ExcelPath;
import com.project.datadriventesting.json.JsonPath;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import static com.project.component.Constants.FILE_PATH;
import static com.project.component.Constants.JSON;

public class TestDataProvider {

    @DataProvider(name = "getDataFromExcelTabAsMap")
    public static Iterator<Object[]> getDataFromExcelTabAsMap(final Method testMethod) {
        ExcelPath dataFile = testMethod.getAnnotation(ExcelPath.class);
        ExcelLibrary excelReader = new ExcelLibrary();
        Object[][] excelResults = excelReader.readFromExcelTabWithHeaders(dataFile.fileName(), dataFile.tab());
        List<Object[]> excelResultsAsMap = new ArrayList<>();
        for (int i = 1; i < excelResults.length; i++) {
            Map<Object, Object> map = new HashMap<>();
            for (int j = 0; j < excelResults[0].length; j++) {
                map.put(excelResults[0][j], excelResults[i][j]);
            }
            excelResultsAsMap.add(new Object[]{map});
        }
        return excelResultsAsMap.iterator();
    }

    @DataProvider(name = "getDataFromJsonFile")
    public static Iterator<Object[]> getDataFromJsonFile(final Method testMethod) {
        JsonPath dataFile = testMethod.getAnnotation(JsonPath.class);
        try {
            FileReader reader = new FileReader(FILE_PATH + File.separator + JSON + File.separator + dataFile.fileName() + ".json");
            JsonElement jsonData = new JsonParser().parse(reader);
            JsonArray jsonArray = jsonData.getAsJsonArray();
            ArrayList<Object[]> jsonResultAsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonResultAsList.add(new Object[]{jsonArray.get(i)});
            }
            return jsonResultAsList.iterator();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}