package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StoreMainPage {

    @FindAll(@FindBy( className = "product-title"))
    public static List<WebElement> productLinkList;

}
