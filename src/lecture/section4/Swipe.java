package lecture.section4;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class Swipe {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Pixel 7 API 29");
        capabilities.setCapability("platformName", "10");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/user/Desktop/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        WebElement element = driver.findElementByXPath("//*[contains(@text, 'Skip')]");
        element.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSwipeArticle() {  // тест свайпает до элемента
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Appium",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' article in search",
                5
        );
//        waitForElementPresent(
//                By.xpath("//android.widget.TextView[contains(@text, 'Java (programming language)')]"),
//                "Cannot find article title",
//                15
//        );
        swipeUpToFindElement(
                By.xpath("//*[@text='View article in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    @Test
    public void saveFirstArticleToMyList() {  // Тест сохраняет статью в закладки с созданием папки
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find 'Appium' article in search",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find article title",
                15
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find to save button",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find 'add to list' button",
                5
        );
        String nameOfFolder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find < link",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find < link",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "Cannot find navigation button to Saved",
                5
        );
//        waitForElementAndClick(
//                By.xpath("//*[@text='No thanks']"),
//                "Cannot find 'No thanks' button",
//                5
//        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find saved folder",
                5
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {  // Тест проверяет, что результат поиска больше 0
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine= "Linkin Park discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='" + searchLine + "']";

        waitForElementPresent(
                By.xpath(searchResultLocator),
                "Cannot find anything by the request " + searchLine,
                15
        );

        int amountOfSearchResults = getAmountOfElements(
                By.xpath(searchResultLocator)
        );

        Assert.assertTrue(
                "We found few results!",
                amountOfSearchResults > 0);
    }

    @Test
    public void testAmountOfEmptySearch() { // Тест проверяет, что результат поиска пустой
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine= "zxvffghfsdf";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='" + searchLine + "']";
        String emptyResultLabel = "//*[@text='No results']";

        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Cannot find empty result label by the request " + searchLine,
                15
        );

        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "We've found some results by request" + searchLine
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine= "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by " + searchLine,
                5
        );

        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation = waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                titleBeforeRotation,
                titleAfterRotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);
        String titleAfterSecondRotation = waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine= "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by " + searchLine,
                5
        );

        driver.runAppInBackground(3);
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find article after returning from background",
                5
        );
    }



    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private void waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.click();
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.sendKeys(value);
        return element;
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize(); // получаем размер экрана девайса
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8); // получаем начальную точку, которая на ходится на 80% внизу, т.е. немного над нижним краем экрана
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform(); //метод perform отсылает всю последовательность действий на выполнение
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while(driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMessage,0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    protected void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(
                by,
                errorMessage,
                10);

        int leftX = element.getLocation().getX(); // функция запишет в переменную самую левую точку элемента по оси Х
        int rightX = leftX + element.getSize().getWidth(); // берем размер элемента по ширине и прибавляем к левой точке. Получаем правую
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(rightX, middleY)
                .waitAction(150)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if(amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
