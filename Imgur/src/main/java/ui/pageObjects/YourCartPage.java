package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YourCartPage {

    @FindAll(@FindBy(xpath = "//td[@class='line-title']/a"))
    public static List<WebElement> productInCartList;

    @FindBy(id = "agree")
    public static WebElement termsCheckbox;

    @FindBy(id = "checkout")
    public static WebElement checkoutBtn;

}
