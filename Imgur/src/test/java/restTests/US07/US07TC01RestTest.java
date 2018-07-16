package restTests.US07;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;
import java.io.IOException;
import java.util.List;

import static rest.responses.GetGallerySearchResponse.getStringPropertyListFromData;

public class US07TC01RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String searchQuery;
    private List<String> list;
    private JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

    prepareTestData();
    httpResponse = getGallerySearch(null,null,null,searchQuery);
    action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
    response = EntityUtils.toString(httpResponse.getEntity());
    action.logResponse(httpResponse, response);
    list = getStringPropertyListFromData(response, "title");
    for (String title:list){
        Assert.assertTrue(title.toLowerCase().contains(searchQuery),
                "Title: " + title + "\n don't contains \"" + searchQuery + "\"\n" );
        Reporter.log("Title: " + title + "<br>contains \"" + searchQuery + "\"<br><br>");
    }
        Reporter.log("<h3>Search by time Test PASSED</h3>");
    }
    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us07TestData");
        searchQuery = testData.get("searchQuery").toString();
    }
}
