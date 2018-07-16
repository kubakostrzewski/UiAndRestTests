package restTests.US17;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;

import static rest.responses.BasicResponseModel.getProperty;
import static rest.responses.GetTagInfoResponse.*;

public class US17TC01RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    public String clientId, name, displayName, backgroundHash, accent, success;
    public JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();
        httpResponse = getGalleryTagInfo(name);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(),200,
                "Status code");
        action.logResponse(httpResponse, response);

        action.assertEqualsAndReport(getName(response), testData.get("name"),"Name");
        action.assertEqualsAndReport(getDisplayName(response), testData.get("displayName"),"Display name");
        action.assertEqualsAndReport(getBackgroundHash(response), testData.get("backgroundHash"),
                "Background hash");
        action.assertEqualsAndReport(getAccent(response), testData.get("accent"),"Accent");
        success = getProperty(response, "success");
        action.assertEqualsAndReport(success, "true", "Success field");

        int followersCount = Integer.parseInt(getFollowersCount(response));
        Reporter.log("Number of \'" + name + "\' tag followers: " + "<b>" + followersCount + "</b>");
        Reporter.log("<h3>Get Tag Info Test PASSED</h3>");
    }

    public void prepareTestData() throws ParseException, IOException {
        testData = manager.getTestCaseData("us17TestData");
        name = testData.get("name").toString();
        displayName = testData.get("displayName").toString();
        backgroundHash = testData.get("backgroundHash").toString();
        accent = testData.get("accent").toString();
    }
}
