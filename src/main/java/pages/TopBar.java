package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.qameta.allure.Step;
import utilities.Logs;

public class TopBar extends BasePage {

    private final Locator itemCounter;

    public TopBar(Page page) {
        super(page);
        itemCounter = page.getByTestId("shopping-cart-badge");
    }

    @Step("Validating items counter")
    public void verifyItemCounter(String expectedCount) {
        Logs.info("Validating items counter");
        PlaywrightAssertions.assertThat(itemCounter).hasText(expectedCount);
    }

}
