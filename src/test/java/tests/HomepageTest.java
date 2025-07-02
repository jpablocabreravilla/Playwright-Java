package tests;

import base.BaseTest;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomepageTest extends BaseTest {
    @Test
    public void verifyHeadingContainsGoogle() {
        Locator element = homepage.verification();
        Assert.assertTrue(element.isVisible());
    }
}
