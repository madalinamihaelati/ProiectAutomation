package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locatori stabili (folosesc id / data-test native din pagină)
    private final By userInput   = By.id("user-name");                 // sau By.cssSelector("[data-test='username']")
    private final By passInput   = By.id("password");                  // sau By.cssSelector("[data-test='password']")
    private final By loginButton = By.id("login-button");              // sau By.cssSelector("[data-test='login-button']")
    private final By errorBox    = By.cssSelector("h3[data-test='error']");
    private final By inventoryTitle = By.cssSelector(".title");        // textul devine "Products" după login OK

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // implicitWait = 0 în DriverFactory
        ensureOnLoginPage();
    }

    /** Verifică că suntem pe pagina de login (username vizibil). */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userInput));
            wait.until(ExpectedConditions.visibilityOfElementLocated(passInput));
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Completează user + pass și apasă Login. BaseTest deja a deschis URL-ul. */
    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userInput)).clear();
        driver.findElement(userInput).sendKeys(username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(passInput)).clear();
        WebElement pass = driver.findElement(passInput);
        pass.sendKeys(password);

        // ENTER e stabil; dacă vrei click pe buton, folosește clickLogin()
        pass.sendKeys(Keys.ENTER);
    }

    /** Variante separate dacă preferi butonul. */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    /** Așteaptă până la succes (Products vizibil) sau eroare; întoarce true dacă succes. */
    public boolean waitForResultSuccessOrError() {
        try {
            wait.until(d -> isErrorVisible() || isInventoryLoaded());
            return isInventoryLoaded() && !isErrorVisible();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Este vizibil mesajul de eroare? */
    public boolean isErrorVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorBox));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Textul complet al erorii (gol dacă nu e prezentă). */
    public String getErrorText() {
        try {
            return driver.findElement(errorBox).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // ---------- Helpers private ----------

    private void ensureOnLoginPage() {
        // dacă nu se vede user-input, încearcă să mergi la login (util când vii din test rulat după altul)
        if (!isElementPresent(userInput)) {
            driver.get("https://www.saucedemo.com/");
            isLoaded(); // sincronizare scurtă
        }
    }

    private boolean isInventoryLoaded() {
        try {
            return wait.until(ExpectedConditions.textToBe(inventoryTitle, "Products"));
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }
}
