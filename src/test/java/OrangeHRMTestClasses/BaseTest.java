package OrangeHRMTestClasses;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;

public class BaseTest {

    protected ExtentReports extent;
    protected ExtentTest test;

    public  WebDriver driver;
    public String baseUrl = "https://opensource-demo.orangehrmlive.com";

    public void setUpBrowser(Logger log, String browser) {
        log.info("*******************Browser to use : " + browser);
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            log.info("Browser type is not supported");
        }
    }

    public void initializeExtentReport() {
        // Set up the Extent Reporting infrastructure
        extent = new ExtentReports();
        extent.setSystemInfo("Project Name", "OrangeHrm HW Project");
        extent.setSystemInfo("Organisation", "Bhavana Kasarla");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReports/ExtentReport.html");
        extent.attachReporter(sparkReporter);
    }

    @AfterMethod
    public void attachScreenshot(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String path = takeScreenshot(testResult.getName());
            test.addScreenCaptureFromPath("." + path);
            test.log(Status.FAIL, "Test Method Failed: " + testResult.getName());
        }
    }
    private String takeScreenshot(String fileName) throws IOException {
        fileName = fileName + ".png";
        String directory = "./ExtentReports/Screenshots//";
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(directory + fileName));
        return directory + fileName;
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        extent.flush();
    }
}
