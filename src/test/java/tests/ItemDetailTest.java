package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.ItemDetailPage;
import pages.ShoppingPage;
import utilities.BaseTest;
import utilities.CommonFlows;
import utilities.Logs;
import utilities.TestLogger;

public class ItemDetailTest extends BaseTest {

    private ShoppingPage shoppingPage;

    @BeforeEach
    public void loginAndPreparePage() {
        Logs.info("Logging in as standard user");
        new CommonFlows(page).loginAsStandardUser();

        Logs.info("Opening Shopping Page");
        shoppingPage = new ShoppingPage(page);
    }

    @ParameterizedTest
    @Tag("regression")
    @ValueSource(strings = {
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket",
            "Sauce Labs Onesie",
            "Test.allTheThings() T-Shirt (Red)"
    })
    public void shouldDisplayCorrectItemDetails(String itemName) {
        TestLogger.start("shouldDisplayCorrectItemDetails - " + itemName);

        Logs.info("Clicking on item: " + itemName);
        shoppingPage.clickItemTitle(itemName);

        ItemDetailPage itemDetailPage = new ItemDetailPage(page);

        Logs.info("Validating item detail components are visible");
        itemDetailPage.validateVisibleElements();

        Logs.info("Validating item name is: " + itemName);
        itemDetailPage.validateItemName(itemName);

        TestLogger.pass("shouldDisplayCorrectItemDetails - " + itemName);
    }
}
