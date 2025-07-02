package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public Locator verification() {
        return page.locator("(//div[contains(text(),'Google')])[1]");
    }

}
