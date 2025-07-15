package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ShoppingPage;
import pages.TopBarPage;
import utilities.BaseTest;
import utilities.CommonFlows;
import utilities.Logs;
import utilities.TestLogger;

public class ShoppingTests extends BaseTest {

    private ShoppingPage shoppingPage;
    private TopBarPage topBar;

    @BeforeEach
    public void loginAndOpenShoppingPage() {
        Logs.info("Logging in as standard user");
        new CommonFlows(page).loginAsStandardUser();

        shoppingPage = new ShoppingPage(page);
        topBar = new TopBarPage(page);
    }

    @Test
    @Tag("regression")
    public void shouldDisplayShoppingPage() {
        TestLogger.start("shouldDisplayShoppingPage");
        shoppingPage.verifyPageIsDisplayed();
        TestLogger.pass("shouldDisplayShoppingPage");
    }

    @Test
    @Tag("regression")
    public void shouldAddAndRemoveItemSuccessfully() {
        TestLogger.start("shouldAddAndRemoveItemSuccessfully");
        shoppingPage.verifyAddRemoveButtonText("Sauce Labs Fleece Jacket");
        TestLogger.pass("shouldAddAndRemoveItemSuccessfully");
    }

    @Test
    @Tag("regression")
    public void shouldVerifyFirstItemPrice() {
        TestLogger.start("shouldVerifyFirstItemPrice");
        shoppingPage.verifyItemPrice(0, "$29.99");
        TestLogger.pass("shouldVerifyFirstItemPrice");
    }

    @Test
    @Tag("regression")
    public void shouldAddAllItemsToCartAndVerifyCounter() {
        TestLogger.start("shouldAddAllItemsToCartAndVerifyCounter");
        shoppingPage.clickAllAddToCartButtons();
        topBar.verifyItemCounter("6");
        TestLogger.pass("shouldAddAllItemsToCartAndVerifyCounter");
    }

    @Test
    @Tag("regression")
    public void shouldSortItemsByPriceHighToLow() {
        TestLogger.start("shouldSortItemsByPriceHighToLow");
        shoppingPage.sortItems("hilo");
        shoppingPage.verifyFirstAndLastItemPrices("$49.99", "$7.99");
        TestLogger.pass("shouldSortItemsByPriceHighToLow");
    }

    @Test
    @Tag("regression")
    public void shouldSortItemsByPriceLowToHigh() {
        TestLogger.start("shouldSortItemsByPriceLowToHigh");
        shoppingPage.sortItems("lohi");
        shoppingPage.verifyFirstAndLastItemPrices("$7.99", "$49.99");
        TestLogger.pass("shouldSortItemsByPriceLowToHigh");
    }

    @Test
    @Tag("regression")
    public void shouldSortItemsByNameDescending() {
        TestLogger.start("shouldSortItemsByNameDescending");
        shoppingPage.sortItems("za");
        shoppingPage.verifyFirstAndLastItemNames(
                "Test.allTheThings() T-Shirt (Red)",
                "Sauce Labs Backpack"
        );
        TestLogger.pass("shouldSortItemsByNameDescending");
    }
}
