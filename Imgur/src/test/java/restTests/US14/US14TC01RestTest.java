package restTests.US14;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;
import static rest.responses.BasicResponseModel.*;

public class US14TC01RestTest extends ImgurClient {

    private JSONObject testData;
    private HttpResponse httpResponse;
    private String response;
    private String imageID;
    private String comment;
    private String success;
    private String accountName;
    private String commentSort;
    private String newCommentID;
    private String newImageID;

    @Test
    public void sendRequest() throws ParseException, IOException {

        prepareTestData();

        //get account comments
        httpResponse = getAccountComments(accountName, commentSort);
        response = EntityUtils.toString(httpResponse.getEntity());
        int beforePosting = getDataArraySize(response);

        //post new comment
        httpResponse = postCommentCreation(imageID, comment);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        success = getProperty(response, "success");
        action.assertEqualsAndReport(success, "true", "Success field");

        //get new comment ID and its image ID
        httpResponse = getAccountComments(accountName, commentSort);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        success = getProperty(response, "success");
        action.assertEqualsAndReport(success, "true", "Success field");
        int afterPosting = getDataArraySize(response);
        newCommentID = getPropertyFromDataArray(response, "id");
        newImageID = getPropertyFromDataArray(response, "image_id");
        action.assertEqualsAndReport(newImageID, imageID, "Image ID equality");
        boolean addOne = ((afterPosting-beforePosting)==1) ? true : false;
        Assert.assertTrue(addOne, "Comment size difference is not equal to 1\n");
        Reporter.log("Number of comments before posting a reply: " + beforePosting + "\n" +
                "<br>Number of comments after posting a reply: " + afterPosting + "\n" +
                "<br>Difference: 1<br>");

        //delete comment
        httpResponse = deleteCommentDeletion(accountName, newCommentID);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        success = getProperty(response, "success");
        action.assertEqualsAndReport(success, "true", "Success field");

        //check if new comment was deleted
        httpResponse = getComment(newCommentID);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 404, "Status code");
        action.assertEqualsAndReport(success, "false", "Success field");

        httpResponse = getAccountComments(accountName, commentSort);
        response = EntityUtils.toString(httpResponse.getEntity());
        int afterDeleting = getDataArraySize(response);
        action.assertEqualsAndReport(beforePosting, afterDeleting, "Account comments equality");

        Reporter.log("<h3>Post a comment to an image and delete it test PASSED");


    }

    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us14TestData");
        imageID = testData.get("imageID").toString();
        comment = testData.get("comment").toString();
        accountName = testData.get("account_username").toString();
        commentSort = testData.get("commentSort").toString();

    }
}
