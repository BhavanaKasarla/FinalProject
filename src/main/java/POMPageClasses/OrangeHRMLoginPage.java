package POMPageClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

    public class OrangeHRMLoginPage {

        public static WebElement element = null;

        // Locators of the Elements present in this page
        private static final String USER_NAME_FIELD = "//input[contains(@name, 'username')]";
        private static final String PASSWORD_FIELD = "//input[contains(@name, 'password')]";

        private static final String LOGIN_BUTTON = "//button[contains(@class,'login')]";

        private static final String FORGOT_LINK = "//p[contains(@class,'forgot')]";

        private static final String INVALID_LOGIN_MESSAGE = "//p[contains(@class,'alert')]";

        private static final String DASHBOARD_LINK = "//a[contains(@href, 'dashboard')]";

        private static final String RESET_PASSWORD_BUTTON = "//button[contains(@class,'reset')]";

        private static final String CANCEL_PASSWORD_REST_BUTTON = "//button[contains(@class,'cancel')]";

        private static final String EMAIL_SENT_MESSAGE = "//h6[contains(@class,'forgot-password-title')]";

        private static final Logger log = LogManager.getLogger(POMPageClasses.OrangeHRMLoginPage.class.getName());

        // Web elements from the locators
        public static WebElement userNameField(WebDriver driver) {
            element = driver.findElement(By.xpath(USER_NAME_FIELD));
            return element;
        }

        public static WebElement passwordField(WebDriver driver) {
            element = driver.findElement(By.xpath(PASSWORD_FIELD));
            return element;
        }

        public static WebElement loginButton(WebDriver driver) {
            element = driver.findElement(By.xpath(LOGIN_BUTTON));
            return element;
        }

        public static WebElement forgotPasswordLink(WebDriver driver) {
            element = driver.findElement(By.xpath(FORGOT_LINK));
            return element;
        }

        public static WebElement invalidLoginMessage(WebDriver driver) {
            element = driver.findElement(By.xpath(INVALID_LOGIN_MESSAGE));
            return element;
        }

        public static WebElement dashboardLink(WebDriver driver) {
            element = driver.findElement(By.xpath(DASHBOARD_LINK));
            return element;
        }

        public static WebElement resetPasswordButton(WebDriver driver) {
            element = driver.findElement(By.xpath(RESET_PASSWORD_BUTTON));
            return element;
        }

        public static WebElement cancelPasswordResetButton(WebDriver driver) {
            element = driver.findElement(By.xpath(CANCEL_PASSWORD_REST_BUTTON));
            return element;
        }

        public static WebElement resetPasswordEmailSentMessage(WebDriver driver) {
            element = driver.findElement(By.xpath(EMAIL_SENT_MESSAGE));
            return element;
        }

        // Methods to perform actions on the elements in this page
        public static void enterUserName(WebDriver driver, String userName) {
            element = userNameField(driver);
            element.clear();
            element.sendKeys(userName);
            log.info("User name entered as: " + userName);
        }

        public static void enterPassword(WebDriver driver, String password) {
            element = passwordField(driver);
            element.clear();
            element.sendKeys(password);
            log.info("Password entered as: " + password);
        }

        public static void clickLogin(WebDriver driver) {
            element = loginButton(driver);
            element.click();
            log.info("Clicked the login button");
        }

        public static void clickForgotPassword(WebDriver driver) {
            element = forgotPasswordLink(driver);
            element.click();
            log.info("Clicked the forgot password button");
        }

        public static boolean invalidLoginMessageCheck(WebDriver driver) {
            element = invalidLoginMessage(driver);
            if (element != null) {
                log.info("Invalid login message appears: " + element.isDisplayed());
                return element.isDisplayed();
            }
            return false;
        }

        public static boolean dashboardLinkVisibleCheck(WebDriver driver) {
            element = dashboardLink(driver);
            if (element != null) {
                log.info("Dashboard link appears so login is successful: " + element.isDisplayed());
                return element.isDisplayed();
            }
            return false;
        }

        public static boolean resetPWDEmailMessageCheck(WebDriver driver) {
            element = resetPasswordEmailSentMessage(driver);
            if (element != null) {
                log.info("Email with link to reset password message appears: " + element.isDisplayed());
                return element.isDisplayed();
            }
            return false;
        }

        public static boolean forgotPasswordLinkVisibleCheck(WebDriver driver) {
            element = forgotPasswordLink(driver);
            if (element != null) {
                log.info("Email with link to reset password message appears: " + element.isDisplayed());
                return element.isDisplayed();
            }
            return false;
        }
    }


