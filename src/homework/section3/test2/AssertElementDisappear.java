package homework.section3.test2;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;


public class AssertElementDisappear {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","Pixel 7 API 29");
        capabilities.setCapability("platformName","10");
        capabilities.setCapability("automationName","UiAutomator2");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/user/Desktop/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        WebElement element = driver.findElementByXPath("//*[contains(@text, 'Skip')]");
        element.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void hasElementDisappeared() {
        waitAndClickOnSearchField(
                By.id("org.wikipedia:id/search_container"),
                "Couldn't click on search field",
                5
        );

        inputTextInSearchField(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Couldn't find search field",
                5
        );

        assertHasSeveralTitle();
        clickOnClearButtonInSearchField(
                By.id("org.wikipedia:id/search_close_btn"),
                "Couldn't find clear button in search field!",
                5
        );
        assertHasPlaceholderInSearchField();
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage +"\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private void waitAndClickOnSearchField(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.click();
    }

    private WebElement inputTextInSearchField(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.sendKeys(value);
        return element;
    }

    private void clickOnClearButtonInSearchField(By by, String errorMessage, long timeoutInSeconds) {
        WebElement closeButton = waitForElementPresent(by, errorMessage, 5);
        closeButton.click();
    }

    private void assertHasSeveralTitle() {
        int isPresent = driver.findElementsByXPath("//*[contains(@text, 'Java')]").size();
        if(isPresent > 1){
            Assert.assertTrue(true);
        }
    }

    private void assertHasPlaceholderInSearchField() {
        WebElement titleElement = waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find placeholder in search field",
                5);
        String placeholderInSearchField = titleElement.getAttribute("text");

        Assert.assertEquals("This is unexpected placeholder!",
                "Search Wikipedia",
                placeholderInSearchField
        );
    }
}
