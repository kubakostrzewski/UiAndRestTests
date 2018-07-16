package uiTests;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ui.pageObjects.MainPagePopUp;
import ui.pageObjects.SharePicturePage;
import ui.pageObjects.ImgToSendWindow;
import ui.pageObjects.TopBar;
import ui.utils.BaseTest;
import ui.utils.Page;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


@Page({MainPagePopUp.class, TopBar.class, ImgToSendWindow.class, SharePicturePage.class})
public class US04UiTest extends BaseTest {

    @Test
    public void runTest() throws IOException, ParseException, UnsupportedFlavorException {

        String filePath = testDataManager.getProperty("imgFromHardDrive");

        action.click(MainPagePopUp.skipBtn, "Skip button");
        action.click(TopBar.newPostBtn, "New post button");
        action.sendFileFromHardDrive(filePath, ImgToSendWindow.localFileInput);
        action.waitForElement(SharePicturePage.addedImage, "Added image");
        action.click(SharePicturePage.copyUrlBtn, "Copy URL button");
        String clipboardContent = action.getClipboardContent();
        Assert.assertEquals(clipboardContent, driver.getCurrentUrl(), "Copied URL is different then expected");
        Reporter.log("Copied URL OK\nEXPECTED: " + driver.getCurrentUrl() + "\nFOUND: " + clipboardContent + "\n");
    }

}
