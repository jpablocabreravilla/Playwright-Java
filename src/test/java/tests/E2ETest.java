package tests;

import net.datafaker.Faker;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;
import utilities.BaseTest;
import utilities.CommonFlows;
import utilities.Logs;

import java.util.List;
import java.util.Map;

public class E2ETest extends BaseTest {

    @Test
    @Tag("regression")
    public void shouldDisplayCorrectItemsInCart() {
        Logs.info("Test started: shouldDisplayCorrectItemsInCart");

        Logs.info("Logging in as a standard user");
        new CommonFlows(page).loginAsStandardUser();

        Logs.info("Initializing page objects");
        ShoppingPage shoppingPage = new ShoppingPage(page);
        TopBarPage topBarPage = new TopBarPage(page);

        Logs.info("Preparing items to add to cart");
        List<String> itemsToAdd = List.of(
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Backpack",
                "Sauce Labs Bolt T-Shirt",
                "Test.allTheThings() T-Shirt (Red)"
        );

        Logs.info("Adding items to the shopping cart");
        itemsToAdd.forEach(item -> {
            Logs.info("Adding item: " + item);
            shoppingPage.clickAddToCartButton(item);
        });

        String expectedCount = String.valueOf(itemsToAdd.size());
        Logs.info("Verifying item counter equals: " + expectedCount);
        topBarPage.verifyItemCounter(expectedCount);

        Logs.info("Navigating to shopping cart");
        topBarPage.clickShoppingCart();

        Logs.info("Validating items in the cart");
        ShoppingCartPage cartPage = new ShoppingCartPage(page);
        cartPage.validateItemNames(itemsToAdd);

        Logs.info("Proceeding to checkout");
        cartPage.clickCheckout();

        Logs.info("Filling in checkout information with generated user data");
        CheckoutYourInformationPage checkoutPage = new CheckoutYourInformationPage(page);
        Faker faker = new Faker();

        Map<String, String> userData = Map.of(
                "firstName", faker.name().firstName(),
                "lastName", faker.name().lastName(),
                "zipCode", faker.address().zipCode()
        );

        userData.forEach((key, value) -> Logs.info("Generated " + key + ": " + value));
        checkoutPage.fillInformation(userData);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(page);
        shoppingCartPage.clickCheckout();

        Logs.info("Test completed successfully: shouldDisplayCorrectItemsInCart");
    }
}
