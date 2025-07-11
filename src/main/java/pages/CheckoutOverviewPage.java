package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import utilities.Logs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutOverviewPage extends BasePage {

    private final Locator cartItems;
    private final Locator itemTotal;
    private final Locator tax;
    private final Locator total;
    private final Locator btnFinish;

    public CheckoutOverviewPage(Page page) {
        super(page);
        this.cartItems = page.locator(".cart_item");
        this.itemTotal = page.locator(".summary_subtotal_label");
        this.tax = page.locator("//div[contains(@class, 'summary_tax_label')]");
        this.total = page.locator("//div[contains(@class, 'summary_total_label')]");
       this.btnFinish = page.locator("//*[@id='finish']");

    }

    @Step("Extracting item prices from each cart item")
    private List<Double> getItemPrices() {
        int itemCount = cartItems.count();
        Logs.info("Total items found in cart: " + itemCount);

        List<Double> prices = new ArrayList<>();

        for (int i = 0; i < itemCount; i++) {
            Locator item = cartItems.nth(i);
            String priceText = item.locator(".inventory_item_price").innerText().trim();
            String numeric = priceText.replace("$", "").trim();
            double price = Double.parseDouble(numeric);
            Logs.info("Item " + (i + 1) + " price: $" + price);
            prices.add(price);
        }

        Logs.info("All extracted prices: " + prices);
        return prices;
    }

    @Step("Summing item prices")
    private double sumItemPrices(List<Double> prices) {
        double sum = 0;
        for (double price : prices) {
            sum += price;
        }
        Logs.info("Total sum of item prices: $" + sum);
        return sum;
    }

    private double extractAmountFromText(String rawText) {
        Matcher matcher = Pattern.compile("\\$([\\d.]+)").matcher(rawText);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        throw new RuntimeException("No amount found in text: " + rawText);
    }


    @Step("Reading item total displayed in UI")
    public double getItemTotalAmount() {
        String rawText = itemTotal.innerText();
        double total = extractAmountFromText(rawText);
        Logs.info("Item total from UI: $" + total);
        return total;
    }

    @Step("Reading tax value from UI")
    public double getTaxAmount() {
        String rawText = tax.innerText();
        double value = extractAmountFromText(rawText);
        Logs.info("Tax from UI: $" + value);
        return value;
    }

    @Step("Reading total amount from UI")
    public double getTotalAmount() {
        String rawText = total.innerText(); // Ej: "Total: $120.92"
        double value = extractAmountFromText(rawText);
        Logs.info("Total from UI: $" + value);
        return value;
    }


    @Step("Calculating expected tax (8%) based on item total")
    public double calculateExpectedTax(double itemTotal) {
        double tax = Math.round(itemTotal * 0.08 * 100.0) / 100.0;
        Logs.info("Expected tax for $" + itemTotal + ": $" + tax);
        return tax;
    }

    @Step("Verifying item total, tax and final total are correctly calculated")
    public void verifyItemPrices() {
        List<Double> prices = getItemPrices();
        double calculatedItemTotal = sumItemPrices(prices);
        double uiItemTotal = getItemTotalAmount();

        Logs.info("Comparing calculated item total with UI value...");
        Assertions.assertEquals(calculatedItemTotal, uiItemTotal, 0.01, "Item total mismatch");

        double expectedTax = calculateExpectedTax(uiItemTotal);
        double uiTax = getTaxAmount();

        Logs.info("Comparing calculated tax with UI value...");
        Assertions.assertEquals(expectedTax, uiTax, 0.01, "Tax mismatch");

        double expectedTotal = uiItemTotal + uiTax;
        double uiTotal = getTotalAmount();

        Logs.info("Comparing calculated total with UI value...");
        Assertions.assertEquals(expectedTotal, uiTotal, 0.01, "Total mismatch");
    }


    public void clickBtnFinish() {
        btnFinish.click();
    }
}
