package tests;

import net.datafaker.Faker;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;
import utilities.BaseTest;
import utilities.CommonFlows;
import utilities.Logs;
import utilities.TestLogger;

import java.util.List;
import java.util.Map;

public class E2ETest extends BaseTest {

    @Test
    @Tag("E2E")
    public void EndToEndTest() {

        System.out.println("[DEBUG] HEADLESS: " + System.getProperty("headless"));

        TestLogger.start("shouldDisplayCorrectItemsInCart");

        // --- Login phase ---
        Logs.info("Logging in as standard user");
        new CommonFlows(page).loginAsStandardUser();

        // --- Page object instantiations ---
        ShoppingPage shoppingPage = new ShoppingPage(page);
        TopBarPage topBarPage = new TopBarPage(page);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(page);
        CheckoutYourInformationPage checkoutPage = new CheckoutYourInformationPage(page);
        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(page);
        CheckoutComplete checkoutComplete = new CheckoutComplete(page);

        // --- Prepare items to add ---
        List<String> itemsToAdd = List.of(
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Backpack",
                "Sauce Labs Bolt T-Shirt",
                "Test.allTheThings() T-Shirt (Red)"
        );

        Logs.info("Adding items to cart: " + itemsToAdd);
        itemsToAdd.forEach(shoppingPage::clickAddToCartButton);

        // --- Verify cart counter ---
        String expectedCount = String.valueOf(itemsToAdd.size());
        topBarPage.verifyItemCounter(expectedCount);

        // --- Go to cart and verify contents ---
        topBarPage.clickShoppingCart();
        shoppingCartPage.validateItemNames(itemsToAdd);

        // --- Proceed to checkout ---
        shoppingCartPage.clickCheckout();

        // --- Generate fake user info ---
        Faker faker = new Faker();
        Map<String, String> userData = Map.of(
                "firstName", faker.name().firstName(),
                "lastName", faker.name().lastName(),
                "zipCode", faker.address().zipCode()
        );
        Logs.info("Generated user data: " + userData);

        // --- Fill checkout form ---
        checkoutPage.fillInformation(userData);

        // --- Final validations ---
        shoppingCartPage.validateItemNames(itemsToAdd);
        checkoutOverviewPage.verifyItemPrices();
        checkoutOverviewPage.clickBtnFinish();

        // --- Checkout complete ---
        checkoutComplete.verifyCheckoutComplete();
        checkoutComplete.selectBackHomeBtn();

        TestLogger.pass("shouldDisplayCorrectItemsInCart");
    }
}
