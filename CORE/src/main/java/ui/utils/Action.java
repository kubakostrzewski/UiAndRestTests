package ui.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Action {

    private final WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public Action(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        this.driver = driver;
        actions = new Actions(driver);
    }

    private int step = 1;

    public void click(WebElement element, String name) throws IOException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            highlightElement(element);
            actions.moveToElement(element).perform();
            element.click();
        } catch (NullPointerException nue) {
            Assert.assertTrue(false, "<NullPointerException - check @Page annotation");
        } catch (Exception e) {
            Assert.assertTrue(false, "Element \"" + name + "\" is not clickable.\n" + e.getMessage());
        }
        Reporter.log(step++ + ". Element \"" + name + "\" was successfully clicked<br>");
    }

    public void clickCheckRadio(WebElement element, String name) throws IOException {
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            highlightElement(element);
            actions.moveToElement(element).perform();
            element.click();
        } catch (Exception e) {
            Assert.assertTrue(false, "Element \"" + name + "\" is not clickable.\n" + e.getMessage());
        }
        Reporter.log(step++ + ". Element " + name + " was successfully clicked<br>");
    }


    public void type(WebElement element, String text, String name) throws IOException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            highlightElement(element);
            actions.moveToElement(element).perform();
            element.clear();
            element.sendKeys(text);
        } catch (NullPointerException nue) {
            Assert.assertTrue(false, "NullPointerException - check @Page annotation");
        } catch (Exception e) {
            Assert.assertTrue(false, "Wasn't able to type in element \"" + name + "\".\n" + e.getMessage());
        }
        Reporter.log(step++ + ". Text \"" + text + "\" was successfully typed into element \"" + name + "\"<br>");
    }

    public void pasteText(WebElement element, String text, String name) throws IOException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            highlightElement(element);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(text), null);
            element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
        } catch (NullPointerException nue) {
            Assert.assertTrue(false, "NullPointerException - check @Page annotation");
        } catch (Exception e) {
            Assert.assertTrue(false, "Can not paste text \"" + text + "\" to \"" + name + "\" element");
        }
        Reporter.log(step++ + "Text \"" + text + "\" successfully pasted to \"" + element + "\" element<br>");
    }

    public void waitForElement(WebElement element, String name) throws IOException {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightElement(element);
            actions.moveToElement(element).perform();
        } catch (NullPointerException nue) {
            Assert.assertTrue(false, "NullPointerException - check @Page annotation");
        } catch (Exception e) {
            Assert.assertTrue(false, "Element \"" + name + "\" is not visible.\n" + e.getMessage());
        }
        Reporter.log(step++ + ". Element " + name + " was successfully displayed<br>");
    }
    public void sendFileFromHardDrive(String filePath, WebElement element) throws IOException {

        File temp = new File(filePath);
        sendKeysToElement(element, temp.getAbsolutePath(), "File input");
    }

    public void sleep(int timeMill) {
        try {
            Thread.sleep(timeMill);
        } catch (InterruptedException e) {
            Assert.assertTrue(false, "I cannot wait even a one millisecond!!! And you want me to wait for "
                    + timeMill + " milliseconds???\n" + e.getMessage());
        }
        Reporter.log(step++ + ".\tProgram successfully slept for " + timeMill + " milliseconds. Congrats!<br>");
    }

    public void sendKeysToElement(WebElement element, String text, String name) throws IOException {

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            highlightElement(element);
            element.sendKeys(text);

        } catch (Exception e) {
            Assert.assertTrue(false, "Element \"" + name + "\" is not available.\n. Cannot send keys to " + name + ". " + e.getMessage());
        }
        Reporter.log(step++ + ". Text: [" + text + "] was successfully send to element: [" + element + "]<br>");
    }

    public String getClipboardContent() throws IOException, UnsupportedFlavorException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String content = (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
        return content;
    }

    private void highlightElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].style.border='3px solid red'", element);
    }
}
