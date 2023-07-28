package com.project.component;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.project.component.reporting.ExtentManager;
import com.project.datadriventesting.excel.ExcelPath;
import com.project.datadriventesting.json.JsonPath;
import com.project.utils.DataLoader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class TestBase extends Utils {
    public static ExcelPath excelFile;
    public static JsonPath jsonFile;
    public static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> parentTestThreadLocal = new ThreadLocal<>();
    private final HashMap<String, ExtentTest> extentMap = new HashMap<>();
    public static ThreadLocal<ExtentTest> child = new ThreadLocal<>();
    protected ThreadLocal<String> testNameParameter = new ThreadLocal<>();
    protected static ThreadLocal<String> categoryGroup = new ThreadLocal<>();

    @BeforeSuite
    public void beforeSuite() {
        extent = ExtentManager.getInstance();
    }

    @BeforeClass
    public void beforeClass() {
        parentTestThreadLocal.set(extent.createTest(getClass().getSimpleName()));
        extentMap.put(getClass().getSimpleName(), parentTestThreadLocal.get());
    }

    @BeforeMethod
    public void beforeMethod(final Method method) {
        setTestCategory();
        child.set(extentMap.get(getClass().getSimpleName()).createNode(method.getName() + dataProviderString()));
        chooseAndLaunchBrowser(DataLoader.getInstance().getBrowserType());
        driver.get().get(DataLoader.getInstance().getURL());
        info("<b><<<< Execution Started >>>>></b>");
        System.out.println("<<<<< STARTING TEST : " + method.getName() + " >>>>>");
    }

    @AfterMethod
    public void afterMethod(ITestResult result, final Method method) {
        child.get().assignCategory(categoryGroup.get());
        info("<b><<<< Execution Ended >>>>></b>");
        readTestStatusAndLogResults(result, method);
        driver.get().close();
        System.out.println("<<<<< ENDING TEST : " + method.getName() + " >>>>>");
    }

    @AfterSuite
    public void afterSuite() {
        addInformationToExtentReport();
        extent.flush();
    }

    private void addInformationToExtentReport() {
        try {
            extent.setSystemInfo("OS : ", System.getProperty("os.name"));
            extent.setSystemInfo("OS Architecture : ", System.getProperty("os.arch"));
            extent.setSystemInfo("User Name : ", System.getProperty("user.name"));
            extent.setSystemInfo("Machine Name : ", System.getProperty("machine.name"));
            extent.setSystemInfo("IP Address : ", System.getProperty("machine.address"));
            extent.setSystemInfo("Java Version : ", System.getProperty("java.version"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readTestStatusAndLogResults(ITestResult result, Method method) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                addScreenCaptureOnFailure(method.getName(), getDriver());
                child.get().log(Status.FAIL, "Test FAILED: " + method.getName());
            }
            if (result.getStatus() == ITestResult.SUCCESS) {
                child.get().log(Status.PASS, "Test PASSED: " + method.getName());
            }
            if (result.getStatus() == ITestResult.SKIP) {
                child.get().skip("Test SKIPPED: " + method.getName());
            }
        } catch (Exception ex) {
            addScreenCaptureOnFailure("Unable to log failure", getDriver());
        }
    }

    private void setTestCategory() {
        try {
            categoryGroup.set(getClass().getSimpleName());
        } catch (ArrayIndexOutOfBoundsException e) {
            categoryGroup.set("unknownCategory");
        }
    }

    private String dataProviderString() {
        if (testNameParameter.get() != null) {
            return " (" + testNameParameter.get() + ")";
        }
        return "";
    }

    public static void info(String description) {
        child.get().info(description);
    }

    public static void fail(String Description) {
        child.get().fail(Description);
    }

    public static void warning(String Description) {
        child.get().warning(Description);
    }

    public static void pass(String description) {
        child.get().pass(description);
    }

    public void addScreenCaptureOnFailure(String Description, WebDriver driver) {
        child.get().fail(Description, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64(driver)).build());
    }

    public String getBase64(WebDriver driver) {
        String base = "";
        try {
            base = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base;
    }
}