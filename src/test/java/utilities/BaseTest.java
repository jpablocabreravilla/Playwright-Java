package utilities;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@UsePlaywright(CustomOptions.class)
public class BaseTest {
    protected Page page;

    @BeforeEach
    public void setUp(Page page) {
        this.page = page;
        Logs.info("Navigating to the base URL");
        page.navigate("/");
    }

    @AfterEach
    public void tearDown() {
    }

}
