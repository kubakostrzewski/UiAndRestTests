package uiTests;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pageObjects.MainPagePopUp;
import ui.pageObjects.SearchResultPage;
import ui.pageObjects.TopBar;
import ui.utils.BaseTest;
import ui.utils.Page;

import java.io.IOException;

@Page({MainPagePopUp.class, TopBar.class, SearchResultPage.class})
public class US06UiTest extends BaseTest {

    @Test
    public void runTest() throws IOException {

        action.click(MainPagePopUp.skipBtn, "Skip button");
        action.click(TopBar.searchBtn,"Search button");
        action.type(TopBar.searchInput,"bear" + Keys.ENTER,"Search box");
        action.waitForElement(SearchResultPage.searchResultLabel,"Search result label");
        Assert.assertEquals(SearchResultPage.searchResultLabel.getText(), "bear",
                "Search result label is different then expected");
    }
}
