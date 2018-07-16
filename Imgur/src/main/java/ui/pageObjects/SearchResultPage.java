package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultPage {

    @FindBy(xpath = "//span[@class='search-term-text sorting-text-align']")
    public static WebElement searchResultLabel;
}
