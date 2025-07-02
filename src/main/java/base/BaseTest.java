package base;

import com.microsoft.playwright.Page;
import factory.BrowserFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.HomePage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class BaseTest {

    private final BrowserFactory browserFactory = new BrowserFactory();
    protected Properties properties = browserFactory.initializeConfigProperties();
    protected Page page;
    protected HomePage homepage;
    private String browserName;

    @BeforeMethod
    @Parameters({"browserName", "headless"})
    public void setUp(@Optional("chrome") String browserName,
                      @Optional("false") String headless) {
        this.browserName = browserName;
        page = browserFactory.initializeBrowser(browserName, headless);
        String baseUrl = properties.getProperty("BASE_URL");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("BASE_URL is missing or empty in config.properties");
        }

        page.navigate(baseUrl.trim());
        homepage = new HomePage(page);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result);
        }
        if (page != null && page.context() != null && page.context().browser() != null) {
            page.context().browser().close();
        }
    }

    private void takeScreenshot(ITestResult result) throws IOException {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String resultName = result.getName() + "_" + browserName + "_" + timestamp;

        String screenshotDir = "target" + File.separator + "screenshots";
        Files.createDirectories(Paths.get(screenshotDir));

        Path screenshotPath = Paths.get(screenshotDir, resultName + ".png");

        page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
        attachScreenshotToAllureReport(resultName, screenshotPath);
    }

    @Attachment(type = "image/png")
    private void attachScreenshotToAllureReport(String name, Path path) throws IOException {
        byte[] screenshotBytes = Files.readAllBytes(path);
        Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes));
    }
}
