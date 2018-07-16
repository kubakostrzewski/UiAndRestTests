package ui.utils;

import managers.ConfigManager;
import managers.TestDataManager;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class BaseTest extends BaseCore {

    protected TestDataManager testDataManager;

    @BeforeClass
    public void setUpTest() throws ParseException, IOException {

        String url = new ConfigManager().getProperty("url");
        testDataManager = new TestDataManager();
        driver.navigate().to(url);

    }

}
