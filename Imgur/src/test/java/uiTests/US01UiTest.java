package uiTests;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import ui.pageObjects.ImgToSendWindow;
import ui.pageObjects.MainPagePopUp;
import ui.pageObjects.SharePicturePage;
import ui.pageObjects.TopBar;
import ui.utils.BaseTest;
import ui.utils.Page;
import java.io.IOException;

@Page({MainPagePopUp.class, TopBar.class, ImgToSendWindow.class, SharePicturePage.class})
public class US01UiTest extends BaseTest{

    @Test
    public void runTest() throws IOException, ParseException {

        action.click(MainPagePopUp.skipBtn, "Skip button");
        action.click(TopBar.newPostBtn, "New post button");
        action.pasteText(ImgToSendWindow.urlInput, testDataManager.getProperty("imageFromURL"), "URL input");
        action.waitForElement(SharePicturePage.addedImage, "Added image");
        action.click(SharePicturePage.deletePost, "Delete post button");
        action.click(SharePicturePage.confirmDeletePopup, "Confirm delete popup");

    }

}
