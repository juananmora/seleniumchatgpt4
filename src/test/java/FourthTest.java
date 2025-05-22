import org.testng.Assert;
import org.testng.annotations.Test;
import app.getxray.xray.testng.annotations.Requirement;
import app.getxray.xray.testng.annotations.XrayTest;

public class FourthTest extends BaseTest {

    @Test
    @Requirement(key = "JON-1001") // Placeholder Requirement Key
    @XrayTest(key = "JON-1002", labels = "automation-new") // Placeholder Xray Test Key
    public void GOOGLE_IMAGES_TEST() {
        System.out.println("Google Images Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("https://www.google.com/imghp");
        String pageTitle = getDriver().getTitle();
        System.out.println("Google Images Test's Page title is: " + pageTitle +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(pageTitle, "Google Images");
        System.out.println("Google Images Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }
}
