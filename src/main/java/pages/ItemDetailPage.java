package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.Logs;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ItemDetailPage extends BasePage {

    private final Locator itemName;
    private final Locator itemPrice;
    private final Locator addToCartButton;

    public ItemDetailPage(Page page) {
        super(page);
        itemName = page.locator(".inventory_details_name");
        itemPrice = page.locator(".inventory_details_price"); // Make sure this selector matches your app
        addToCartButton = page.locator("[name='add-to-cart']");
    }

    @Step("Validating visibility of item detail elements")
    public void validateVisibleElements() {
        Logs.info("Validating visibility of item name");
        assertThat(itemName).isVisible();

        Logs.info("Validating visibility of item price");
        assertThat(itemPrice).isVisible();

        Logs.info("Validating visibility of 'Add to cart' button");
        assertThat(addToCartButton).isVisible();
    }

    @Step("Validating item name: {itemName}")
    public void validateItemName(String itemName) {
        Logs.info("Validating that the item name is: " + itemName);
        assertThat(this.itemName).hasText(itemName);
    }
}
