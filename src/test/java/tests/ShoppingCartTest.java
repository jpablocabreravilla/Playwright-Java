package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ShoppingCartPage;
import pages.ShoppingPage;
import pages.TopBarPage;
import utilities.BaseTest;
import utilities.CommonFlows;
import utilities.Logs;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartTest extends BaseTest {

    private ShoppingPage shoppingPage;
    private TopBarPage topBarPage;

    @BeforeEach
    public void setUp() {
        Logs.info("Logging in as a standard user");
        new CommonFlows(page).loginAsStandardUser();

        Logs.info("Initializing required page objects");
        shoppingPage = new ShoppingPage(page);
        topBarPage = new TopBarPage(page);
    }

    @Test
    @Tag("regression")
    public void shouldRemoveItemFromCartSuccessfully() {
        Logs.info("Test: shouldRemoveItemFromCartSuccessfully");

        List<String> itemsToAdd = new ArrayList<>(List.of(
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light"
        ));

        itemsToAdd.forEach(item -> {
            Logs.info("Adding item to cart: " + item);
            shoppingPage.clickAddToCartButton(item);
        });

        String expectedCount = String.valueOf(itemsToAdd.size());
        topBarPage.verifyItemCounter(expectedCount);
        topBarPage.clickShoppingCart();

        ShoppingCartPage cartPage = new ShoppingCartPage(page);
        cartPage.validateItemNames(itemsToAdd);

        String itemToRemove = "Sauce Labs Bike Light";
        Logs.info("Removing item from cart: " + itemToRemove);
        cartPage.removeItem(itemToRemove);
        itemsToAdd.remove(itemToRemove);
        cartPage.validateItemNames(itemsToAdd);

        Logs.info("Test completed: shouldRemoveItemFromCartSuccessfully");
    }

    @Test
    @Tag("regression")
    public void shouldDisplayCorrectItemsInCart() {
        Logs.info("Test: shouldDisplayCorrectItemsInCart");

        List<String> itemsToAdd = List.of(
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light"
        );

        itemsToAdd.forEach(item -> {
            Logs.info("Adding item to cart: " + item);
            shoppingPage.clickAddToCartButton(item);
        });

        String expectedCount = String.valueOf(itemsToAdd.size());
        topBarPage.verifyItemCounter(expectedCount);
        topBarPage.clickShoppingCart();

        ShoppingCartPage cartPage = new ShoppingCartPage(page);
        cartPage.validateItemNames(itemsToAdd);
        cartPage.clickCheckout();

        Logs.info("Test completed: shouldDisplayCorrectItemsInCart");
    }
}
