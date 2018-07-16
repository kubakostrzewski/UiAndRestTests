package uiTests;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pageObjects.ImgToSendWindow;
import ui.pageObjects.MainPagePopUp;
import ui.pageObjects.SharePicturePage;
import ui.pageObjects.TopBar;
import ui.utils.Action;
import ui.utils.BaseTest;
import ui.utils.Page;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Page({MainPagePopUp.class, TopBar.class, ImgToSendWindow.class, SharePicturePage.class})
public class US02UiTest extends BaseTest {

    @Test
    public void runTest() throws IOException, ParseException {

        String filePath = testDataManager.getProperty("imgFromHardDrive");

        action.click(MainPagePopUp.skipBtn, "Skip button");
        action.click(TopBar.newPostBtn, "New Post button");
        action.sendFileFromHardDrive(filePath, ImgToSendWindow.localFileInput);
        action.waitForElement(SharePicturePage.addedImage, "Added image");
        action.click(SharePicturePage.deletePost, "Delete post button");
        action.click(SharePicturePage.confirmDeletePopup, "Confirm delete popup");

    }
}
