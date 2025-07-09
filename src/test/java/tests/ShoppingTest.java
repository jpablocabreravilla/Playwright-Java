package tests;

import utilities.CommonFlows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ShoppingPage;
import pages.TopBar;
import utilities.BaseTest;

public class ShoppingTest extends BaseTest {

    private ShoppingPage shoppingPage;
    private TopBar topBar;

    @BeforeEach
    public void setup() {
        new CommonFlows(page).loginAsStandardUser();
        shoppingPage = new ShoppingPage(page);
        topBar = new TopBar(page);
    }

    @Test
    @Tag("regression")
    public void shouldVerifyShoppingPage() {
        shoppingPage.verifyPage();
    }

    @Test
    @Tag("regression")
    public void shouldAddAndRemoveItem() {
        shoppingPage.verifyAddRemove("Sauce Labs Fleece Jacket");
    }

    @Test
    @Tag("regression")
    public void shouldVerifyItemPrice() {
        shoppingPage.verifyItemPrice(0, "$29.99");
    }

    @Test
    @Tag("regression")
    public void shouldAddToCartAndVerifyCounter() {
        shoppingPage.clickAddToCartButton();
        topBar.verifyItemCounter("6");
    }

    @Test
    @Tag("regression")
    public void shouldSortByPriceHighToLow() {
        shoppingPage.orderItems("hilo");
        shoppingPage.verifyItemPrices("$49.99", "$7.99");
    }

    @Test
    @Tag("regression")
    public void shouldSortByNameZtoA() {
        shoppingPage.orderItems("za");
        shoppingPage.verifyItemNames("Test.allTheThings() T-Shirt (Red)", "Sauce Labs Backpack");
    }
}
