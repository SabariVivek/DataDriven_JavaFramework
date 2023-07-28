package com.project.component.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    public static String Path = System.getProperty("user.dir") + "/Reports/" + date() + "/" + "TestClass-Report (" + time() + ").html";

    public static ExtentReports getInstance() {
        if (null == extent) createInstance(Path);
        return extent;
    }

    private static void createInstance(String fileName) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        //------ Extent Spark Report Configuration ------//
        htmlReporter.viewConfigurer().viewOrder().as(new ViewName[]{ViewName.TEST, ViewName.CATEGORY, ViewName.DEVICE, ViewName.DASHBOARD,}).apply();

        htmlReporter.config().setTimelineEnabled(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Keyword Framework - Automation Report");
        htmlReporter.config().setDocumentTitle("Automation Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static String date() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy EEE");
        return dateFormat.format(date);
    }

    public static String time() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
        return dateFormat.format(date);
    }
}