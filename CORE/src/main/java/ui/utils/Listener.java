package ui.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            takeScreenShot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
    private void takeScreenShot() throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String pathName = "test-output/errorScreenshots/error" + Long.parseLong(dateFormatter.format(date)) + ".jpg";
        File file = new File(pathName);
        File scrFile = ((TakesScreenshot) BaseCore.driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(pathName));
        Reporter.log("<a href=\"Screenshot\"><p align=\"left\">Error screenshot at " + date + "</p>");
        Reporter.log("<p><img width=\"1024\" src=\"" + file.getAbsolutePath() + "\" alt=\"screenshot at " + date + "\"/></p></a><br />");

    }
}
