package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.Logs;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartPage extends BasePage {

    private final Locator cartItems;
    private final Locator checkoutButton;
    private final Locator finishBtn;

    public ShoppingCartPage(Page page) {
        super(page);
        cartItems = page.locator(".cart_item");
        checkoutButton = page.locator("[name='checkout']");
        finishBtn = page.locator("[name='finish']");
    }

    @Step("Getting all item names in the cart")
    public List<String> getItemNames() {
        Logs.info("Extracting item names from each cart item...");

        int itemCount = cartItems.count();
        Logs.info("Total items found in cart: " + itemCount);

        List<String> names = new ArrayList<>();

        for (int i = 0; i < itemCount; i++) {
            Locator item = cartItems.nth(i);
            String name = item.locator(".inventory_item_name").innerText().trim();
            Logs.info("Item " + (i + 1) + ": " + name);
            names.add(name);
        }

        Logs.info("All extracted item names: " + names);
        return names;
    }

    @Step("Removing item from cart: {itemName}")
    public void removeItem(String itemName) {
        Logs.info("Attempting to remove item: " + itemName);

        Locator item = cartItems
                .filter(new Locator.FilterOptions().setHasText(itemName));
        Locator removeBtn = item.locator("button:has-text('Remove')");

        Logs.info("Clicking 'Remove' button for item: " + itemName);
        removeBtn.click();
    }

    @Step("Clicking on the 'Checkout' button")
    public void clickCheckout() {
        Logs.info("Clicking on 'Checkout' button");
        checkoutButton.click();
    }

    @Step("Validating presence of expected items in cart")
    public void validateItemNames(List<String> expectedItemNames) {
        Logs.info("Starting validation of expected item names...");
        Logs.info("Expected items: " + expectedItemNames);

        List<String> actualItemNames = getItemNames();
        Logs.info("Actual items in cart: " + actualItemNames);

        for (String expected : expectedItemNames) {
            boolean found = actualItemNames.contains(expected);
            Logs.info("Checking item: " + expected + " - Found: " + found);
            assertTrue(found, "Item NOT found in cart: " + expected);
        }
    }
}
