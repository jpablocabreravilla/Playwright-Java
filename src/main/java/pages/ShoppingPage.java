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
    private final Locator itemPrices;
    private final Locator itemNames;
    private final Locator addToCartButtons;

    public ShoppingPage(Page page) {
        super(page);
        this.title = page.getByText("Products");
        this.inventoryList = page.getByTestId("inventory-list");
        this.sortDropdown = page.getByTestId("product-sort-container");
        this.itemPrices = page.getByTestId("inventory-item-price");
        this.itemNames = page.getByTestId("inventory-item-name");
        this.addToCartButtons = page.locator(".btn_inventory");
    }

    private Locator getItemButton(String state, String name) {
        final var testId = String.format("%s %s", state, name)
                .toLowerCase()
                .replace(" ", "-");

        return page.getByTestId(testId);
    }

    @Step("Verifying that Shopping Page is visible")
    public void verifyPageIsDisplayed() {
        Logs.info("Verifying the Shopping page is displayed");

        assertAll(
                () -> assertThat(title).isVisible(),
                () -> assertThat(inventoryList).isVisible(),
                () -> assertThat(sortDropdown).isVisible()
        );
    }

    @Step("Verifying button toggles from 'Add to cart' to 'Remove' for item: {itemName}")
    public void verifyAddRemoveButtonText(String itemName) {
        Logs.info("Checking button toggle for item: " + itemName);

        final var addButton = getItemButton("Add to cart", itemName);
        assertThat(addButton).hasText("Add to cart");
        addButton.click();

        final var removeButton = getItemButton("Remove", itemName);
        assertThat(removeButton).hasText("Remove");
    }

    @Step("Verifying item price at index {index}")
    public void verifyItemPrice(int index, String expectedPrice) {
        Logs.info("Verifying price at index " + index + " is: " + expectedPrice);
        assertThat(itemPrices.nth(index)).hasText(expectedPrice);
    }

    @Step("Clicking all visible 'Add to Cart' buttons")
    public void clickAllAddToCartButtons() {
        final List<Locator> buttons = addToCartButtons.all();
        Logs.info("Found " + buttons.size() + " 'Add to Cart' buttons");

        buttons.forEach(button -> {
            assertThat(button).isVisible();
            button.click();
        });
    }

    @Step("Sorting items by option: {value}")
    public void sortItems(String value) {
        Logs.info("Sorting items by: " + value);
        sortDropdown.selectOption(value);
    }

    @Step("Verifying first and last item prices")
    public void verifyFirstAndLastItemPrices(String first, String last) {
        Logs.info("Verifying first and last item prices: " + first + ", " + last);

        assertAll(
                () -> assertThat(itemPrices.first()).hasText(first),
                () -> assertThat(itemPrices.last()).hasText(last)
        );
    }

    @Step("Verifying first and last item names")
    public void verifyFirstAndLastItemNames(String first, String last) {
        Logs.info("Verifying first and last item names: " + first + ", " + last);

        assertAll(
                () -> assertThat(itemNames.first()).hasText(first),
                () -> assertThat(itemNames.last()).hasText(last)
        );
    }

    @Step("Clicking on item title: {itemName}")
    public void clickItemTitle(String itemName) {
        Logs.info("Clicking on item title: " + itemName);
        final Locator item = itemNames.getByText(itemName);
        assertThat(item).isVisible();
        item.click();
    }

    @Step("Clicking 'Remove' button for item: {itemName}")
    public void clickRemoveFromCartButton(String itemName) {
        Logs.info("Removing item from cart: " + itemName);
        final var removeButton = getItemButton("Remove", itemName);
        assertThat(removeButton).isVisible();
        removeButton.click();
    }

    @Step("Clicking 'Add to Cart' button for item: {itemName}")
    public void clickAddToCartButton(String itemName) {
        Logs.info("Adding item to cart: " + itemName);
        final var addButton = getItemButton("Add to cart", itemName);
        assertThat(addButton).isVisible();
        addButton.click();
    }
}
