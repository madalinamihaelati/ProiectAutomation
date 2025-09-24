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

public class T05_CheckoutFlowTest extends BaseTest {

    @Test
    @DisplayName("Checkout complet: login → add item → cart → step one → overview → finish")
    void checkout_complete_flow() {
        // LOGIN
        LoginPage login = new LoginPage(driver);
        login.login("error_user", "secret_sauce");

        InventoryPage inv = new InventoryPage(driver);
        assertTrue(inv.isLoaded(), "Trebuie să fiu pe pagina Products după login.");
        System.out.println("[CHECKOUT] Login OK cu user=error_user.");

        // ADD TO CART (poți schimba produsul dacă vrei)
        String item = "Sauce Labs Bike Light";
        assertTrue(inv.addItemByName(item), "[CHECKOUT] EȘEC: nu am putut adăuga produsul: " + item);
        assertEquals(1, inv.getCartCount(), "[CHECKOUT] Badge-ul coșului ar trebui să fie 1 după adăugare.");
        System.out.println("[CHECKOUT] Am adăugat în coș: " + item);

        // MERG ÎN COȘ
        inv.openCart();
        CartPage cart = new CartPage(driver);
        assertTrue(cart.containsItem(item), "[CHECKOUT] Produsul nu este în coș.");
        System.out.println("[CHECKOUT] Produsul este prezent în coș.");

        // CHECKOUT STEP ONE
        assertTrue(cart.clickCheckout(), "[CHECKOUT] EȘEC: Nu am intrat pe checkout-step-one.");
        CheckoutStepOnePage step1 = new CheckoutStepOnePage(driver);
        step1.fill("Timm", "QA", "110001");
        step1.clickContinue();
        System.out.println("[CHECKOUT] Datele de livrare completate și am continuat spre overview.");

        // OVERVIEW
        CheckoutOverviewPage overview = new CheckoutOverviewPage(driver);
        assertTrue(overview.finish(), "[CHECKOUT] EȘEC: Butonul Finish nu m-a dus pe pagina de complete.");
        System.out.println("[CHECKOUT] Am apăsat Finish pe overview.");

        // COMPLETE
        CheckoutCompletePage complete = new CheckoutCompletePage(driver);
        assertTrue(complete.isThankYouVisible(), "[CHECKOUT] EȘEC: Mesajul 'Thank you' nu este vizibil.");
        System.out.println("[CHECKOUT] SUCCES: Checkout complet — mesajul 'Thank you' este vizibil.");
    }
}
