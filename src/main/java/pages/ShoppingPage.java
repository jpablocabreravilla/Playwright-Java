package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.Logs;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ShoppingPage extends BasePage {

    private final Locator title;
    private final Locator inventoryList;
    private final Locator select;
    private final Locator itemPrice;
    private final Locator itemName;
    private final Locator addToCartButton;


    public ShoppingPage(Page page) {
        super(page);
        title = page.getByText("Products");
        inventoryList = page.getByTestId("inventory-list");
        select = page.getByTestId("product-sort-container");
        itemPrice = page.getByTestId("inventory-item-price");
        itemName = page.getByTestId("inventory-item-name");
        addToCartButton = page.locator(".btn_inventory");
    }

    private Locator getItemName(String state, String name) {
        final var texto2 = String.format("%s %s", state, name);      // Add to cart Sauce Labs Backpack
        final var texto3 = texto2.toLowerCase();                     // add to cart sauce labs backpack
        final var texto4 = texto3.replace(" ", "-");                 // add-to-cart-sauce-labs-backpack

        return page.getByTestId(texto4);
    }

    public void verifyPage() {
        Logs.info("Verificando la página de Shopping");
        assertAll(
                () -> assertThat(title).isVisible(),
                () -> assertThat(inventoryList).isVisible(),
                () -> assertThat(select).isVisible()
        );
    }

    @Step("Verificando los textos de Add to Cart & Remove")
    public void verifyAddRemove(String itemName) {
        Logs.info("Verifico el texto de add to cart");
        final var addToCartButton = getItemName("Add to cart", itemName);
        assertThat(addToCartButton).hasText("Add to cart");

        Logs.info("Hago click en el botón add to cart");
        addToCartButton.click();

        Logs.info("Verifico el texto de remove");
        assertThat(getItemName("Remove", itemName)).hasText("Remove");
    }

    @Step("Verificando precio")
    public void verifyItemPrice(int index, String expected) {
        Logs.info("Verificando precio");
        assertThat(itemPrice.nth(index)).hasText(expected);
    }

    @Step("Haciendo click en todos los botones Add to Cart")
    public void clickAddToCartButton() {
        Logs.info("Haciendo click en todos los botones Add to Cart");
        final var list = addToCartButton.all();
        list.forEach(Locator::click);
        /*for (var element : list) {
            element.click();
        }*/
    }

    @Step("Ordenando los ítems")
    public void orderItems(String value) {
        Logs.info("Ordenando los ítems");
        select.selectOption(value);
    }

    @Step("Verificando el primer y último precio")
    public void verifyItemPrices(String first, String last) {
        Logs.info("Verificando el primer y último precio");

        final var primerPrecio = itemPrice.first();
        final var ultimoPrecio = itemPrice.last();

        assertAll(
                () -> assertThat(primerPrecio).hasText(first),
                () -> assertThat(ultimoPrecio).hasText(last)
        );
    }

    @Step("Verificando el primer y último nombre")
    public void verifyItemNames(String first, String last) {
        Logs.info("Verificando el primer y último nombre");

        final var primerNombre = itemName.first();
        final var ultimoNombre = itemName.last();

        assertAll(
                () -> assertThat(primerNombre).hasText(first),
                () -> assertThat(ultimoNombre).hasText(last)
        );
    }


}