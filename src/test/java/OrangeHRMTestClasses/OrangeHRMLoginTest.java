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
    private String userName;
    private String password;

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
        setUpBrowser(log, browser);
        driver.get(baseUrl);
    }

    @Test
    public void testLoginSucessfull() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Login Page: Verify Login positive case");

        driver.get(baseUrl);

        userName = "Admin";
        password = "admin123";

        // Correct login info to check positive case
        OrangeHRMLoginPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMLoginPage.enterPassword(driver, password);
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);

        test.log(Status.INFO, "Login button clicked with user name: "+ userName+" and password:"+ password);

        // login success if u can see the dashboard screen
        boolean dashboardVisible = OrangeHRMLoginPage.dashboardLinkVisibleCheck(driver);
        Assert.assertTrue(dashboardVisible);
        test.log(Status.INFO, "Dashboard can be seen after valid login:" + dashboardVisible);
    }

    @Test
    public void testLoginFailedWrongPassword() throws InterruptedException {
        test = extent.createTest("Login Page: Verify Login incorrect password case");
        driver.get(baseUrl);

        userName = "Admin";
        password = "admin12";

        // incorrect password to check negative case
        OrangeHRMLoginPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        // password is incorrect
        OrangeHRMLoginPage.enterPassword(driver, password);
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);

        test.log(Status.INFO, "Login button clicked with user name: "+ userName+" and password:"+ password);

        checkInvalidLoginMessage();
    }

    @Test
    public void testLoginFailedWrongUsername() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Login Page: Verify Login incorrect username case");
        driver.get(baseUrl);

        userName = "Admi";
        password = "admin123";

        // incorrect username to check negative case
        OrangeHRMLoginPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMLoginPage.enterPassword(driver, password);
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);
        test.log(Status.INFO, "Login button clicked with user name: "+ userName+" and password:"+ password);

        checkInvalidLoginMessage();
    }

    @Test
    public void testLoginFailedWrongUsernameAndPassword() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Login Page: Verify Login incorrect username and password case");
        driver.get(baseUrl);

        userName = "Admi";
        password = "admin12";

        // incorrect username and password to check negative case
        OrangeHRMLoginPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMLoginPage.enterPassword(driver, password);
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickLogin(driver);
        test.log(Status.INFO, "Login button clicked with user name: "+ userName+" and password:"+ password);

        checkInvalidLoginMessage();
    }

    @Test
    public void testForgotPasswordLink() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Login Page: Verify forgot password link positive case");

       driver.get(baseUrl);

        userName = "Admin";
        // Correct login info to check positive case
        OrangeHRMLoginPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickForgotPassword(driver);
        test.log(Status.INFO, "Forgot password link clicked with user name: "+ userName);

        OrangeHRMLoginPage.enterUserName(driver, "Admin");
        Thread.sleep(2000);
        OrangeHRMLoginPage.resetPasswordButton(driver);
        test.log(Status.INFO, "On popup window, reset password button clicked with user name: "+ userName);

        boolean resetEmailMessageVisible = OrangeHRMLoginPage.resetPWDEmailMessageCheck(driver);
        Assert.assertTrue(resetEmailMessageVisible);
        test.log(Status.INFO, "Reset email message alert is visible after password reset button click: "
                             + resetEmailMessageVisible);
    }

    @Test
    public void testForgotPasswordLinkCancelButton() throws InterruptedException {
        // Create a new Test section inside the Extent Report
        test = extent.createTest("Login Page: Verify forgot password link positive case");

        driver.get(baseUrl);

        userName = "Admin";
        // Correct login info
        OrangeHRMLoginPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMLoginPage.clickForgotPassword(driver);
        test.log(Status.INFO, "Forgot password link clicked with user name: "+ userName);

        OrangeHRMLoginPage.enterUserName(driver, userName);
        Thread.sleep(2000);
        OrangeHRMLoginPage.cancelPasswordResetButton(driver);
        test.log(Status.INFO, "On popup window, cancel password reset button clicked with user name: "+ userName);

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