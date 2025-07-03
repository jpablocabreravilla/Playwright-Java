package baseTests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        browser = playwright.chromium().launch(new LaunchOptions().setHeadless(headless));
        context = browser.newContext(new Browser.NewContextOptions());
                //.setViewportSize(1280, 720)
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
