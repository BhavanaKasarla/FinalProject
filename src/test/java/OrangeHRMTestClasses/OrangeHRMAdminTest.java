package OrangeHRMTestClasses;

import POMPageClasses.OrangeHRMAdminPage;
import POMPageClasses.OrangeHRMLoginPage;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        OrangeHRMAdminPage.clickAdminPage(driver);
        Thread.sleep(2000);
    }

    @Test
    public void testResetSuccessfull() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify rest button on the top search panel works");
        log.info("*************checking reset button on admin page works" );

        OrangeHRMAdminPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterUserRole(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterEmployeeName(driver, "Paul");
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterStatus(driver, "Enabled");
        Thread.sleep(2000);

        OrangeHRMAdminPage.clickReset(driver);
        boolean allEmpty = OrangeHRMAdminPage.checkAllSearchFieldsEmpty(driver);

        Assert.assertTrue(allEmpty);
        test.log(Status.INFO, "All search fields are back to original values after reset:" + allEmpty);
    }

    @Test
    public void testSearchSuccessfully() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify that search button on the top search panel works");
        log.info("*************checking search button works for user name and user role" );

        String userName = "Admin";
        String userRole = "Admin";
        String status = "Enabled";
        OrangeHRMAdminPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterUserRole(driver, userRole);
        Thread.sleep(2000);
        OrangeHRMAdminPage.enterStatus(driver, status);
        Thread.sleep(2000);

        OrangeHRMAdminPage.clickSearch(driver);
        // remove 1 as header info is also counted in the row count
        int rowCount = OrangeHRMAdminPage.checkSearchResultRows(driver, userName, userRole, status);


        Assert.assertTrue(rowCount == 1);
        test.log(Status.INFO, "Should have only 1 record for admin:" + rowCount);
    }

    @Test
    public void testDeleteSuccessfully() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify that delete button on the search result rows works");
        log.info("*************checking delete button by user role works" );

        String userRole = "ESS";
        OrangeHRMAdminPage.enterUserRole(driver, userRole);
        Thread.sleep(2000);

        OrangeHRMAdminPage.clickSearch(driver);
        Thread.sleep(2000);
        // remove 1 as header info is also counted in the row count
        boolean deletedFirstRecord = OrangeHRMAdminPage.checkDeleteFirstRecord(driver, userRole);

        Assert.assertTrue(deletedFirstRecord);
        test.log(Status.INFO, "Deleted first record with user role :" + userRole);
    }

}