package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import utilities.BaseTest;
import utilities.TestLogger;

public class LoginTest extends BaseTest {

    @Test
    @Tag("regression")
    public void shouldDisplayLoginElements() {
        String testName = "shouldDisplayLoginElements";
        TestLogger.start(testName);

        LoginPage loginPage = new LoginPage(page);
        loginPage.validateVisibleElements();

        TestLogger.pass(testName);
    }

    @Test
    @Tag("regression")
    public void shouldShowErrorForLockedUser() {
        String testName = "shouldShowErrorForLockedUser";
        TestLogger.start(testName);

        LoginPage loginPage = new LoginPage(page);
        loginPage.fillForm("locked_out_user", "secret_sauce");
        loginPage.verifyErrorMessage("Epic sadface: Sorry, this user has been locked out.");

        TestLogger.pass(testName);
    }
}
