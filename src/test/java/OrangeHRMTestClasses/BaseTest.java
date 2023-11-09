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
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest implements ITestListener {
    public  static ExtentReports extent;
    public ExtentTest test;
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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
    }

    @BeforeSuite
    public void createExtentReport() {
        // Before Suite starts
        // Set up the Extent Reporting infrastructure
        if(extent == null) {
            extent = new ExtentReports();
            extent.setSystemInfo("Project Name", "OrangeHrm HW Project");
            extent.setSystemInfo("Organisation", "Bhavana Kasarla");
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReports/ExtentReport.html");
            extent.attachReporter(sparkReporter);
        }
    }
    @AfterSuite
    public void onFinish() {
        // After Suite ends
        extent.flush();
    }

    @AfterMethod
    public void onTestFailure(ITestResult testResult)   {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            // String path = takeScreenshot(testResult.getName());
            //  test.addScreenCaptureFromPath("." + path);
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
    }

}
