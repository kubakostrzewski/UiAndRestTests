package uiTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pageObjects.*;
import ui.utils.BaseTest;
import ui.utils.Page;

import java.io.IOException;

@Page({MainPagePopUp.class, BottomBar.class, TopBar.class, StoreMainPage.class, ProductPage.class, YourCartPage.class,
        SharePicturePage.class, ShippingAddressPage.class})
public class US07UiTest extends BaseTest {

    @Test
    public void runTest() throws IOException {

        String productName, productInCartName;

        action.click(MainPagePopUp.skipBtn, "Skip button");
        action.click(BottomBar.storeLink,"Imgur Store Link");
        productName = StoreMainPage.productLinkList.get(0).getText();
        action.click(StoreMainPage.productLinkList.get(0),"Product link");
        action.click(ProductPage.addToCartBtn,"Add to cart button");
        action.waitForElement(YourCartPage.productInCartList.get(0),"Cart product");
        productInCartName = YourCartPage.productInCartList.get(0).getText();
        Assert.assertEquals(productName, productInCartName, "Product in cart is different then expected");
        action.clickCheckRadio(YourCartPage.termsCheckbox,"Therms and conditions checkbox");
        action.click(YourCartPage.checkoutBtn,"Checkout button");
        action.waitForElement(ShippingAddressPage.shippingAddressLabel,"Shipping address label");
    }
}
