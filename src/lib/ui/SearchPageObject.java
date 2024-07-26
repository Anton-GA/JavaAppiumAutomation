package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']", // TPL == Template
            SEARCH_BUTTON_VIEW_ARTICLE_IN_BROWSER = "//*[@text='View article in browser']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) { // Это метод темплейтов (шаблон), в котором по шаблону меняем одно на другое
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element", 5);
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still appear", 5);
    }

    public void clickCancelButton() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "Cannot find and type into search input", 5);
    }

//    public void waitForSearchResult(String substring) {
//        String searchResultXpath = getResultSearchElement(substring);
//        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with substring" + substring, 5);
//    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath), "Cannot find and click search result with substring" + substring, 5);
    }

//    public void clickOnArticleInSearchLine(String substring) {
//        String searchResultXpath = getResultSearchElement(substring);
//        this.waitForElementAndClick(By.xpath(searchResultXpath), "Cannot click on article after search", 5);
//    }
}
