package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utilities.Logs;

import java.util.Map;

public class CheckoutYourInformationPage extends BasePage {

    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator zipCodeInput;
    private final Locator continueButton;

    public CheckoutYourInformationPage(Page page) {
        super(page);
        this.firstNameInput = page.locator("#first-name");
        this.lastNameInput = page.locator("#last-name"); // <-- reemplazado xpath por CSS
        this.zipCodeInput = page.locator("[name='postalCode']");
        this.continueButton = page.locator("#continue"); // <-- reemplazado xpath por CSS
    }

    @Step("Filling checkout information: {userData}")
    public void fillInformation(Map<String, String> userData) {
        Logs.info("Filling in checkout form with: " + userData);

        if (userData.containsKey("firstName")) {
            String value = userData.get("firstName");
            Logs.info("Typing first name: " + value);
            firstNameInput.fill(value);
        }

        if (userData.containsKey("lastName")) {
            String value = userData.get("lastName");
            Logs.info("Typing last name: " + value);
            lastNameInput.fill(value);
        }

        if (userData.containsKey("zipCode")) {
            String value = userData.get("zipCode");
            Logs.info("Typing zip code: " + value);
            zipCodeInput.fill(value);
        }

        Logs.info("Clicking 'Continue' button");
        continueButton.click();
    }
}
