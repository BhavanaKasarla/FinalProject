package POMPageClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class OrangeHRMAdminPage extends BasePage {
    public static WebElement element = null;

    // Locators of the Elements present in this page
    private static final String ADMIN_SIDE_PANEL_LINK = "//a[contains(@href,'viewAdmin')]";
    private static final String USER_NAME_FIELD = "//div[contains(@class,'table-filter')]//input[contains(@class,'input')]";
    private static final String USER_ROLE_FIELD = "//label[contains(text(),'User Role')]//ancestor::div[contains(@class,'input-group')]//div[contains(@class,'select-text-input')]";
    private static final String EMPLOYEE_NAME_FIELD = "//input[contains(@placeholder, 'Type for hints')]";
    private static final String EMPLOYEE_NAME_ERROR = "//span[contains(@class,'error')]";
    private static final String STATUS_FIELD = "//label[contains(text(),'Status')]//ancestor::div[contains(@class,'input-group')]//div[contains(@class,'select-text-input')]";
    private static final String SEARCH_BUTTON = "//button[contains(@type, 'submit')]";
    private static final String RESET_BUTTON = "//div[contains(@class, 'form-actions')]//button[1]";
    private static final Logger log = LogManager.getLogger(OrangeHRMAdminPage.class.getName());

    // Web elements from the locators
    public static WebElement adminSidePanelLink(WebDriver driver) {
        element = driver.findElement(By.xpath(ADMIN_SIDE_PANEL_LINK));
        return element;
    }
    public static WebElement userNameField(WebDriver driver) {
        element = driver.findElement(By.xpath(USER_NAME_FIELD));
        return element;
    }

    public static WebElement employeeNameField(WebDriver driver) {
        element = driver.findElement(By.xpath(EMPLOYEE_NAME_FIELD));
        return element;
    }

    public static WebElement userRoleField(WebDriver driver) {
        element = driver.findElement(By.xpath(USER_ROLE_FIELD));
        return element;
    }

    public static WebElement statusField(WebDriver driver) {
        element = driver.findElement(By.xpath(STATUS_FIELD));
        return element;
    }

    public static WebElement searchButton(WebDriver driver) {
        element = driver.findElement(By.xpath(SEARCH_BUTTON));
        return element;
    }

    public static WebElement resetButton(WebDriver driver) {
        element = driver.findElement(By.xpath(RESET_BUTTON));
        return element;
    }

    public static WebElement employeeNameError(WebDriver driver) {
        element = driver.findElement(By.xpath(EMPLOYEE_NAME_ERROR));
        return element;
    }

    // Methods to perform actions on the elements in this page
    public static void enterUserName(WebDriver driver, String userName) {
        element = userNameField(driver);
        element.clear();
        element.sendKeys(userName);
        log.info("User name entered as: " + userName);
    }

    public static void enterEmployeeName(WebDriver driver, String employeeName) {
        element = employeeNameField(driver);
        element.clear();
        element.sendKeys(employeeName);
        log.info("Employee name entered as: " + employeeName);
    }
    public static void enterUserRole(WebDriver driver, String userRole)  {
        element = userRoleField(driver);

        Actions action = new Actions(driver);
        action.moveToElement(element).click().perform();

        // click on the user role you want from the dropdown
        WebElement customOptionValue = driver.findElement(
                By.xpath("//span[contains(text(),'"+userRole+"')]"));
        clickWhenReady(driver, customOptionValue, 2000);

        log.info("User role entered as: " + userRole);
    }
    public static void enterStatus(WebDriver driver, String status) {
        element = statusField(driver);

        Actions action = new Actions(driver);
        action.moveToElement(element).click().perform();

        // click on the user role you want from the dropdown
        WebElement customOptionValue = driver.findElement(
                By.xpath("//span[contains(text(),'"+status+"')]"));
        clickWhenReady(driver, customOptionValue, 2000);

        log.info("Status entered as: " + status);
    }

    public static void clickAdminPage(WebDriver driver) {
        element = adminSidePanelLink(driver);
        element.click();
        log.info("Clicked the admin side panel link");
    }

    public static void clickSearch(WebDriver driver) {
        element = searchButton(driver);
        element.click();
        log.info("Clicked the search button:");
    }

    public static void clickReset(WebDriver driver) {
        element = resetButton(driver);
        element.click();
        log.info("Clicked the reset button:");
    }
    public static boolean checkUserNameIsEmpty(WebDriver driver) {
        element = userNameField(driver);

        if (element != null && element.getText().isEmpty()) {
            log.info("User name field has empty value");
            return true;
        }
        return false;
    }

    public static boolean checkUserRoleIsEmpty(WebDriver driver) {
        element = userRoleField(driver);

        if (element != null && element.getText().contains("Select")) {
            log.info("User role field has value: " + element.getText());
            return true;
        }
        return false;
    }

    public static boolean checkEmployeeNameIsEmpty(WebDriver driver) {
        element = employeeNameField(driver);

        if (element != null && element.getText().isEmpty()) {
            log.info("Employee name field has empty value" );
            return true;
        }
        return false;
    }

    public static boolean checkEmployeeNameHasError(WebDriver driver) {
        WebElement element = employeeNameError(driver);

        return element != null;
    }

    public static boolean checkStatusIsEmpty(WebDriver driver) {
        element = statusField(driver);

        if (element != null && element.getText().contains("Select")) {
            log.info("Status field has value: " + element.getText());
            return true;
        }
        return false;
    }

    public static boolean checkAllSearchFieldsEmpty(WebDriver driver) {
        boolean first = OrangeHRMAdminPage.checkUserNameIsEmpty(driver);
        boolean second = OrangeHRMAdminPage.checkUserRoleIsEmpty(driver);
        boolean third =OrangeHRMAdminPage.checkEmployeeNameIsEmpty(driver);
        boolean fourth = OrangeHRMAdminPage.checkStatusIsEmpty(driver);

        return first && second && third && fourth;
    }

    public static boolean checkDeleteFirstRecord(WebDriver driver,  String userRole) {
        // findElements will return a list of table row elements
        List<WebElement> rowWebElements = getRowWebElements(driver);
        log.info("Number of search records found for delete: " + rowWebElements.size());

        if (rowWebElements.size() > 0) {
            List<WebElement> cellWebElements = rowWebElements.get(0).
                    findElements(By.xpath("//div[contains(@class,'table-cell')]/div"));
            log.info("Number of cells found: " + cellWebElements.size());

            if(cellWebElements.size() > 0) {
                if (cellWebElements.get(2).getText().equals(userRole)) {
                    // click on the delete button on the first search result
                    WebElement deleteButton = driver.findElement(By.xpath("//div[contains(@class,'table-cell')]//button[contains(@class,'action')][1]"));
                    clickWhenReady(driver, deleteButton, 2000);
                    log.info("Delete button clicked on first search result row ");
                    WebElement yesAlertButton = driver.findElement(By.xpath("//button[contains(@class,'label-danger')]"));
                    clickWhenReady(driver, yesAlertButton, 2000);
                    log.info("Click on the yes button for delete alert popup ");
                }
                List<WebElement> newRowWebElements = getRowWebElements(driver);
                log.info("After delete the number of rows found was: "+newRowWebElements.size());
                return newRowWebElements.size() == rowWebElements.size() - 1;
            }
        }
        return false;
    }

    public static int checkSearchResultRows(WebDriver driver, String username, String userRole, String enabled) {

        List<WebElement> rowWebElements = getRowWebElements(driver);
        log.info("Number of search records found: " + rowWebElements.size());

        int searchResultRows = 0;
        for (WebElement rowWebElement : rowWebElements) {
            // Find all the cells under the rows
            List<WebElement> cellWebElements = rowWebElement.findElements(By.xpath("//div[contains(@class,'table-cell')]/div"));
            log.debug("Number of cells found: " + cellWebElements.size());

            if (cellWebElements.size() > 0 &&
                    cellWebElements.get(1).getText().equals(username) &&
                    cellWebElements.get(2).getText().equals(userRole) &&
                    cellWebElements.get(4).getText().equals(enabled))
            {
                // this search result matches the search criteria
                searchResultRows++;
                log.info("search result match found: " + searchResultRows);
            }
        }
        return searchResultRows;
    }

    private static List<WebElement> getRowWebElements(WebDriver driver) {
        // findElements will return a list of table elements
        List<WebElement> rowWebElements = driver.findElements(By.xpath("//div[contains(@class,'table-row')]"));
        // remove the header row
        rowWebElements.remove(0);
        return rowWebElements;
    }

}
