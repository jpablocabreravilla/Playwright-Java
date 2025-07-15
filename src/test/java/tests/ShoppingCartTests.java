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
import utilities.TestLogger;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartTests extends BaseTest {

    private ShoppingPage shoppingPage;
    private TopBarPage topBarPage;

    @BeforeEach
    public void loginAndInitializePages() {
        Logs.info("Logging in as standard user");
        new CommonFlows(page).loginAsStandardUser();

        shoppingPage = new ShoppingPage(page);
        topBarPage = new TopBarPage(page);
    }

    @Test
    @Tag("regression")
    public void shouldRemoveItemFromCartSuccessfully() {
        TestLogger.start("shouldRemoveItemFromCartSuccessfully");

        List<String> itemsToAdd = new ArrayList<>(List.of(
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light"
        ));

        Logs.info("Adding items to cart: " + itemsToAdd);
        itemsToAdd.forEach(shoppingPage::clickAddToCartButton);

        String expectedCount = String.valueOf(itemsToAdd.size());
        topBarPage.verifyItemCounter(expectedCount);

        topBarPage.clickShoppingCart();
        ShoppingCartPage cartPage = new ShoppingCartPage(page);
        cartPage.validateItemNames(itemsToAdd);

        String itemToRemove = "Sauce Labs Bike Light";
        Logs.info("Removing item: " + itemToRemove);
        cartPage.removeItem(itemToRemove);

        itemsToAdd.remove(itemToRemove);
        cartPage.validateItemNames(itemsToAdd);

        TestLogger.pass("shouldRemoveItemFromCartSuccessfully");
    }

    @Test
    @Tag("regression")
    public void shouldDisplayCorrectItemsInCart() {
        TestLogger.start("shouldDisplayCorrectItemsInCart");

        List<String> itemsToAdd = List.of(
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light"
        );

        Logs.info("Adding items to cart: " + itemsToAdd);
        itemsToAdd.forEach(shoppingPage::clickAddToCartButton);

        String expectedCount = String.valueOf(itemsToAdd.size());
        topBarPage.verifyItemCounter(expectedCount);

        topBarPage.clickShoppingCart();
        ShoppingCartPage cartPage = new ShoppingCartPage(page);
        cartPage.validateItemNames(itemsToAdd);

        cartPage.clickCheckout();

        TestLogger.pass("shouldDisplayCorrectItemsInCart");
    }
}
