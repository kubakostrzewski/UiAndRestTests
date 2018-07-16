package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopBar {

    @FindBy(xpath = "//a[@class='ButtonLink Button upload']")
    public static WebElement newPostBtn;

    @FindBy(className = "icon-search")
    public static WebElement searchBtn;

    @FindBy(className = "search")
    public static WebElement searchInput;

}
