package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Selectors ---
    private final By title      = By.cssSelector(".title"); // text: "Products"
    private final By cartBadge  = By.cssSelector(".shopping_cart_badge");
    private final By cartLink   = By.cssSelector("a.shopping_cart_link");
    private final By itemCards  = By.cssSelector(".inventory_item");
    private final By itemName   = By.cssSelector(".inventory_item_name");
    private final By itemBtn    = By.cssSelector("button.btn"); // Add to cart / Remove

    // --- Constructor ---
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Actions / Validations ---

    /** Confirmă că pagina Products este încărcată. */
    public boolean isLoaded() {
        try {
            return wait.until(ExpectedConditions.textToBe(title, "Products"));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Adaugă în coș produsul după nume (ex: "Sauce Labs Backpack"). */
    public boolean addItemByName(String productName) {
        try {
            WebElement card = findItemCardByName(productName);
            WebElement addBtn = card.findElement(itemBtn);
            wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Returnează numărul din badge-ul coșului (0 dacă badge-ul nu e vizibil). */
    public int getCartCount() {
        try {
            String txt = driver.findElement(cartBadge).getText().trim();
            return Integer.parseInt(txt);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    /** Deschide pagina coșului. */
    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    /** Returnează numele tuturor produselor vizibile din inventory. */
    public List<String> getVisibleProductNames() {
        List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemCards));
        List<String> names = new ArrayList<>();
        for (WebElement c : cards) {
            names.add(c.findElement(itemName).getText().trim());
        }
        return names;
    }

    /** Apasă „Add to cart” pe toate produsele vizibile (ignoră cele deja „Remove”). */
    public int addAllVisibleItems() {
        List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemCards));
        int clicks = 0;
        for (WebElement c : cards) {
            WebElement btn = c.findElement(itemBtn);
            String label = btn.getText().trim().toLowerCase();
            if (label.contains("add")) {
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                clicks++;
            }
        }
        return clicks;
    }

    // --- Helpers ---
    private WebElement findItemCardByName(String productName) {
        String xpath = "//div[@class='inventory_item' and .//div[@class='inventory_item_name' and normalize-space()='"
                + productName + "']]";
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
}
