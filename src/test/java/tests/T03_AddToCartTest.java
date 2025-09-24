package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Pages.LoginPage;
import Pages.InventoryPage;
import Pages.CartPage;

public class T03_AddToCartTest extends BaseTest {

    @Test
    @DisplayName("Add to cart: adaug un produs și îl regăsesc în coș")
    void add_to_cart_and_verify_in_cart() {
        new LoginPage(driver).login("error_user", "secret_sauce");

        InventoryPage inv = new InventoryPage(driver);
        assertTrue(inv.isLoaded(), "Trebuie să fiu pe Products după login.");
        String item = "Sauce Labs Backpack";

        boolean added = inv.addItemByName(item);
        int badge = inv.getCartCount();

        if (added && badge == 1) {
            System.out.println("[TEST Add] SUCCES: Am adăugat '" + item + "' (badge=" + badge + ").");
        } else {
            System.out.println("[TEST Add] EȘEC: Nu am putut adăuga '" + item + "' (badge=" + badge + ").");
        }
        assertTrue(added, "Nu am putut apăsa Add to cart pentru: " + item);
        assertEquals(1, badge, "Badge-ul ar trebui să arate 1 după adăugare.");

        inv.openCart();
        CartPage cart = new CartPage(driver);
        assertTrue(cart.containsItem(item), "Produsul nu se regăsește în coș.");
    }
}
