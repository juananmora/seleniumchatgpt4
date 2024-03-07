import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.Reporter;
import org.testng.reporters.XMLReporter;
import org.testng.ITestResult;
import app.getxray.xray.testng.annotations.Requirement;
import app.getxray.xray.testng.annotations.XrayTest;

/**
 * Created by ONUR on 03.12.2016.
 */
public class SecondTest extends BaseTest {

    @Test
    @Requirement(key = "JON-2932")
    @XrayTest(key = "JON-2939", labels = "automation")
    public void GOOGLE4() {
        System.out.println("Google4 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google4 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: "+ Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google4 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

    @Test
    @Requirement(key = "JON-2941")
    @XrayTest(key = "JON-2942", labels = "nmil")
    public void YANDEX() {
        System.out.println("CTTI Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("https://ctti.gencat.cat/ca/inici");
        System.out.println("Yandex Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " + Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Inici. Centre de Telecomunicacions i Tecnologies de la Informació");
        System.out.println("CTTI Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }
}
