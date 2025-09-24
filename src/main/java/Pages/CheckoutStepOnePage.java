package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class CheckoutStepOnePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector(".title"); // "Checkout: Your Information"
    private final By firstName = By.id("first-name");
    private final By lastName  = By.id("last-name");
    private final By postal    = By.id("postal-code");
    private final By continueBtn = By.id("continue");
    private final By errorBox  = By.cssSelector("h3[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ensureLoaded();
    }

    private void ensureLoaded() {
        try { wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBe(title, "Checkout: Your Information"),
                ExpectedConditions.visibilityOfElementLocated(firstName)
        )); } catch (TimeoutException ignored) {}
    }

    public void fill(String fn, String ln, String zip) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).clear();
        driver.findElement(firstName).sendKeys(fn == null ? "" : fn);

        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(ln == null ? "" : ln);

        driver.findElement(postal).clear();
        driver.findElement(postal).sendKeys(zip == null ? "" : zip);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }

    public String getError() {
        try { return driver.findElement(errorBox).getText().trim(); }
        catch (NoSuchElementException e) { return ""; }
    }
}
