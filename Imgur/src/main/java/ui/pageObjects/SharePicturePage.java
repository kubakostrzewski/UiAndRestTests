package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SharePicturePage {

    @FindBy(xpath = "//div[@class='post-image-description post-image-description--editable post-contenteditable']")
    public static WebElement descriptionInput;

    @FindBy(xpath = "//a[@class='post-options-tag'][1]")
    public static WebElement firstTag;

    @FindBy(xpath = "//a[@class='post-options-tag'][2]")
    public static WebElement secondTag;

    @FindBy(xpath = "a[contains(.,'Share to community')]")
    public static WebElement shareBtn;

    @FindBy(xpath = "//li[contains(@class, 'delete')]")
    public static WebElement deletePost;

    @FindBy(xpath = "//div[contains(@class,'btn-confirm-yes')]")
    public static WebElement confirmDeletePopup;

    @FindBy(xpath = "//div[@class = 'left post-pad']//img")
    public static WebElement addedImage;

    @FindBy(className = "copy-post")
    public static WebElement copyUrlBtn;
    

}
