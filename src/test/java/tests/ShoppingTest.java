package tests;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ShoppingPage;
import pages.TopBar;
import utilities.BaseTest;

public class ShoppingTest extends BaseTest {

    @BeforeEach
    public void Login() {
        final var loginPage = new LoginPage(page);
        loginPage.fillForm("standard_user", "secret_sauce");
    }

    @Test
    @Tag("regression")
    public void verificarPaginaShoppingTest(Page page) {
        final var shoppingPage = new ShoppingPage(page);
        shoppingPage.verifyPage();
    }

    @Test
    @Tag("regression")
    public void buttonRemoveTest(Page page) {
        final var shoppingPage = new ShoppingPage(page);
        shoppingPage.verifyAddRemove("Sauce Labs Fleece Jacket");
    }

    @Test
    @Tag("regression")
    public void precioTest(Page page) {
        final var shoppingPage = new ShoppingPage(page);
        shoppingPage.verifyItemPrice(2, "$15.99");
    }

    @Test
    @Tag("regression")
    public void addToCartTest(Page page) {
        final var shoppingPage = new ShoppingPage(page);
        shoppingPage.clickAddToCartButton();

        final var topBar = new TopBar(page);
        topBar.verifyItemCounter("6");
    }

    @Test
    @Tag("regression")
    public void ordenarMayorMenorPrecioTest(Page page) {
        final var shoppingPage = new ShoppingPage(page);
        shoppingPage.orderItems("hilo");
        shoppingPage.verifyItemPrices("$49.99", "$7.99");
    }

    @Test
    @Tag("regression")
    public void ordenarMayorMenorNombreTest(Page page) {
        final var shoppingPage = new ShoppingPage(page);
        shoppingPage.orderItems("za");
        shoppingPage.verifyItemNames("Test.allTheThings() T-Shirt (Red)", "Sauce Labs Backpack");
    }
}
