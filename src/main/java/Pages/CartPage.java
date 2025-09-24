package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartItem = By.cssSelector(".cart_item");
    private final By title = By.cssSelector(".title"); // "Your Cart"

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ensureOnCart();
    }

    /** Verifică dacă produsul cu numele dat apare în coș. */
    public boolean containsItem(String productName) {
        try {
            findCartItemByName(productName);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Elimină produsul după nume (apasă butonul Remove din acel card). */
    public boolean removeItemByName(String productName) {
        try {
            WebElement item = findCartItemByName(productName);
            WebElement removeBtn = item.findElement(By.cssSelector("button.cart_button"));
            wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
            // confirmă dispariția item-ului
            wait.until(ExpectedConditions.invisibilityOf(item));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Numărul de item-uri vizibile în coș. */
    public int getItemCount() {
        return driver.findElements(cartItem).size();
    }
    public boolean clickCheckout() {
        try {
            By checkoutBtn = By.id("checkout");
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
            return true;
        } catch (Exception e) { return false; }
    }

    // ------- helpers -------
    private void ensureOnCart() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.textToBe(title, "Your Cart"),
                    ExpectedConditions.visibilityOfElementLocated(cartItem)
            ));
        } catch (Exception ignored) {}
    }

    private WebElement findCartItemByName(String productName) {
        String xpath = "//div[contains(@class,'cart_item')][.//div[@class='inventory_item_name' and normalize-space()='" + productName + "']]";
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
}
