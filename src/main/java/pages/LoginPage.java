package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import utilities.Logs;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    public void validateVisibleElements() {
        assertThat(usernameInput).isVisible();
        assertThat(passwordInput).isVisible();
        assertThat(loginButton).isVisible();
        assertThat(title).isVisible();
    }

    @Step("Filling in the login form")
    public void fillForm(String username, String password) {
        Logs.info("Typing username");
        usernameInput.fill(username);

        Logs.info("Typing password");
        passwordInput.fill(password);

        Logs.info("Clicking the login button");
        loginButton.click();
    }

    @Step("Verifying the error message")
    public void verifyErrorMessage(String expectedText) {
        Logs.info("Verifying the error message");

        assertAll(
                () -> assertThat(errorMessage).isVisible(),
                () -> assertThat(errorMessage).hasText(expectedText)
        );
    }
}
