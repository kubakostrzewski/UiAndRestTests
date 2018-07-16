package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage {

    @FindBy(xpath = "//div[@class='btn-add-wrapper']/input")
    public static WebElement addToCartBtn;

}
