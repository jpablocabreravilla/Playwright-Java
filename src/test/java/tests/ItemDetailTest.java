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

public class ItemDetailTest extends BaseTest {

    private ShoppingPage shoppingPage;

    @BeforeEach
    public void setUp() {
        Logs.info("Logging in as standard user");
        new CommonFlows(page).loginAsStandardUser();

        Logs.info("Initializing required pages");
        shoppingPage = new ShoppingPage(page);
    }

    @Tag("regression")
    @ParameterizedTest
    @ValueSource(strings = {
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket",
            "Sauce Labs Onesie",
            "Test.allTheThings() T-Shirt (Red)"
    })
    public void shouldDisplayCorrectItemDetails(String itemName) {
        Logs.info("Test: Verify selected item details");

        Logs.info("Selecting item: " + itemName);
        shoppingPage.clickItemTitle(itemName);

        Logs.info("Navigating to item detail page");
        ItemDetailPage itemDetailPage = new ItemDetailPage(page);

        Logs.info("Validating visible elements on the item detail page");
        itemDetailPage.validateVisibleElements();

        Logs.info("Validating item name");
        itemDetailPage.validateItemName(itemName);

        Logs.info("Test completed successfully");
    }
}
