package com.project.component;

import com.google.gson.JsonObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GenericMethods extends TestBase {

    public GenericMethods(WebDriver driver) {
        this.driver.set(driver);
    }

    public static WebDriverWait wait;

    public enum Until {
        EXISTS, VISIBLE, HIDDEN, GONE;
    }

    public WebElement waitForElement(WebElement element) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void typeInto(WebElement element, String fieldName, String value) {
        waitForElement(element).sendKeys(value);
        pass("Successfully entered the value (" + value + ") in " + fieldName + " field");
    }

    public void clickElement(WebElement element, String elementName) {
        waitForElement(element).click();
        pass("Successfully clicked on the \"" + elementName + "\" field");
    }

    public void pageIdentifier(WebElement element, String pageName) {
        if (element.isDisplayed()) {
            pass("User is in " + pageName + " name");
        } else {
            fail("User is not in " + pageName + " name");
        }
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public void verifyTitle(String title) {
        if (title.trim().equalsIgnoreCase("")) {
            pass("Successfully verified the title : MATCHED");
        } else {
            fail("Successfully verified the title : NOT MATCHED");
        }
    }

    public String getWebElementText(WebElement element) {
        return element.getText();
    }

    public String getWebElementText(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    public boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }

    public boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public boolean isElementSelected(WebElement element) {
        return element.isSelected();
    }

    public String getAlertText() {
        Alert alert = getDriver().switchTo().alert();
        return alert.getText();
    }

    public String getJsonValue(JsonObject object, String value) {
        return object.get(value).toString().replace("\"", "").trim();
    }

    public String getExcelValue(Map<Object, Object> map, String mapKey) {
        return map.get(mapKey).toString().trim();
    }

    public void verifyWebElementText(WebElement element, String valueToBeMatched, String fieldName) {
        if (waitForElement(element).getText().equalsIgnoreCase(valueToBeMatched)) {
            pass("Successfully verified the \"" + fieldName + "\" : " + valueToBeMatched);
        } else {
            fail("Value mismatch in \"" + fieldName + "\" : " + valueToBeMatched);
        }
    }
}