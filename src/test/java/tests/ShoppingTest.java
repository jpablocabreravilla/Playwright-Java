package tests;

import utilities.CommonFlows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ShoppingPage;
import pages.TopBarPage;
import utilities.BaseTest;

public class ShoppingTest extends BaseTest {

    private ShoppingPage shoppingPage;
    private TopBarPage topBar;

    @BeforeEach
    public void setUp() {
        new CommonFlows(page).loginAsStandardUser();
        shoppingPage = new ShoppingPage(page);
        topBar = new TopBarPage(page);
    }

    @Test
    @Tag("regression")
    public void shouldVerifyShoppingPageIsVisible() {
        shoppingPage.verifyPageIsDisplayed();
    }

    @Test
    @Tag("regression")
    public void shouldAddAndRemoveAnItem() {
        shoppingPage.verifyAddRemoveButtonText("Sauce Labs Fleece Jacket");
    }

    @Test
    @Tag("regression")
    public void shouldVerifyFirstItemPrice() {
        shoppingPage.verifyItemPrice(0, "$29.99");
    }

    @Test
    @Tag("regression")
    public void shouldAddItemsToCartAndVerifyCounter() {
        shoppingPage.clickAllAddToCartButtons();
        topBar.verifyItemCounter("6");
    }

    @Test
    @Tag("regression")
    public void shouldSortItemsByPriceHighToLow() {
        shoppingPage.sortItems("hilo");
        shoppingPage.verifyFirstAndLastItemPrices("$49.99", "$7.99");
    }

    @Test
    @Tag("regression")
    public void shouldSortItemsByPriceZtoA() {
        shoppingPage.sortItems("za");
        shoppingPage.verifyFirstAndLastItemPrices("$15.99", "$29.99");
    }

    @Test
    @Tag("regression")
    public void shouldSortItemsByNameZToA() {
        shoppingPage.sortItems("za");
        shoppingPage.verifyFirstAndLastItemNames("Test.allTheThings() T-Shirt (Red)", "Sauce Labs Backpack");
    }
}
