package tests;

import baseTests.BaseTest;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        // Instanciar la página de login
        LoginPage loginPage = new LoginPage(page);

        // Navegar a la URL de la aplicación
        page.navigate("https://www.saucedemo.com/");



    }
}
