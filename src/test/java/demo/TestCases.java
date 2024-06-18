package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers Perform;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    // testCase01: Go to www.flipkart.com. Search "Washing Machine". Sort by
    // popularity and print the count of items with rating less than or equal to 4
    // stars.
    @Test(priority = 0, enabled = true)
    public void testCase01() {
        Perform.NavigateToURL("https://www.flipkart.com/");
        WebElement search_box = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        Perform.EnterText(search_box, "Washing Machine");
        Perform.PressEnterKey();
        Perform.Wait("q=Washing%20Machine", null);
        WebElement popularity_loc = driver.findElement(By.xpath("//div[text()='Popularity']"));
        Perform.ClickTheElement(popularity_loc);
        Perform.Wait("popularity", null);
        List<WebElement> rating_loc = driver
                .findElements(By.xpath("//div[@class='col col-7-12']//descendant::div[@class='XQDdHH']"));
        int count = 0;
        String ratinginString = "";
        for (int i = 0; i < rating_loc.size(); i++) {
            ratinginString = rating_loc.get(i).getText().toString();
            if (Double.valueOf(ratinginString) <= 4.0)
                count = count + 1;
        }
        System.out.println("the count of items with rating less than or equal to 4 stars= " + count);
    }

    // testCase02: Search "iPhone", print the Titles and discount % of items with
    // more than 17% discount
    @Test(priority = 1, enabled = true)
    public void testCase02() {
        Perform.NavigateToURL("https://www.flipkart.com/");
        WebElement search_box = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        Perform.EnterText(search_box, "iPhone");
        Perform.PressEnterKey();

        List<WebElement> discounts_loc = driver
                .findElements(By.xpath("//div[@class='tUxRFH']"));

        int discount = 0;
        String item_title = "";
        WebElement element;

        for (int i = 0; i < discounts_loc.size(); i++) {
            element = discounts_loc.get(i).findElement(By.xpath(".//div[@class='UkUFwK']"));
            discount = Integer.valueOf(element.getText().substring(0, 2));
            if (discount > 17) {
                item_title = discounts_loc.get(i).findElement(By.xpath(".//div[@class='KzDlHZ']")).getText();
                System.out.println(item_title + "  |  Discount of this item is = " + discount + "%");
            }
        }
    }

    // testCase03: Search "Coffee Mug", select 4 stars and above, and print the
    // Title and image URL of the 5 items with highest number of reviews
    @Test(priority = 2, enabled = true)
    public void testCase03() {
        Perform.NavigateToURL("https://www.flipkart.com/");
        WebElement search_box = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        Perform.EnterText(search_box, "Coffee Mug");
        Perform.PressEnterKey();
        WebElement filter_loc = driver.findElement(By.xpath("//div[text()='4★ & above']/../div[@class='XqNaEv']"));
        Perform.ClickTheElement(filter_loc);
        List<WebElement> reviews_loc = driver.findElements(By.xpath("//div[@class='slAVV4']"));
        HashMap<Integer, String> hm1 = new HashMap<Integer, String>();
        HashMap<Integer, String> hm2 = new HashMap<Integer, String>();
        String title = "";
        String image_URL = "";
        String s1 = "";
        String s2 = "";
        String s3 = "";
        int j = 0;
        for (int i = 0; i < reviews_loc.size(); i++) {
            j = i + 1;
            s1 = driver.findElement(By.xpath("(//div[@class='slAVV4']//span[@class='Wphh3N'])[" + j + "]")).getText();
            s2 = s1.replace(",", "");
            s3 = s2.substring(1, s2.length() - 1);

            title = driver.findElement(By.xpath(
                    "(//div[@class='slAVV4']//span[@class='Wphh3N'])[" + j + "]/../../a[2]"))
                    .getAttribute("title");

            image_URL = driver.findElement(By.xpath(
                    "(//div[@class='slAVV4']//span[@class='Wphh3N'])[" + j + "]/../../a/div/div/div/img"))
                    .getAttribute("src");

            hm1.put(Integer.valueOf(s3), title);
            hm2.put(Integer.valueOf(s3), image_URL);
        }
        Collection<Integer> reviews = hm1.keySet();
        Object[] reviews_array = reviews.toArray();
        Arrays.sort(reviews_array);
        int len = reviews_array.length;
        System.out.println("The Title and image URL of the 5 items with highest number of reviews");
        for (int i = 1; i <= 5; i++) {
            System.out.println("TITLE= " + hm1.get(reviews_array[len - i]));
            System.out.println("IMAGE URL= " + hm2.get(reviews_array[len - i]));
        }

    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        Perform = new Wrappers(driver);
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}