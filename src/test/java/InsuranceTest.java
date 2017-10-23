import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.Set;

public class InsuranceTest {
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
    public void testInsurance() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("//span[contains(text(),'Застраховать себя ')]")).click();
        driver.findElement(By.xpath("//*[contains(text(),'Страхование путешественников')]")).click();
        //driver.findElement(By.xpath("//div[contains(@class, 'col-md-7')]")).click();
        driver.findElement(By.xpath("//SPAN[@class=''][text()='Оформить онлайн']")).click();

        final Set<String> oldWindowsSet = driver.getWindowHandles();

        driver.findElement(By.xpath("//IMG[@src='/portalserver/content/atom/contentRepository/content/person/travel/banner-zashita-traveler.jpg?id=f6c836e1-5c5c-4367-b0d0-bbfb96be9c53']"))
                .click();

        String newWindowHandle = (new WebDriverWait(driver, 10))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );

        driver.switchTo().window(newWindowHandle);
        driver.findElement(By.xpath("//*[contains(text(),'Минимальная')]")).click();
        driver.findElement(By.xpath("//*[contains(text(),'Оформить')]")).click();

        fillField(By.xpath("//INPUT[@name='insured0_surname']/self::INPUT"), "Ivanov");
        fillField(By.xpath("//INPUT[@name='insured0_name']/self::INPUT"), "Ivan");
        fillField(By.xpath("//INPUT[@name='insured0_birthDate']/self::INPUT"), "15.12.1990");
        fillField(By.xpath("//INPUT[@name='surname']"), "Иванов");
        fillField(By.xpath("//INPUT[@name='name']/self::INPUT"), "Иван");
        fillField(By.xpath("//INPUT[@name='middlename']/self::INPUT"), "Иванович");
        fillField(By.xpath("//INPUT[@name='birthDate']/self::INPUT"), "15.12.1990");
        driver.findElement(By.xpath("//INPUT[@name='male']")).click();
        fillField(By.xpath("//INPUT[@ng-model='formdata.insurer.documentList[0].DOCSERIES']/self::INPUT"), "1234");
        fillField(By.xpath("//INPUT[@ng-model='formdata.insurer.documentList[0].DOCNUMBER']/self::INPUT"), "45678910");
        fillField(By.xpath("//INPUT[@name='issueDate']"), "11.10.2013");
        fillField(By.xpath("//TEXTAREA[@name='issuePlace']/self::TEXTAREA"), "qwertyuiop");

        assertEquals("Ivanov", driver.findElement(By.name("insured0_surname")).getAttribute("value"));
        assertEquals("Ivan", driver.findElement(By.name("insured0_name")).getAttribute("value"));
//        assertEquals("15.12.1990", driver.findElement(By.name("insured0_birthDate")).getAttribute("value"));
        assertEquals("Иванов", driver.findElement(By.name("surname")).getAttribute("value"));
        assertEquals("Иван", driver.findElement(By.name("name")).getAttribute("value"));
        assertEquals("Иванович", driver.findElement(By.name("middlename")).getAttribute("value"));
//        assertEquals("15.12.1990", driver.findElement(By.name("birthDate")).getAttribute("value"));
//        assertEquals("1234", driver.findElement(By.xpath("//INPUT[@ng-model='formdata.insurer.documentList[0].DOCSERIES']/self::INPUT")).getAttribute("value"));
//        assertEquals("45678910", driver.findElement(By.xpath("//INPUT[@ng-model='formdata.insurer.documentList[0].DOCNUMBER']/self::INPUT")).getAttribute("value"));
//        assertEquals("qwertyuiop", driver.findElement(By.xpath("//TEXTAREA[@name='issuePlace']/self::TEXTAREA")).getAttribute("value"));

        driver.findElement(By.xpath("//SPAN[@ng-click='save()']")).click();
        assertEquals("Заполнены не все обязательные поля", driver.findElement(By.xpath("//DIV[@ng-show='tryNext && myForm.$invalid']")).getText());
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
