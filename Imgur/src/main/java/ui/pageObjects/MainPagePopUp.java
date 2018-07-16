package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPagePopUp {

    @FindBy(xpath = "//span[@class='Button-label'][contains(text(),'Ok, go away.')]")
    public static WebElement skipBtn;
}
