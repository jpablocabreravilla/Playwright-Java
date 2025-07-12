package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utilities.AllureUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutComplete extends BasePage {

    private final Locator heading;
    private final Locator image;
    private final Locator backHomeButton;
    private final Locator confirmationText;

    public CheckoutComplete(Page page) {
        super(page);

        Locator container = page.locator("#checkout_complete_container");

        heading = container.getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setName("Thank you for your order!"));

        image = container.getByRole(AriaRole.IMG,
                new Locator.GetByRoleOptions().setName("Pony Express"));

        backHomeButton = container.getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("Back Home"));

        confirmationText = container.locator("//div[contains(@class, 'complete-text')]");
    }

    public void selectBackHomeBtn() {
        backHomeButton.click();
    }

    public void verifyCheckoutComplete() {
        assertThat(heading).isVisible();
        assertThat(image).isVisible();
        assertThat(backHomeButton).isVisible();
        assertThat(confirmationText).hasText(
                "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
        );

        AllureUtils.attachScreenshot(page);
    }
}
