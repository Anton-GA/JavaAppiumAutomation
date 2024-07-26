package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public void waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.click();
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.sendKeys(value);
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize(); // получаем размер экрана девайса
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8); // получаем начальную точку, которая на ходится на 80% внизу, т.е. немного над нижним краем экрана
        int end_y = (int) (size.height * 0.2);


        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform(); //метод perform отсылает всю последовательность действий на выполнение
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
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

    public void swipeElementToLeft(By by, String errorMessage) {
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
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();
    }

    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if(amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}

