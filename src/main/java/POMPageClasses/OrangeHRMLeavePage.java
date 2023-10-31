package POMPageClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class OrangeHRMLeavePage extends BasePage {
    public static WebElement element = null;

    // Locators of the Elements present in this page
    private static final String LEAVE_SIDE_PANEL_LINK = "//a[contains(@href,'viewLeave')]";
    private static final String FROM_DATE_FIELD = "//label[contains(text(),'From')]//ancestor::div[contains(@class, 'input')]//input[contains(@class, 'input')]";
    private static final String TO_DATE_FIELD = "//label[contains(text(),'To')]//ancestor::div[contains(@class, 'input')]//input[contains(@class, 'input')]";
    private static final String LEAVE_STATUS_FIELD = "//label[contains(text(),'Status')]//ancestor::div[contains(@class,'input-group')]//div[contains(@class,'text-input')]";
    private static final String LEAVE_TYPE_FIELD = "//label[contains(text(),'Leave Type')]//ancestor::div[contains(@class,'input-group')]//div[contains(@class,'text-input')]";
    private static final String EMPLOYEE_NAME_FIELD = "//input[contains(@placeholder, 'Type for hints')]";
    private static final String SUB_UNIT_FIELD = "//label[contains(text(),'Sub')]//ancestor::div[contains(@class,'input-group')]//div[contains(@class,'text-input')]";
    private static final String PAST_EMPLOYEE_SWITCH_FIELD = "//div[contains(@class,'switch')]//input[contains(@type,'checkbox')]";
    private static final String SEARCH_BUTTON = "//button[contains(@type, 'submit')]";
    private static final String RESET_BUTTON = "//div[contains(@class, 'form-actions')]//button[1]";
    private static final Logger log = LogManager.getLogger(OrangeHRMLeavePage.class.getName());

    public static WebElement leaveSidePanelLink(WebDriver driver) {
        element = driver.findElement(By.xpath(LEAVE_SIDE_PANEL_LINK));
        return element;
    }

    // Web elements from the locators
    public static WebElement fromDateField(WebDriver driver) {
        element = driver.findElement(By.xpath(FROM_DATE_FIELD));
        return element;
    }
    public static WebElement toDateField(WebDriver driver) {
        element = driver.findElement(By.xpath(TO_DATE_FIELD));
        return element;
    }
    public static WebElement leaveStatusField(WebDriver driver) {
        element = driver.findElement(By.xpath(LEAVE_STATUS_FIELD));
        return element;
    }

    public static WebElement leaveTypeField(WebDriver driver) {
        element = driver.findElement(By.xpath(LEAVE_TYPE_FIELD));
        return element;
    }

    public static WebElement employeeNameField(WebDriver driver) {
        element = driver.findElement(By.xpath(EMPLOYEE_NAME_FIELD));
        return element;
    }

    public static WebElement subUnitField(WebDriver driver) {
        element = driver.findElement(By.xpath(SUB_UNIT_FIELD));
        return element;
    }

    public static WebElement pastEmployeeSwitchField(WebDriver driver) {
        element = driver.findElement(By.xpath(PAST_EMPLOYEE_SWITCH_FIELD));
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

    public static void clickLeavePage(WebDriver driver) {
        element = leaveSidePanelLink(driver);
        element.click();
        log.info("Clicked the leave side panel link");
    }

    // Methods to perform actions on the elements in this page
    public static void enterFromDate(WebDriver driver, String fromDay, String year) throws InterruptedException {
        element = fromDateField(driver);
        element.click();
        if (!year.isEmpty())
        {
            pickYearFromCalendar(driver, year);
        }
        log.info("From day to be entered: " + fromDay);
        pickDateFromCalendar(driver, fromDay);
    }

    public static void enterToDate(WebDriver driver, String toDay, String year) throws InterruptedException {
        element = toDateField(driver);
        element.sendKeys(Keys.PAGE_UP);
        element.click();
        if (!year.isEmpty())
        {
            pickYearFromCalendar(driver, year);
        }
        log.info("To date to be entered: " + toDay);
        pickDateFromCalendar(driver, toDay);

    }

    private static void pickDateFromCalendar(WebDriver driver, String pickDate) {
        // Get All dates which are not weekend and not holidays
        List<WebElement> nonHolidayLeaveDates = driver.findElements(
                By.xpath("//div[(@class='oxd-calendar-date-wrapper')]//div[(@class='oxd-calendar-date')]"));
        // Loop through the list of working day Dates found, and select a date
        for (WebElement leaveDate: nonHolidayLeaveDates) {
            if (leaveDate.getText().equals(pickDate)) {
                leaveDate.click();
                log.info("Day picked from calender: " + pickDate);
                break;
            }
        }
    }

    private static void pickYearFromCalendar(WebDriver driver, String year) throws InterruptedException {
        // Get the date dropdown from the calender popup
        element = driver.findElement(By.xpath("//div[contains(@class,'year-selected')]"));

        clickWhenReady(driver, element, 3000);
        Thread.sleep(2000);

        // select the year you want
        WebElement yearOption = driver.findElement(
                By.xpath("//ul[contains(@class,'calendar-dropdown')]//li[text()='"+year+"']"));
        clickWhenReady(driver, yearOption, 2000);
        Thread.sleep(2000);

        log.info("Year picked from calender: " + year);
    }

    public static void enterEmployeeName(WebDriver driver, String employeeName) throws InterruptedException {
        element = employeeNameField(driver);
        element.sendKeys(Keys.PAGE_UP);
        element.clear();
        element.sendKeys(employeeName);
        log.info("Employee name typed as: " + employeeName);
        Thread.sleep(2000);

        // Get the Suggestion ul element
        WebElement ulElement = element.findElement(By.xpath("//div[contains(@class,'autocomplete-dropdown')]"));
        log.info("Searching for employee name : "+employeeName);
        Thread.sleep(2000);

        // Get the list of all the li elements under the employee name element
        List<WebElement> liElements = ulElement.findElements(By.xpath("//div[@class='oxd-autocomplete-option']/span"));
        log.info(liElements.size());

        for (WebElement searchElement : liElements) {
            if (searchElement.getText().toLowerCase().contains(employeeName.toLowerCase())) {
                log.info("Selected employee name : " + searchElement.getText());
                searchElement.click();
                break;
            }
        }
    }
    public static void enterLeaveStatus(WebDriver driver, String leaveStatus)  {
        element = leaveStatusField(driver);
        selectDropdownOption(driver, leaveStatus);
        log.info("Leave status entered as: " + leaveStatus);
    }
    public static void enterLeaveType(WebDriver driver, String leaveType) {
        element = leaveTypeField(driver);
        selectDropdownOption(driver, leaveType);
        log.info("Leave type entered as: " + leaveType);
    }

    public static void enterSubunitField(WebDriver driver, String subUnitField) {
        element = subUnitField(driver);
        selectDropdownOption(driver, subUnitField);
        log.info("Subunit entered as: " + subUnitField);
    }
    private static void selectDropdownOption(WebDriver driver, String subOption) {
        Actions action = new Actions(driver);
        action.moveToElement(element).click().perform();

        // click on the user role you want from the dropdown
        WebElement customOptionValue = driver.findElement(
                By.xpath("//span[contains(text(),'"+ subOption +"')]"));
        customOptionValue.click();
    }
    public static void togglePastEmployeeSwitch(WebDriver driver ) {
        element = pastEmployeeSwitchField(driver);
        WebElement switchElement = driver.findElement(
                By.xpath("//span[contains(@class,'switch-input')]"));
        switchElement.click();
        log.info("past employee checkbox selected: " + element.isSelected());
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
    public static boolean checkFromDateIsReset(WebDriver driver) {
        element = fromDateField(driver);

        if (element != null && element.getText().isEmpty()) {
            log.info("From date field has empty value");
            return true;
        }
        return false;
    }

    public static boolean checkToDateIsReset(WebDriver driver) {
        element = toDateField(driver);

        if (element != null && element.getText().isBlank()) {
            log.info("To date field has empty value");
            return true;
        }
        return false;
    }
    public static boolean leaveStatusIsEmpty(WebDriver driver) {
        element = leaveStatusField(driver);
        List<WebElement> statusWebElements = driver.findElements(By.xpath("//div[contains(@class,'multiselect')]/span"));
        log.info("leave status has default value of only pending status: " + statusWebElements.size());
        if(statusWebElements.size() == 1)
            return true;
        else
            return false;
    }
    public static boolean leaveTypeIsEmpty(WebDriver driver) {
        element = leaveTypeField(driver);
        return checkDropdownEmpty();
    }
    public static boolean subUnitIsEmpty(WebDriver driver) {
        element = subUnitField(driver);
        return checkDropdownEmpty();
    }
    private static boolean checkDropdownEmpty() {
        if (element != null && element.getText().contains("Select")) {
            log.info("Dropdown has value: " + element.getText());
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

    public static boolean checkTogglePastEmployeeIsOff(WebDriver driver ) {
        element = pastEmployeeSwitchField(driver);
        log.info(" checking past employee checkbox is not selected: " + !element.isSelected());
        return !element.isSelected();
    }
    public static boolean checkAllSearchFieldsEmpty(WebDriver driver) {
        boolean first = OrangeHRMLeavePage.checkFromDateIsReset(driver);
        boolean second = OrangeHRMLeavePage.checkToDateIsReset(driver);
        boolean third = OrangeHRMLeavePage.leaveStatusIsEmpty(driver);
        boolean fourth = OrangeHRMLeavePage.leaveTypeIsEmpty(driver);
        boolean fifth = OrangeHRMLeavePage.checkEmployeeNameIsEmpty(driver);
        boolean sixth = OrangeHRMLeavePage.subUnitIsEmpty(driver);
        boolean seventh = OrangeHRMLeavePage.checkTogglePastEmployeeIsOff(driver);

        return first && second && third && fourth && fifth && sixth && seventh;
    }

    public static int checkSearchResultRows(WebDriver driver, String searchValue, int searchCellIndex) {

        List<WebElement> rowWebElements = getRowWebElements(driver);

        int searchResultRows = 0;
        for (WebElement rowWebElement : rowWebElements) {
            // Find all the rows under the table
            List<WebElement> cellWebElements = rowWebElement.findElements(By.xpath("//div[contains(@class,'table-cell')]/div"));

            if (cellWebElements.get(searchCellIndex).getText().toLowerCase().contains(searchValue.toLowerCase()))
            {
                // this search result matches the search criteria
                searchResultRows++;
                log.info("Search result row matches search criteria: " + searchValue);
            }
        }
        return searchResultRows;
    }

    private static List<WebElement> getRowWebElements(WebDriver driver) {
        // findElements will return a list of table elements
        List<WebElement> rowWebElements = driver.findElements(By.xpath("//div[contains(@class,'table-body')]//div[contains(@class,'table-row')]"));
        log.info("Number of search records found: " + rowWebElements.size());
        return rowWebElements;
    }
}
