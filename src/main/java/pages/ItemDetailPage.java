package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.Logs;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ItemDetailPage extends BasePage {

    private final Locator itemName;
    private final Locator itemPrice;

    public ItemDetailPage(Page page) {
        super(page);
        this.itemName = page.locator(".inventory_details_name");
        this.itemPrice = page.locator(".inventory_details_price");
    }

    @Step("Validating visibility of item details")
    public void validateVisibleElements() {
        Logs.info("Validating visibility of item name");
        assertThat(itemName).isVisible();

        Logs.info("Validating visibility of item price");
        assertThat(itemPrice).isVisible();
    }

    @Step("Validating item name is: {itemName}")
    public void validateItemName(String itemName) {
        Logs.info("Checking item name: " + itemName);
        assertThat(this.itemName).hasText(itemName);
    }
}
