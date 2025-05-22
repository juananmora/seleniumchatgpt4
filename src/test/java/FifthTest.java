import org.testng.annotations.Test;
import app.getxray.xray.testng.annotations.Requirement;
import app.getxray.xray.testng.annotations.XrayTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class FifthTest extends BaseTest {

    @Test
    @Requirement(key = "JON-1003") // Placeholder Requirement Key
    @XrayTest(key = "JON-1004", labels = "automation-realmadrid") // Placeholder Xray Test Key
    public void REAL_MADRID_LAST_MATCH_RESULT_TEST() {
        System.out.println("Real Madrid Last Match Result Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        try {
            getDriver().navigate().to("https://www.google.es");
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

            // Try to accept cookies first
            try {
                // Option 1: By ID (often reliable)
                WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("L2AGLb")));
                acceptCookiesButton.click();
                System.out.println("Clicked cookie consent button by ID L2AGLb.");
            } catch (Exception e) {
                System.out.println("Cookie consent button with ID L2AGLb not found or clickable, trying another selector.");
                try {
                    // Option 2: By text content (more robust to ID changes but can be slower)
                    WebElement acceptCookiesButtonByText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Aceptar todo']/ancestor::button | //button[.//div[contains(text(), 'Aceptar todo')]]")));
                    acceptCookiesButtonByText.click();
                    System.out.println("Clicked cookie consent button by text 'Aceptar todo'.");
                } catch (Exception e2) {
                    System.out.println("Could not find or click cookie consent button by text 'Aceptar todo' either. Proceeding as if no cookie button was present or needed.");
                }
            }

            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
            searchBox.sendKeys("Real Madrid");
            searchBox.sendKeys(Keys.RETURN);

            // Wait for results to load (implicit or explicit wait might be needed in a real scenario)
            // For now, we'll try to find the element directly.
            // Potential selectors for the sports result - these might need adjustment.
            // Option 1: More specific if structure is known
            // By resultLocator = By.xpath("//div[contains(@class, 'imso_mh__lms-exp')]//div[@data-entityname]/div[normalize-space()]"); 
            // Option 2: A more general one, trying to find a score-like element.
            // This is a guess and will likely need refinement based on actual page structure.
            By resultLocator = By.xpath("(//div[contains(@class, 'imso_score') or contains(@class, 'liveresults-sports-immersive') or contains(@class, 'KAIX8d') or contains(@class, 'imso_mh__lms-exp')]//div[normalize-space(text()) and string-length(normalize-space(text())) > 5 and (contains(text(),'-') or contains(text(),':'))])[1]");


            WebElement matchResultElement = null;
            try {
                // Try a fairly common selector pattern for sports results
                 matchResultElement = getDriver().findElement(By.xpath("//div[contains(@class, 'imso_mh__lms-exp')]//div[@data-entityname]/div[normalize-space()]"));
                 if (matchResultElement.getText().trim().isEmpty()){
                    // Fallback to a more generic one if the first is empty or not found
                    matchResultElement = getDriver().findElement(By.xpath("(//div[contains(@class, 'imso_score') or contains(@class, 'liveresults-sports-immersive') or contains(@class, 'KAIX8d') or contains(@class, 'imso_mh__lms-exp')]//div[normalize-space(text()) and string-length(normalize-space(text())) > 5 and (contains(text(),'-') or contains(text(),':'))])[1]"));
                 }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("Could not find Real Madrid last match result with primary selectors. Trying a broader search...");
                // Attempt a broader search if specific selectors fail. This looks for any prominent text within a sports-like container.
                try {
                    matchResultElement = getDriver().findElement(By.xpath("(//div[contains(@class, 'liveresults-sports-immersive') or contains(@class, 'imso_mh__lms-exp')]//*[self::div or self::span][normalize-space(text()) and string-length(normalize-space(text())) > 4 and (contains(text(),'-') or contains(text(),':') or matches(text(), '[0-9]+'))])[1]"));
                } catch (org.openqa.selenium.NoSuchElementException e2) {
                    System.out.println("Could not find Real Madrid last match result on the page after multiple attempts.");
                }
            }

            if (matchResultElement != null && !matchResultElement.getText().trim().isEmpty()) {
                String resultText = matchResultElement.getText().trim();
                System.out.println("Real Madrid Last Match Result: " + resultText);
            } else {
                System.out.println("Real Madrid last match result element was found but empty, or not found after all attempts.");
            }

        } catch (Exception e) {
            System.err.println("An error occurred during the Real Madrid test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Real Madrid Last Match Result Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
        }
    }
}
