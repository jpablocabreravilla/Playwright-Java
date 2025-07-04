package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import utilities.BaseTest;

public class LoginTest extends BaseTest {

    @Test
    @Tag("regression")
    public void verificarPaginaLoginTest() {
        final var loginPage = new LoginPage(page);
        loginPage.validateVisibleElements();
    }

    @Test
    @Tag("regression")
    public void mensajeErrorTest() {
        final var loginPage = new LoginPage(page);
        loginPage.fillForm("locked_out_user", "secret_sauce");
        loginPage.verifyErrorMessage("Epic sadface: Sorry, this user has been locked out.");
    }
}
