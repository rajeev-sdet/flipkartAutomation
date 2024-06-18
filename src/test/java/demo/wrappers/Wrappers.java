package demo.wrappers;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
     WebDriver driver;
     Actions act;

     public Wrappers(WebDriver driver)
     {
        this.driver=driver;
     }

     public void NavigateToURL(String url)
     {
        driver.get(url);
     }

     public void EnterText(WebElement ele,String text)
     {
        ele.clear();
        ele.sendKeys(text);
     }

     public void PressEnterKey()
     {
        act = new Actions(driver);
        act.keyDown(Keys.ENTER);
        act.keyUp(Keys.ENTER);
        act.build().perform();
     }

     public void Wait(String text,WebElement ele)
     {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.or(ExpectedConditions.urlContains(text),ExpectedConditions.visibilityOf(ele)));
     }

     public void ClickTheElement(WebElement ele)
     {
        ele.click();
     }
}
