package utilities;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AllureUtils {

    /**
     * Attaches a full-page screenshot to Allure report with a custom name.
     *
     * @param page The Playwright Page instance
     * @param name The name to be shown in the Allure report
     */
    public static void attachScreenshot(Page page, String name) {
        if (page == null) return;

        try {
            byte[] screenshot = page.screenshot(new ScreenshotOptions().setFullPage(true));
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to take screenshot: " + e.getMessage());
        }
    }

    /**
     * Attaches a full-page screenshot to Allure report with an auto-generated timestamp name.
     *
     * @param page The Playwright Page instance
     */
    public static void attachScreenshot(Page page) {
        String defaultName = "Screenshot " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        attachScreenshot(page, defaultName);
    }
}
