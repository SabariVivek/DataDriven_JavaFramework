package com.project.tests;

import com.google.gson.JsonObject;
import com.project.component.TestBase;
import com.project.datadriventesting.TestDataProvider;
import com.project.datadriventesting.excel.ExcelPath;
import com.project.datadriventesting.json.JsonPath;
import com.project.pages.LoginPageWithExcelData;
import com.project.pages.LoginPageWithJsonData;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.util.Map;

public class LoginTest extends TestBase {

    ThreadLocal<LoginPageWithJsonData> loginWithJson = new ThreadLocal<>();
    ThreadLocal<LoginPageWithExcelData> loginWithExcel = new ThreadLocal<>();

    /**
     * Login Functionality with Json Data...
     */
    @JsonPath(fileName = "Login")
    @Test(description = "Login Functionality with Json Data", groups = {"Regression"}, dataProvider = "getDataFromJsonFile", dataProviderClass = TestDataProvider.class)
    public void loginTestWithJsonData(JsonObject object) {
        loginWithJson.set(PageFactory.initElements(getDriver(), LoginPageWithJsonData.class));
        loginWithJson.get().loginFunctionality(object);
    }

    /**
     * Login Functionality with Excel Data...
     */
    @ExcelPath(fileName = "Login", tab = "Sheet1")
    @Test(description = "Login Functionality with Excel Data", groups = {"Regression"}, dataProvider = "getDataFromExcelTabAsMap", dataProviderClass = TestDataProvider.class)
    public void loginTestWithExcelData(Map<Object, Object> map) {
        loginWithExcel.set(PageFactory.initElements(getDriver(), LoginPageWithExcelData.class));
        loginWithExcel.get().loginFunctionality(map);
    }
}