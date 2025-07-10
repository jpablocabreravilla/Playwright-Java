package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.Logs;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ShoppingPage extends BasePage {

    private final Locator title;
    private final Locator inventoryList;
    private final Locator sortDropdown;
    private final Locator itemPrice;
    private final Locator itemName;
    private final Locator addToCartButton;

    public ShoppingPage(Page page) {
        super(page);
        title = page.getByText("Products");
        inventoryList = page.getByTestId("inventory-list");
        sortDropdown = page.getByTestId("product-sort-container");
        itemPrice = page.getByTestId("inventory-item-price");
        itemName = page.getByTestId("inventory-item-name");
        addToCartButton = page.locator(".btn_inventory");
    }

    private Locator getItemButton(String state, String name) {
        final var buttonText = String.format("%s %s", state, name);     // Add to cart Sauce Labs Backpack
        final var lowerCaseText = buttonText.toLowerCase();             // add to cart sauce labs backpack
        final var testId = lowerCaseText.replace(" ", "-");             // add-to-cart-sauce-labs-backpack

        return page.getByTestId(testId);
    }

    public void verifyPageIsDisplayed() {
        Logs.info("Verifying the Shopping page is displayed");
        assertAll(
                () -> assertThat(title).isVisible(),
                () -> assertThat(inventoryList).isVisible(),
                () -> assertThat(sortDropdown).isVisible()
        );
    }

    @Step("Verifying 'Add to Cart' and 'Remove' button texts")
    public void verifyAddRemoveButtonText(String itemName) {
        Logs.info("Verifying 'Add to cart' button text");
        final var addButton = getItemButton("Add to cart", itemName);
        assertThat(addButton).hasText("Add to cart");

        Logs.info("Clicking 'Add to cart' button");
        addButton.click();

        Logs.info("Verifying 'Remove' button text");
        assertThat(getItemButton("Remove", itemName)).hasText("Remove");
    }

    @Step("Verifying item price")
    public void verifyItemPrice(int index, String expected) {
        Logs.info("Verifying item price at index " + index);
        assertThat(itemPrice.nth(index)).hasText(expected);
    }

    @Step("Clicking all 'Add to Cart' buttons")
    public void clickAllAddToCartButtons() {
        Logs.info("Clicking all visible 'Add to Cart' buttons");
        List<Locator> buttons = addToCartButton.all();
        Logs.info("Total 'Add to Cart' buttons found: " + buttons.size());
        buttons.forEach(button -> {
            assertThat(button).isVisible();
            button.click();
        });
    }

    @Step("Sorting items")
    public void sortItems(String value) {
        Logs.info("Sorting items");
        sortDropdown.selectOption(value);
    }

    @Step("Verifying first and last item prices")
    public void verifyFirstAndLastItemPrices(String first, String last) {
        Logs.info("Verifying first and last item prices");

        final var firstPrice = itemPrice.first();
        final var lastPrice = itemPrice.last();

        assertAll(
                () -> assertThat(firstPrice).hasText(first),
                () -> assertThat(lastPrice).hasText(last)
        );
    }

    @Step("Verifying first and last item names")
    public void verifyFirstAndLastItemNames(String first, String last) {
        Logs.info("Verifying first and last item names");

        final var firstItemName = itemName.first();
        final var lastItemName = itemName.last();

        assertAll(
                () -> assertThat(firstItemName).hasText(first),
                () -> assertThat(lastItemName).hasText(last)
        );
    }

    public void clickItemTitle(String itemName) {
        Logs.info("Clicking item title: " + itemName);
        Locator item = this.itemName.getByText(itemName);
        assertThat(item).isVisible();
        item.click();
    }

    @Step("Clicking 'Remove' button for item: {itemName}")
    public void clickRemoveFromCartButton(String itemName) {
        Logs.info("Clicking 'Remove' for item: " + itemName);
        Locator removeButton = getItemButton("Remove", itemName);
        assertThat(removeButton).isVisible();
        removeButton.click();
    }

    @Step("Clicking 'Add to Cart' button for item: {itemName}")
    public void clickAddToCartButton(String itemName) {
        Logs.info("Clicking 'Add to Cart' for item: " + itemName);
        Locator addButton = getItemButton("Add to cart", itemName);
        assertThat(addButton).isVisible();
        addButton.click();
    }
}
