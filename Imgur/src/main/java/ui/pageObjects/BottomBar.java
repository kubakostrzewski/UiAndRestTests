package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BottomBar {

    @FindBy(xpath = "//ul[@class='Footer-navbar']//a[@href='http://store.imgur.com']")
    public static WebElement storeLink;
}
