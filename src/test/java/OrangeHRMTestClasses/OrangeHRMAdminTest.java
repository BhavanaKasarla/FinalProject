package OrangeHRMTestClasses;

import POMPageClasses.OrangeHRMAdminPage;
import POMPageClasses.OrangeHRMLeavePage;
import POMPageClasses.OrangeHRMLoginPage;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class OrangeHRMAdminTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(OrangeHRMAdminPage.class.getName());

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
        OrangeHRMAdminPage.clickAdminPage(driver);
        Thread.sleep(2000);
    }

    @Test
    public void testResetSuccessfull() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Admin Page: Verify rest button on the top search panel works");
        log.info("********Admin Page*****checking reset button on admin page works" );

        OrangeHRMAdminPage.clickAdminPage(driver);
        Thread.sleep(2000);

        OrangeHRMAdminPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterUserRole(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterEmployeeName(driver, "Paul");
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterStatus(driver, "Enabled");
        Thread.sleep(2000);

        test.log(Status.INFO, "Reset button clicked by entering UserName/UserRole/EmployeeName/Status as Admin/Admin/Paul/Enabled");

        OrangeHRMAdminPage.clickReset(driver);
        boolean allEmpty = OrangeHRMAdminPage.checkAllSearchFieldsEmpty(driver);

        Assert.assertTrue(allEmpty);
        test.log(Status.INFO, "All search fields are back to original values after reset:" + allEmpty);
    }

    @Test
    public void testSearchSuccessfully() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Admin Page: Verify that search button on the top search panel works");
        log.info("*******Admin Page:******checking search button works for user name and user role" );

        OrangeHRMAdminPage.clickAdminPage(driver);
        Thread.sleep(2000);

        String userName = "Admin";
        String userRole = "Admin";
        String status = "Enabled";
        OrangeHRMAdminPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterUserRole(driver, userRole);
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterStatus(driver, status);
        Thread.sleep(2000);

        test.log(Status.INFO, "Search button clicked after entering UserName/UserRole/Status as Admin/Admin/Enabled");
        OrangeHRMAdminPage.clickSearch(driver);
        Thread.sleep(2000);

        try {
            // remove 1 row as header info is also counted in the row count
            int rowCount = OrangeHRMAdminPage.checkSearchResultRows(driver, userName, userRole, status);
            if (rowCount>=1) {
                Assert.assertTrue(true);
                test.log(Status.PASS, "One record found for userName/userRole: " + userName + "/"+ userRole);
            } else {
                test.log(Status.FAIL, "No data present for the search by userName/userRole: " + userName + "/"+ userRole);
                Assert.fail();
            }
        } catch (Exception e) {
            ErrorOccured(e);
        }
    }


    @Test
    public void testDeleteSuccessfully() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Admin Page: Verify that delete button on the search result rows works");
        log.info("*******Admin Page******checking delete button by user role works" );

        OrangeHRMAdminPage.clickAdminPage(driver);
        Thread.sleep(2000);

        String userRole = "ESS";
        OrangeHRMAdminPage.enterUserRole(driver, userRole);
        Thread.sleep(2000);

        OrangeHRMAdminPage.clickSearch(driver);
        Thread.sleep(2000);
        test.log(Status.INFO, "Search button clicked after entering UserRole as : "+userRole);

        try {
            // remove 1 as header info is also counted in the row count
            boolean deletedFirstRecord = OrangeHRMAdminPage.checkDeleteFirstRecord(driver, userRole);
            if (deletedFirstRecord) {
                Assert.assertTrue(true);
                test.log(Status.PASS, "Deleted first record with user role : " + userRole);
            } else {
                test.log(Status.FAIL, "No data present or unable to delete for the search by userRole: " + userRole);
                Assert.fail();
            }
        } catch (Exception e) {
            ErrorOccured(e);
        }
    }

    @Test
    public void testSearchForMissingEmployeeName() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Admin Page: Verify that error is flagged when non existing employee name is entered");
        log.info("*******Admin Page:******checking error is flagged for entering missing or wrong employee name" );

        OrangeHRMAdminPage.clickAdminPage(driver);
        Thread.sleep(2000);

        String employeeName = "unkown";
        OrangeHRMAdminPage.enterEmployeeName(driver, employeeName);
        Thread.sleep(2000);
        OrangeHRMAdminPage.clickSearch(driver);
        Thread.sleep(2000);
        test.log(Status.INFO, "Employee name entered as: "+ employeeName);

        boolean error = OrangeHRMAdminPage.checkEmployeeNameHasError(driver);
        log.info("Employee name field has error" );

        if (error) {
            Assert.assertTrue(true);
            test.log(Status.PASS, "Error flagged for entering missing employee name: " + employeeName);
        } else {
            test.log(Status.FAIL, "Error not flagged for entering missing employee name: " + employeeName);
            Assert.fail();
        }
    }

    private void ErrorOccured(Exception e) {
        e.printStackTrace();
        test.log(Status.FAIL, "Exception occurred. Test Failed.");
        Assert.fail();
    }

}