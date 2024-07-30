package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
        TITLE = "xpath://*[@text='Appium']", // Переделать
        FOOTER_ELEMENT = "xpath://*[@text='View article in browser']",
        TO_MY_LIST_SAVE_BUTTON = "id:org.wikipedia:id/page_save",
        ADD_TO_LIST_BUTTON = "id:org.wikipedia:id/snackbar_action",
        MY_LIST_INPUT_FIELD = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article on page", 5);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
    }

    public void addArticleToMyList(String nameOfFolder) {
        this.waitForElementAndClick(
                TO_MY_LIST_SAVE_BUTTON,
                "Cannot find to save button",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_LIST_BUTTON,
                "Cannot find 'add to list' button",
                5
        );
        this.waitForElementAndSendKeys(
                MY_LIST_INPUT_FIELD,
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }
}
