package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Pages.LoginPage;
import Pages.InventoryPage;
import Pages.CartPage;
import Pages.CheckoutStepOnePage;
import Pages.CheckoutOverviewPage;
import Pages.CheckoutCompletePage;

public class T06_CheckoutBikeLightTest extends BaseTest {

    @Test
    @DisplayName("Checkout: Sauce Labs Bike Light (error_user)")
    void checkout_bike_light() {
        // LOGIN
        LoginPage login = new LoginPage(driver);
        login.login("error_user", "secret_sauce");

        InventoryPage inv = new InventoryPage(driver);
        assertTrue(inv.isLoaded(), "Trebuie să fiu pe pagina Products după login.");
        System.out.println("[T06] Login OK (error_user).");

        // ADD TO CART
        String item = "Sauce Labs Bike Light";
        assertTrue(inv.addItemByName(item), "[T06] EȘEC: nu am putut adăuga produsul: " + item);
        assertEquals(1, inv.getCartCount(), "[T06] Badge-ul trebuie să fie 1 după adăugare.");
        System.out.println("[T06] Am adăugat în coș: " + item);

        // CART
        inv.openCart();
        CartPage cart = new CartPage(driver);
        assertTrue(cart.containsItem(item), "[T06] Produsul nu este în coș.");
        System.out.println("[T06] Produsul este prezent în coș.");

        // CHECKOUT STEP ONE (completează cu date simple)
        assertTrue(cart.clickCheckout(), "[T06] EȘEC: Nu am intrat pe checkout-step-one.");
        CheckoutStepOnePage step1 = new CheckoutStepOnePage(driver);
        step1.fill("Madalina", "Ilie", "112233");
        step1.clickContinue();
        System.out.println("[T06] Date completate: prenume=Madalina, nume=Ilie, cod=112233.");

        // OVERVIEW → FINISH
        CheckoutOverviewPage overview = new CheckoutOverviewPage(driver);
        assertTrue(overview.finish(), "[T06] EȘEC: Butonul Finish nu a funcționat.");
        System.out.println("[T06] Am apăsat Finish.");

        // COMPLETE
        CheckoutCompletePage complete = new CheckoutCompletePage(driver);
        boolean thankYou = complete.isThankYouVisible();
        if (thankYou) {
            System.out.println("[T06] SUCCES: Checkout finalizat — mesajul 'Thank you' este vizibil.");
        } else {
            System.out.println("[T06] EȘEC: Mesajul 'Thank you' nu este vizibil.");
        }
        assertTrue(thankYou, "[T06] Mesajul 'Thank you' ar trebui să fie vizibil la final.");
    }
}
