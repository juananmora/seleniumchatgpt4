import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.Reporter;
import org.testng.reporters.XMLReporter;
import org.testng.ITestResult;
import com.xpand.annotations.Xray;

/**
 * Created by ONUR on 03.12.2016.
 */
public class FirstTest extends BaseTest {

    @Test
    public void GOOGLE1() {
        System.out.println("Google1 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google1 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google1 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
        ITestResult result = Reporter.getCurrentTestResult();
        result.setAttribute("requirement", "JON-2911");   // Xray will try to create a link to this requirement issue
        //result.setAttribute("test", "CALC-2");             // Xray will try to find this Test issue and report result against it
        result.setAttribute("labels", "core addition");    // Xray will add this(ese) label(s
    }

    @Test
    public void GOOGLE2() {
        System.out.println("Google2 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google2 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google2 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

    @Test
    public void GOOGLE3() {
        System.out.println("Google3 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google3 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google3 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

    @Test
    public void GOOGLE5() {
        System.out.println("Google5 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google5 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google5 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

    @Test
    public void GOOGLE6() {
        System.out.println("Google6 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google6 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google6 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

    @Test
    public void GOOGLE7() {
        System.out.println("Google7 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google7 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google7 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

    @Test
    public void GOOGLE8() {
        System.out.println("Google8 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google8 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google8 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

    @Test
    public void GOOGLE9() {
        System.out.println("Google9 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google9 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google9 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }
}
