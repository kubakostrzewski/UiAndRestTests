package restTests.US07;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;
import rest.utils.RequestArguments;

import java.io.IOException;
import java.util.List;
import static rest.responses.GetGallerySearchResponse.getStringPropertyListFromData;

public class US07TC02RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String searchQuery;
    private List<Long> list;
    private JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();
        httpResponse = getGallerySearch(RequestArguments.SortBy.TIME.getArgument(),null,null,searchQuery);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        list = getStringPropertyListFromData(response, "datetime");
        for (int i = 0 ; i < list.size() - 1; i++){
            Assert.assertTrue(list.get(i) > list.get(i+1), "Search results are not sorted by time\n"
                    + list.get(i) + " < " + list.get(i+1));
            Reporter.log("Result no " + (i+1) + " datatime equals " + list.get(i) + " and is lower then result no "
                    + (i+2) + " datatime which equals " + list.get(i+1) + "<br>");
    }
        Reporter.log("<h3>Sort by time Test PASSED</h3>");
    }
    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us07TestData");
        searchQuery = testData.get("searchQuery").toString();
    }
}
