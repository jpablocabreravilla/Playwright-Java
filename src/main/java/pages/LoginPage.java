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
        this.usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username")); // page.locator("#user-name")
        this.passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")); // page.locator("#password")
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));    // page.locator("#login-button")
        this.errorMessage = page.getByTestId("error");
        this.title = page.getByText("Swag Labs");
    }

    @Step("Validating visibility of login page elements")
    public void validateVisibleElements() {
        Logs.info("Checking visibility of login form elements");

        assertAll(
                () -> assertThat(usernameInput).isVisible(),
                () -> assertThat(passwordInput).isVisible(),
                () -> assertThat(loginButton).isVisible(),
                () -> assertThat(title).isVisible()
        );
    }

    @Step("Filling in login form with username: {username}")
    public void fillForm(String username, String password) {
        Logs.info("Filling login form");
        Logs.info("Typing username: " + username);
        usernameInput.fill(username);

        Logs.info("Typing password: [PROTECTED]");
        passwordInput.fill(password);

        Logs.info("Clicking login button");
        loginButton.click();
    }

    @Step("Verifying error message is: \"{expectedText}\"")
    public void verifyErrorMessage(String expectedText) {
        Logs.info("Validating error message: " + expectedText);

        assertAll(
                () -> assertThat(errorMessage).isVisible(),
                () -> assertThat(errorMessage).hasText(expectedText)
        );
    }
}
