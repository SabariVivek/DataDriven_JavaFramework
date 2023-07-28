package com.project.component;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

import static com.project.component.Constants.*;
import static com.project.component.TestBase.pass;

public class Utils {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver() {
        return driver.get();
    }

    public void chooseAndLaunchBrowser(String browser) {
        try {
            switch (browser) {
                case CHROME_BROWSER:
                    configureChromeWebDriver();
                    break;
                case EDGE_BROWSER:
                    configureEdgeWebDriver();
                    break;
                case FIREFOX_BROWSER:
                    configureFirefoxWebDriver();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void configureChromeWebDriver() {
        try {
            HashMap<String, Object> chromePref = new HashMap<>();
            String downloadPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "test" + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator") + "Download" + System.getProperty("file.separator");
            chromePref.put("download.default_directory", downloadPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            //options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.setExperimentalOption("prefs", chromePref);
            driver.set(new ChromeDriver(options));
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            pass("Successfully launched the \"Chrome\" Browser");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void configureEdgeWebDriver() {
        try {
            EdgeOptions options = new EdgeOptions();
            options.setCapability("nativeEvents", false);
            options.setCapability("unexpectedAlertBehaviour", "accept");
            options.setCapability("ignoreProtectedModeSettings", true);
            options.setCapability("disable-popup-blocking", true);
            options.setCapability("enablePersistentHover", true);
            options.setCapability("ignoreZoomSetting", true);
            options.setCapability("useAutomationExtension", false);
            //options.addArguments("--headless");
            driver.set(new EdgeDriver(options));
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            pass("Successfully launched the \"Edge\" Browser");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void configureFirefoxWebDriver() {
        try {
            File pathBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
            DesiredCapabilities desired = new DesiredCapabilities();
            FirefoxOptions options = new FirefoxOptions();
            //options.addArguments("--headless");
            desired.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(firefoxBinary));
            driver.set(new FirefoxDriver(options));
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            pass("Successfully launched the \"Firefox\" Browser");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}