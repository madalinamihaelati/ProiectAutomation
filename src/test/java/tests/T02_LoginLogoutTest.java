package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Pages.LoginPage;
import Pages.InventoryPage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class T02_LoginLogoutTest extends BaseTest {

    // locatori standard pentru logout în SauceDemo
    private final By menuButton   = By.id("react-burger-menu-btn");
    private final By logoutLink   = By.id("logout_sidebar_link");
    private final By usernameField = By.id("user-name");

    @Test
    @DisplayName("Login (error_user) + Logout")
    void login_then_logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // LOGIN
        LoginPage login = new LoginPage(driver);
        login.login("error_user", "secret_sauce");

        InventoryPage inv = new InventoryPage(driver);
        boolean loggedIn = inv.isLoaded() && !login.isErrorVisible();

        if (loggedIn) {
            System.out.println("[TEST 2] LOGIN: SUCCES – autentificare reușită cu user=error_user.");
        } else {
            System.out.println("[TEST 2] LOGIN: EȘEC – credențiale invalide sau altă problemă. Eroare: " + login.getErrorText());
        }
        assertTrue(loggedIn, "Trebuia să ajung pe pagina de inventar după login valid.");

        // LOGOUT
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink)).click();

        // verificare revenire pe pagina de login (fără a crea un nou LoginPage care ar putea naviga)
        boolean backToLogin = false;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            backToLogin = true;
        } catch (Exception ignored) {}

        if (backToLogin) {
            System.out.println("[TEST 2] LOGOUT: SUCCES – am revenit pe pagina de login.");
        } else {
            System.out.println("[TEST 2] LOGOUT: EȘEC – nu am revenit pe pagina de login.");
        }
        assertTrue(backToLogin, "După logout ar trebui să văd câmpul de username pe pagina de login.");
    }
}
