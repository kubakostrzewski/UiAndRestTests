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

public class US07TC03RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String indicesQuery1,indicesQuery2;
    private List<String> list;
    private JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();
        httpResponse = getGallerySearch(null,null,null,
                indicesQuery1 + "+AND+" + indicesQuery2);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        list = getStringPropertyListFromData(response, "title");
        for (String title:list){
            Assert.assertTrue(title.toLowerCase().contains(indicesQuery1),
                    "Title: " + title + "\n don't contains \"" + indicesQuery1 + "\"\n" );
            Reporter.log("Title: " + title + "<br>contains \"" + indicesQuery1 +"\"<br>");
            Assert.assertTrue(title.toLowerCase().contains(indicesQuery2),
                    "Title: " + title + "\n don't contains \"" + indicesQuery2 + "\"\n" );
            Reporter.log("Title: " + title + "<br>contains \"" + indicesQuery2 + "\"<br><br>");
        }
        Reporter.log("<h3>Indices Test PASSED</h3>");
    }
    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us07TestData");
        indicesQuery1 = testData.get("indicesQuery1").toString();
        indicesQuery2 = testData.get("indicesQuery2").toString();

    }
}
