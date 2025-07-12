package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.AllureUtils;
import utilities.Logs;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartPage extends BasePage {

    private final Locator cartItems;
    private final Locator checkoutButton;

    public ShoppingCartPage(Page page) {
        super(page);
        this.cartItems = page.locator(".cart_item");
        this.checkoutButton = page.locator("[name='checkout']");
    }

    @Step("Getting all item names from cart")
    public List<String> getItemNames() {
        Logs.info("Extracting item names from cart items...");

        final int itemCount = cartItems.count();
        Logs.info("Total items found: " + itemCount);

        final List<String> names = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            final Locator item = cartItems.nth(i);
            final String name = item.locator(".inventory_item_name").innerText().trim();
            Logs.info("Item " + (i + 1) + ": " + name);
            names.add(name);
        }

        Logs.info("Final list of item names: " + names);
        return names;
    }

    @Step("Removing item from cart: {itemName}")
    public void removeItem(String itemName) {
        Logs.info("Attempting to remove item: " + itemName);

        final Locator item = cartItems
                .filter(new Locator.FilterOptions().setHasText(itemName));
        final Locator removeButton = item.locator("button:has-text('Remove')");

        Logs.info("Clicking 'Remove' for item: " + itemName);
        removeButton.click();
    }

    @Step("Clicking on 'Checkout' button")
    public void clickCheckout() {
        Logs.info("Clicking 'Checkout' button");
        checkoutButton.click();
    }

    @Step("Validating that the following items are present in cart: {expectedItemNames}")
    public void validateItemNames(List<String> expectedItemNames) {
        Logs.info("Starting validation of item names...");
        Logs.info("Expected items: " + expectedItemNames);

        final List<String> actualItemNames = getItemNames();
        Logs.info("Actual items in cart: " + actualItemNames);

        for (String expected : expectedItemNames) {
            final boolean found = actualItemNames.contains(expected);
            Logs.info("Checking if item is present: " + expected + " → " + (found ? "✔" : "✘"));
            assertTrue(found, "Item NOT found in cart: " + expected);
        }

        AllureUtils.attachScreenshot(page);
    }
}
