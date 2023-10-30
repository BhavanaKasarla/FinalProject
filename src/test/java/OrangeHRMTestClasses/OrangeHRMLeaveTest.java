package OrangeHRMTestClasses;

import POMPageClasses.OrangeHRMLeavePage;
import POMPageClasses.OrangeHRMLoginPage;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
public class OrangeHRMLeaveTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(OrangeHRMLeavePage.class.getName());

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) throws InterruptedException {
        setUpBrowser(log, browser);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        initializeExtentReport();

        driver.get(baseUrl);

        // login once to reach admin page
        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMLoginPage.enterPassword(driver, "admin123");
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);
        Thread.sleep(2000);
    }

    @Test
    public void testResetSuccessfull() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify rest button on the top search panel works");

        OrangeHRMLeavePage.clickLeavePage(driver);
        Thread.sleep(2000);

        OrangeHRMLeavePage.enterFromDate(driver, "3", "");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterToDate(driver, "27", "");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterLeaveStatus(driver, "Rejected");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterLeaveType(driver, "CAN - Vacation");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterEmployeeName(driver, "Jane");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterSubunitField(driver, "Administration");
        Thread.sleep(2000);
        OrangeHRMLeavePage.togglePastEmployeeSwitch(driver);
        Thread.sleep(2000);


        OrangeHRMLeavePage.clickReset(driver);
        boolean allEmpty = OrangeHRMLeavePage.checkAllSearchFieldsEmpty(driver);

        Assert.assertTrue(allEmpty);
        test.log(Status.INFO, "All search fields are back to original values after reset:" + allEmpty);
    }

    @Test
    public void testSearchForLeaveType() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify that search button on the top search panel works");

        OrangeHRMLeavePage.clickLeavePage(driver);
        Thread.sleep(2000);

        String leaveType = "CAN - Vacation";
        OrangeHRMLeavePage.enterFromDate(driver, "3", "2022");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterLeaveType(driver, leaveType);
        Thread.sleep(2000);

        OrangeHRMLeavePage.clickSearch(driver);
        Thread.sleep(2000);
        int rowCount = OrangeHRMLeavePage.checkSearchResultRows(driver, leaveType, 3);

        Assert.assertTrue(rowCount >= 1);
        test.log(Status.INFO, "Records found for search: " + rowCount);
    }

    @Test
    public void testSearchForEmployeeName() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify that search button on the top search panel works");

        OrangeHRMLeavePage.clickLeavePage(driver);
        Thread.sleep(2000);

        String employeeName = "jane";
        OrangeHRMLeavePage.enterFromDate(driver, "3", "2022");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterEmployeeName(driver, employeeName);
        Thread.sleep(2000);

        OrangeHRMLeavePage.clickSearch(driver);
        Thread.sleep(2000);
        int rowCount = OrangeHRMLeavePage.checkSearchResultRows(driver, employeeName, 2);

        Assert.assertTrue(rowCount >= 1);
        test.log(Status.INFO, "Records found for search: " + rowCount);
    }
}
