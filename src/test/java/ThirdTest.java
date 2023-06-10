import org.testng.Assert;
import org.testng.annotations.Test;


public class ThirdTest extends BaseTest {

    
    @Test
    public void CTTI() {
        System.out.println("CTTI Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("https://ctti.gencat.cat/ca/inici");
        System.out.println("CTTI Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " + Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Inici. Centre de Telecomunicacions i Tecnologies de la Informació");
        System.out.println("CTTI Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }
}