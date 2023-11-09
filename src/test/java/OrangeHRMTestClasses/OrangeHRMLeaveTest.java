package OrangeHRMTestClasses;

import POMPageClasses.OrangeHRMAdminPage;
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
        test = extent.createTest("Leave Page: Verify reset button on the top search panel works");
        log.info("*********Leave Page****checking reset button in leaves page works" );

        OrangeHRMLeavePage.clickLeavePage(driver);
        Thread.sleep(2000);

        OrangeHRMLeavePage.enterFromDate(driver, "3", "");
        Thread.sleep(2000);
        test.log(Status.INFO, "From date entered as 3rd January");
        OrangeHRMLeavePage.enterToDate(driver, "27", "");
        Thread.sleep(2000);
        test.log(Status.INFO, "To date entered as 27th December");
        OrangeHRMLeavePage.enterLeaveStatus(driver, "Rejected");
        Thread.sleep(2000);
        test.log(Status.INFO, "Status entered as Rejected");
        OrangeHRMLeavePage.enterLeaveType(driver, "CAN - Vacation");
        Thread.sleep(2000);
        test.log(Status.INFO, "Leave type entered as CAN - Vacation");
        OrangeHRMLeavePage.enterEmployeeName(driver, "Jane");
        Thread.sleep(2000);
        test.log(Status.INFO, "Employee name entered as Jane");
        OrangeHRMLeavePage.enterSubunitField(driver, "Administration");
        Thread.sleep(2000);
        test.log(Status.INFO, "Subunit entered as Administration");
        OrangeHRMLeavePage.togglePastEmployeeSwitch(driver);
        Thread.sleep(2000);
        test.log(Status.INFO, "Past Employee toggle button changed");

        OrangeHRMLeavePage.clickReset(driver);
        test.log(Status.INFO, "Reset button clicked");
        boolean allEmpty = OrangeHRMLeavePage.checkAllSearchFieldsEmpty(driver);

        Assert.assertTrue(allEmpty);
        test.log(Status.INFO, "All search fields are back to original values after reset:" + allEmpty);
    }

    @Test
    public void testSearchForLeaveType() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Leave Page: Verify that search button  works for leave type");
        log.info("******Leave Page:*******checking search button by leave type works" );

        OrangeHRMLeavePage.clickLeavePage(driver);
        Thread.sleep(2000);

        String leaveType = "CAN - Vacation";
        OrangeHRMLeavePage.enterFromDate(driver, "3", "2022");
        Thread.sleep(2000);
        test.log(Status.INFO, "From date entered as 3rd January 2022");
        OrangeHRMLeavePage.enterLeaveType(driver, leaveType);
        Thread.sleep(2000);
        test.log(Status.INFO, "Leave type entered as : " + leaveType);

        OrangeHRMLeavePage.clickSearch(driver);
        test.log(Status.INFO, "Search button clicked");
        Thread.sleep(2000);

        ConfirmSearchRecords(leaveType, 3);
    }

    @Test
    public void testSearchForEmployeeName() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Leave Page: Verify that search button works for employee name search");
        log.info("******Leave Page*******checking search button by employee name works" );

        OrangeHRMLeavePage.clickLeavePage(driver);
        Thread.sleep(2000);

        String employeeName = "jane";
        OrangeHRMLeavePage.enterFromDate(driver, "3", "2022");
        test.log(Status.INFO, "From date entered as 3rd January 2022");
        Thread.sleep(2000);
        OrangeHRMLeavePage.enterEmployeeName(driver, employeeName);
        Thread.sleep(2000);
        test.log(Status.INFO, "Employee name entered as: "+ employeeName);

        OrangeHRMLeavePage.clickSearch(driver);
        Thread.sleep(2000);
        test.log(Status.INFO, "Click the search button");

        ConfirmSearchRecords(employeeName, 2);
    }
    @Test
    public void testSearchForMissingEmployeeName() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Leave Page: Verify that error is flagged when non existing employee name is entered");
        log.info("*****Leave Page********checking error is flagged for entering missing or wrong employee name" );

        OrangeHRMLeavePage.clickLeavePage(driver);
        Thread.sleep(2000);

        String employeeName = "unkown";
        OrangeHRMLeavePage.enterEmployeeName(driver, employeeName);
        Thread.sleep(2000);
        OrangeHRMLeavePage.clickSearch(driver);
        test.log(Status.INFO, "Employee name entered as: "+ employeeName);
        log.info("Employee name field has error" );

        boolean error = OrangeHRMLeavePage.checkEmployeeNameHasError(driver);
        Thread.sleep(2000);

        if (error) {
            Assert.assertTrue(true);
            test.log(Status.PASS, "Error flagged for entering missing employee name: " + employeeName);
        } else {
            test.log(Status.FAIL, "Error not flagged for entering missing employee name: " + employeeName);
            Assert.fail();
        }
    }

    private void ConfirmSearchRecords(String leaveType, int colIndexToSearch) {
        try {
            int rowCount = OrangeHRMLeavePage.checkSearchResultRows(driver, leaveType, colIndexToSearch);
            if (rowCount==1) {
                Assert.assertTrue(true);
                test.log(Status.PASS, "One search record found for leaveType: " + leaveType);
            } else {
                test.log(Status.FAIL, "No data present for the search by leaveType: " + leaveType);
                Assert.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Exception occurred. Test Failed.");
            Assert.fail();
        }
    }
}
