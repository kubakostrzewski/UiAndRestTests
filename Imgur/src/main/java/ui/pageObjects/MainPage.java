package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {

    @FindBy(className = "TrendingTags-labelToggle")
    public static WebElement moreTagsBtn;

    @FindBy(xpath = "//div[@class='Tag-name' and contains(text(),'gaming')]/ancestor::a")
    public static WebElement gamingLink;

}
