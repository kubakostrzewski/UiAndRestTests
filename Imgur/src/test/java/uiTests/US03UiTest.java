package uiTests;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pageObjects.ImgToSendWindow;
import ui.pageObjects.MainPagePopUp;
import ui.pageObjects.SharePicturePage;
import ui.pageObjects.TopBar;
import ui.utils.BaseTest;
import ui.utils.Page;

import java.io.IOException;

@Page({MainPagePopUp.class, TopBar.class, ImgToSendWindow.class, SharePicturePage.class})
public class US03UiTest extends BaseTest {

    @Test
    public void runTest() throws IOException, ParseException {

        String filePath = testDataManager.getProperty("imgFromHardDrive");

        action.click(MainPagePopUp.skipBtn, "Skip button");
        action.click(TopBar.newPostBtn,"New Post Button");
        action.sendFileFromHardDrive(filePath, ImgToSendWindow.localFileInput);
        action.waitForElement(SharePicturePage.addedImage, "Added image");
        action.type(SharePicturePage.descriptionInput,"#FirstTag #SecondTag","Picture tag");
        action.waitForElement(SharePicturePage.firstTag,"#FirstTag tag");
        action.waitForElement(SharePicturePage.secondTag,"#SecondTag tag");
        Assert.assertEquals(SharePicturePage.firstTag.getText(), "FirstTag");
        Assert.assertEquals(SharePicturePage.secondTag.getText(), "SecondTag");
        action.waitForElement(SharePicturePage.shareBtn,"Share to community button");
    }
}
