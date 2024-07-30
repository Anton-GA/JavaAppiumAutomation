package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationUI extends MainPageObject{
    private static final String
        THE_BACK_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void theBackButton() {
        this.waitForElementAndClick(
                THE_BACK_BUTTON,
                "Cannot close article, cannot find < link",
                5
        );
    }
}
