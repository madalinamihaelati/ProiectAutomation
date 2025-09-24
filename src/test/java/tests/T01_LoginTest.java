package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Pages.LoginPage;
import Pages.InventoryPage;

public class T01_LoginTest extends BaseTest {

    @Test
    @DisplayName("Login reușit cu userul error_user")
    void login_ok() {
        LoginPage login = new LoginPage(driver);
        login.login("error_user", "secret_sauce");

        InventoryPage inv = new InventoryPage(driver);
        boolean success = inv.isLoaded() && !login.isErrorVisible();

        if (success) {
            System.out.println("[TEST 1] SUCCES: Testul a reușit – credențiale valide (user=error_user).");
        } else {
            System.out.println("[TEST 1] EȘEC: Testul a eșuat – credențiale invalide sau altă problemă. "
                    + "Mesaj eroare: " + login.getErrorText());
        }

        assertTrue(success, "După login ar trebui să se încarce pagina de inventar (Products), fără mesaj de eroare.");
    }

    @Test
    @DisplayName("Login eșuat cu parolă greșită")
    void login_invalid_password_shows_error() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "wrong_pass");

        boolean hasError = login.isErrorVisible();
        if (hasError) {
            System.out.println("[TEST 1-negativ] SUCCES: Testul a reușit – mesaj de eroare afișat pentru credențiale invalide: "
                    + login.getErrorText());
        } else {
            System.out.println("[TEST 1-negativ] EȘEC: Nu a apărut mesajul de eroare, dar ne așteptam (parolă greșită).");
        }

        assertTrue(hasError, "Ar trebui să apară mesajul de eroare la credențiale invalide.");
    }
}

