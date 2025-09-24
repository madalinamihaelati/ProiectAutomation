package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class Waits {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Waits(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> visibles(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // >>> Metodele care îți lipsesc <<<

    public boolean urlContains(String part, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.urlContains(part));
            return true;
        } catch (TimeoutException e) { return false; }
    }

    public boolean textEquals(By locator, String txt, int seconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.textToBe(locator, txt));
        } catch (TimeoutException e) { return false; }
    }

    public boolean isAlertPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) { return false; }
    }

    public void acceptAlertIfPresent() {
        if (isAlertPresent()) driver.switchTo().alert().accept();
    }
}
