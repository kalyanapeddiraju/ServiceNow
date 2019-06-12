package Utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestBase {

    public static WebDriver webDriver;
    static File junitReport;
    static BufferedWriter junitWriter;

    @BeforeClass
    public static void setup() {
        String driverPath = System.getProperty("user.dir") + File.separator + "chromedriver 4";
        System.out.println("Using driver file at below location to initialize selenium...\n" + driverPath);
        System.setProperty("webdriver.chrome.driver", driverPath);
        webDriver = new ChromeDriver();
    }

    public static WebDriver getDriver() {
        return webDriver;
    }

    @AfterClass
    public static void teardown() {
        if (webDriver != null)
            webDriver.quit();
    }

    public int getNumberOfWindows(WebDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        return driver.getWindowHandles().size();
    }

    public void switchToSecondWindow(WebDriver driver) {
        String currentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        ArrayList<String> all = new ArrayList<>(allWindows);
        for (int i = 0; i < 2; i++) {
            if (!currentWindow.equals(all.get(i))) {
                driver.switchTo().window(all.get(i));
            }
        }
    }

    @BeforeClass
    public static void setUp() throws IOException, IOException {

        String junitReportFile = System.getProperty("user.dir")
                + File.separator + "junitReportFile.html";
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date = new Date();
        junitReport = new File(junitReportFile);
        junitWriter = new BufferedWriter(new FileWriter(junitReport, true));
        junitWriter.write("<html><body>");
        junitWriter.write("<h1>Test Execution Summary - " + dateFormat.format(date)
                + "</h1>");

    }

    @AfterClass
    public static void tearDown() throws IOException {
        junitWriter.write("</body></html>");
        junitWriter.close();
//        Desktop.getDesktop().browse(junitReport.toURI());
    }

    @Rule
    public TestRule watchman = new TestWatcher() {

        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void succeeded(Description description) {
            try {
                junitWriter.write(description.getDisplayName() + " "
                        + "success!");
                junitWriter.write("<br/>");
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }

        @Override
        protected void failed(Throwable e, Description description) {
            try {
                junitWriter.write(description.getDisplayName() + " "
                        + e.getClass().getSimpleName());
                junitWriter.write("<br/>");
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    };

    protected void assertEqualsR(ExtentTest test, String message, Object expected, Object actual) {
        try {
            assertEquals(message, expected, actual);
        } catch (Throwable T) {
            test.log(LogStatus.FAIL, "Test Failed >>>" + T);
            throw T;
        }
    }

    protected void assertTrueR(ExtentTest test, String message, boolean condition) {
        try {
            assertTrue(message, condition);
        } catch (Throwable T) {
            test.log(LogStatus.FAIL, "Test Failed >>>" + T);
            throw T;
        }
    }
    protected void assertFalseR(ExtentTest test, String message, boolean condition) {
        try {
            assertFalse(message, condition);
        } catch (Throwable T) {
            test.log(LogStatus.FAIL, "Test Failed >>>" + T);
            throw T;
        }
    }
}