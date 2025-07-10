package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.Logs;

import java.util.Map;
import java.util.Optional;

public class CheckoutYourInformationPage extends BasePage {

    private final Locator firstName;
    private final Locator lastName;
    private final Locator zipCode;
    private final Locator btnContinue;

    public CheckoutYourInformationPage(Page page) {
        super(page);
        firstName = page.locator("#first-name");
        lastName = page.locator("//*[@id='last-name']");
        zipCode = page.locator("[name='postalCode']");
        btnContinue = page.locator("//*[@id='continue']");
    }

    @Step("Fill information with provided data")
    public void fillInformation(Map<String, String> userData) {
        Logs.info("Filling in user information");

        Optional.ofNullable(userData.get("firstName")).ifPresent(firstName::fill);
        Optional.ofNullable(userData.get("lastName")).ifPresent(lastName::fill);
        Optional.ofNullable(userData.get("zipCode")).ifPresent(zipCode::fill);
        btnContinue.click();
    }
}
