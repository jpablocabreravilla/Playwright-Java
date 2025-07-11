package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.qameta.allure.Step;
import utilities.Logs;

public class TopBarPage extends BasePage {

    private final Locator itemCounterBadge;
    private final Locator shoppingCartIcon;

    public TopBarPage(Page page) {
        super(page);
        this.itemCounterBadge = page.getByTestId("shopping-cart-badge");
        this.shoppingCartIcon = page.locator("#shopping_cart_container");
    }

    @Step("Validating item counter shows: {expectedCount}")
    public void verifyItemCounter(String expectedCount) {
        Logs.info("Verifying shopping cart badge shows: " + expectedCount);
        PlaywrightAssertions.assertThat(itemCounterBadge).hasText(expectedCount);
    }

    @Step("Clicking on shopping cart icon")
    public void clickShoppingCart() {
        Logs.info("Clicking shopping cart icon");
        shoppingCartIcon.click();
    }
}
