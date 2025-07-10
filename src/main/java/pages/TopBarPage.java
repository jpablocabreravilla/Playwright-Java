package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.qameta.allure.Step;
import utilities.Logs;

public class TopBarPage extends BasePage {

    private final Locator itemCounter;
    private final Locator shoppingCart;

    public TopBarPage(Page page) {
        super(page);
        itemCounter = page.getByTestId("shopping-cart-badge");
        shoppingCart = page.locator("#shopping_cart_container");
    }

    @Step("Validating items counter")
    public void verifyItemCounter(String expectedCount) {
        Logs.info("Validating items counter");
        PlaywrightAssertions.assertThat(itemCounter).hasText(expectedCount);
    }
}
