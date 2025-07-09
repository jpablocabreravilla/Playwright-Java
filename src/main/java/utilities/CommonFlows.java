package utilities;

import com.microsoft.playwright.Page;
import pages.LoginPage;

public class CommonFlows {
    private final Page page;

    public CommonFlows(Page page) {
        this.page = page;
    }

    public void loginAsStandardUser() {
        loginAs("standard_user", "secret_sauce");
    }

    public void loginAs(String username, String password) {
        new LoginPage(page).fillForm(username, password);
    }

}
