package POMPageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    public static void clickWhenReady(WebDriver driver, WebElement element, int timeoutMillis) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMillis));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }


}