package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ImgToSendWindow {

    @FindBy(id = "paste-url-input")
    public static WebElement urlInput;

    @FindBy(xpath = "//input[@type='file']")
    public static WebElement localFileInput;

}
