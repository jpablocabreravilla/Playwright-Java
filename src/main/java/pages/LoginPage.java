package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator title;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        super(page);
        usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username"));
        passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password"));
        loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        errorMessage = page.getByTestId("error");
        title = page.getByText("Swag Labs");
    }

    public void validatePageVisibleElements() {
        PlaywrightAssertions.assertThat(usernameInput).isVisible();
        PlaywrightAssertions.assertThat(passwordInput).isVisible();
        PlaywrightAssertions.assertThat(loginButton).isVisible();
        PlaywrightAssertions.assertThat(title).isVisible();
    }

}



