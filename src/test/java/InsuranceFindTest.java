import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.util.concurrent.TimeUnit;

public class InsuranceFindTest {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception{
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");

        driver=new ChromeDriver();
        baseUrl="http://www.sberbank.ru/ru/person";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testInsuranceFind() throws Exception{
        driver.get(baseUrl + "/");

        /*Значок поиска не активен. Выбор города по нажатию тоже не работает:
        driver.findElement(By.xpath("//SPAN[@class='region-list__arrow']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Нижегородская область')]")).click();
        assertEquals("Нижегородская область",
                driver.findElement(By.xpath("//SPAN[@class='region-list__name']")).getText());*/

        ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);",  driver.findElement(By.xpath("//*[contains(@class,'footer-information')]")));
        assertTrue("Значки социальных сетей присуствуют",driver.findElement(By.xpath("//div[contains(@class,'social__wrapper')]")).isEnabled());

    }

    @After
    public void tearDown() throws Exception{
        driver.quit();
    }

    private void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
}
