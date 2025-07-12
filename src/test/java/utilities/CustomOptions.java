package utilities;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

public class CustomOptions implements OptionsFactory {

    @Override
    public Options getOptions() {
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        return new Options()
                .setLaunchOptions(createLaunchOptions(isHeadless))
                .setContextOptions(null)
                .setHeadless(isHeadless)
                .setBrowserName("chromium")
                .setChannel("chrome")
                .setTestIdAttribute("data-test")
                .setBaseUrl("https://www.saucedemo.com");
    }

    private BrowserType.LaunchOptions createLaunchOptions(boolean isHeadless) {
        return new BrowserType.LaunchOptions()
                .setHeadless(isHeadless)
                .setSlowMo(0);
    }
}
