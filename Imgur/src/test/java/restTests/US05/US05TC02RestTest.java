package restTests.US05;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import static rest.responses.BasicResponseModel.getProperty;
import static rest.responses.GetImageResponse.getPropertyFromData;

import java.io.IOException;

public class US05TC02RestTest extends ImgurClient {

    private JSONObject testData;
    private HttpResponse httpResponse;
    private String response;
    private String success;
    private String authorJson;
    private String author;
    private String galleryHash;
    private String commentID;

    @Test
    public void sendRequest() throws ParseException, IOException {

        prepareTestData();

        httpResponse = getGalleryCommentByID(galleryHash, commentID);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        success = getProperty(response, "success");
        authorJson = getPropertyFromData(response, "author");

        action.assertEqualsAndReport(success, "true", "Success field");
        action.assertEqualsAndReport(authorJson, author, "Author");

        Reporter.log("<h3>Get comment authorJson by commentID test PASSED</h3>");
    }

    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us05TestData");
        galleryHash = testData.get("galleryHash").toString();
        author = testData.get("author").toString();
        commentID = testData.get("commentID").toString();


    }

}
