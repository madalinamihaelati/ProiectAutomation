package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class CheckoutOverviewPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector(".title"); // "Checkout: Overview"
    private final By finishBtn = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ensureLoaded();
    }

    private void ensureLoaded() {
        try { wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBe(title, "Checkout: Overview"),
                ExpectedConditions.elementToBeClickable(finishBtn)
        )); } catch (TimeoutException ignored) {}
    }

    /** Apasă Finish și întoarce true dacă a reușit navigarea spre pagina de complete. */
    public boolean finish() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
