package OrangeHRMTestClasses;

import POMPageClasses.OrangeHRMLoginPage;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class OrangeHRMLoginTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(OrangeHRMLoginPage.class.getName());

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
        setUpBrowser(log, browser);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        initializeExtentReport();
    }

    @Test
    public void testLoginSucessfull() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify Login positive case");

        driver.get(baseUrl);

        // Correct login info to check positive case
        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMLoginPage.enterPassword(driver, "admin123");
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);

        // login success if u can see the dashboard screen
        boolean dashboardVisible = OrangeHRMLoginPage.dashboardLinkVisibleCheck(driver);
        Assert.assertTrue(dashboardVisible);
        test.log(Status.INFO, "Dashboard can be seen after login:" + dashboardVisible);
    }

    @Test
    public void testLoginFailedWrongPassword() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify Login incorrect password case");
        driver.get(baseUrl);

        // incorrect password to check negative case
        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        // password is incorrect
        OrangeHRMLoginPage.enterPassword(driver, "admin12");
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);

        checkInvalidLoginMessage();
    }

    @Test
    public void testLoginFailedWrongUsername() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify Login incorrect username case");
        driver.get(baseUrl);

        // incorrect username to check negative case
        OrangeHRMLoginPage.enterUserName(driver, "Admi");
        Thread.sleep(2000);
        OrangeHRMLoginPage.enterPassword(driver, "admin123");
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);

        checkInvalidLoginMessage();
    }

    @Test
    public void testLoginFailedWrongUsernameAndPassword() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify Login incorrect username and password case");
        driver.get(baseUrl);

        // incorrect username and password to check negative case
        OrangeHRMLoginPage.enterUserName(driver, "Admi");
        Thread.sleep(2000);
        OrangeHRMLoginPage.enterPassword(driver, "admin12");
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);

        checkInvalidLoginMessage();
    }

    @Test
    public void testForgotPasswordLink() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify forgot password link positive case");

        driver.get(baseUrl);

        // Correct login info to check positive case
        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickForgotPassword(driver);

        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMLoginPage.resetPasswordButton(driver);

        // login success if u can see the dashboard screen
        boolean resetEmailMessageVisible = OrangeHRMLoginPage.resetPWDEmailMessageCheck(driver);
        Assert.assertTrue(resetEmailMessageVisible);
        test.log(Status.INFO, "Reset email message is visible after password reset button click:" + resetEmailMessageVisible);
    }

    @Test
    public void testForgotPasswordLinkCancelButton() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Verify forgot password link positive case");

        driver.get(baseUrl);

        // Correct login info to check positive case
        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickForgotPassword(driver);

        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMLoginPage.cancelPasswordResetButton(driver);

        // login success if u can see the dashboard screen
        boolean backToLoginPage = OrangeHRMLoginPage.forgotPasswordLinkVisibleCheck(driver);
        Assert.assertTrue(backToLoginPage);
        test.log(Status.INFO, "Cancel password reset button brings you back to login page:" + backToLoginPage);
    }

    private void checkInvalidLoginMessage() {
        // should see invalid login red text
        boolean errorVisible = OrangeHRMLoginPage.invalidLoginMessageCheck(driver);
        Assert.assertTrue(errorVisible);
        test.log(Status.INFO, "Invalid login message was found: " + errorVisible);
    }
}