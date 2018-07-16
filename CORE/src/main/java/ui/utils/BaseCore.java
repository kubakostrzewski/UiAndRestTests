package ui.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;


@Listeners({org.uncommons.reportng.HTMLReporter.class, Listener.class})
public class BaseCore {
    protected static WebDriver driver;
    private WebDriverWait wait;
    protected Action action;

    @BeforeClass
    public void setUp() throws IOException {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        if (System.getProperty("os.name").contains("Windows")){
            System.setProperty("webdriver.chrome.driver", "../CORE/driver/chromedriver_v_2_40.exe");
        }else {
            System.setProperty("webdriver.chrome.driver", "../CORE/driver/chromedriver");
        }

    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 10);
    driver.manage().window().maximize();
    initPage();
    action = new Action(wait, driver);
    }

    @BeforeMethod
    public void deleteCookies(){
        driver.manage().deleteAllCookies();
    }
    private void initPage() {
    Page page = this.getClass().getAnnotation(Page.class);
    for (Class pageName : page.value()) {
        PageFactory.initElements(driver, pageName);
        }
    }

    @AfterClass
    public void tearDown() throws IOException {
        driver.close();
        driver.quit();
    }

    @AfterSuite
    public void showReport() throws IOException {
        File file = new File("test-output/html/index.html");
        Desktop.getDesktop().browse(file.toURI());
    }
}
