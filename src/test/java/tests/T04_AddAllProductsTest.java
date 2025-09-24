package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import Pages.LoginPage;
import Pages.InventoryPage;
import Pages.CartPage;

public class T04_AddAllProductsTest extends BaseTest {

    @Test
    @DisplayName("Add to cart pentru toate produsele (6 items)")
    void add_all_products_and_verify_cart() {
        // Login
        new LoginPage(driver).login("error_user", "secret_sauce");

        // Inventory
        InventoryPage inv = new InventoryPage(driver);
        assertTrue(inv.isLoaded(), "Trebuie să fiu pe 'Products' după login.");

        List<String> productNames = inv.getVisibleProductNames();
        int total = productNames.size();
        assertEquals(6, total, "Ar trebui să fie 6 produse în inventory.");

        int clicked = inv.addAllVisibleItems();
        int badge = inv.getCartCount();

        if (clicked == total && badge == total) {
            System.out.println("[TEST 4] SUCCES: Am adăugat toate cele " + total + " produse (badge=" + badge + ").");
        } else {
            System.out.println("[TEST 4] EȘEC: add clicks=" + clicked + " / total=" + total + ", badge=" + badge);
        }
        assertEquals(total, clicked, "Nu s-au apăsat toate butoanele 'Add to cart'.");
        assertEquals(total, badge, "Badge-ul coșului nu arată numărul corect de produse.");

        // Cart
        inv.openCart();
        CartPage cart = new CartPage(driver);
        int inCart = cart.getItemCount();
        assertEquals(total, inCart, "În coș nu sunt toate produsele.");

        // (opțional) verifică fiecare nume în coș
        for (String name : productNames) {
            assertTrue(cart.containsItem(name), "Lipsește din coș produsul: " + name);
        }

        System.out.println("[TEST 4] Verificare coș OK: " + inCart + " / " + total + " produse prezente.");
    }
}
