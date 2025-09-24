package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class CheckoutCompletePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector(".title"); // "Checkout: Complete!"
    private final By thankYou = By.cssSelector(".complete-header"); // "Thank you for your order!"

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isThankYouVisible() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.textToBe(title, "Checkout: Complete!"),
                    ExpectedConditions.visibilityOfElementLocated(thankYou)
            ));
            return driver.findElement(thankYou).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
