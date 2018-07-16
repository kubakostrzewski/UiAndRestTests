package ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShippingAddressPage {

    @FindBy(xpath = "//h2[contains(.,'Shipping address')]")
    public static WebElement shippingAddressLabel;

}
