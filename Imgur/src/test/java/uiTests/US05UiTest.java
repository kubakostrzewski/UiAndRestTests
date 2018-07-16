package uiTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pageObjects.CategoryPage;
import ui.pageObjects.MainPage;
import ui.pageObjects.MainPagePopUp;
import ui.utils.BaseTest;
import ui.utils.Page;

import java.io.IOException;

@Page({MainPagePopUp.class, MainPage.class, CategoryPage.class})
public class US05UiTest extends BaseTest {

    @Test
    public void runTest() throws IOException {

        action.click(MainPagePopUp.skipBtn, "Skip button");
        action.click(MainPage.moreTagsBtn, "Most Viral category button");
        action.click(MainPage.gamingLink, "Gaming link");
        action.waitForElement(CategoryPage.categoryLabel, "Category label");
        Assert.assertEquals(CategoryPage.categoryLabel.getText(), "gaming",
                "Category label text is different then expected");
    }
}
