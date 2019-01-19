package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonAPI {

    public static WebDriver driver =null;

    @BeforeMethod
    public void initializeTest(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Zack\\IdeaProjects\\TechnicalA\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
//        String getOsNameFromSystem = System.getProperty("os.name");
//        System.out.println("Opening the Browser chrome");
//        if (getOsNameFromSystem.contains("mac")){
//            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Zack\\IdeaProjects\\TechnicalA\\driver\\chromedriver.exe");
//        } else if (getOsNameFromSystem.contains("windows")){
//            System.setProperty("driver.chrome.driver", "C:\\Users\\Zack\\IdeaProjects\\TechnicalA\\driver\\chromedriver.exe");
//        }
//        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
        //driver.manage().window().maximize(); // driver.manage().window().fullscreen();
        driver.get("https://www.makemysushi.com/");
    }
    @AfterMethod
    public void tearDownTest(){
        System.out.println("Closing the Browser Chrome");
        driver.close(); // driver.quit();
    }
    public void brokenLink() throws IOException {
        //Step1: Get the list of all images and links
        List<WebElement> linksList = driver.findElements(By.tagName("a"));
        linksList.addAll(driver.findElements(By.tagName("img")));

        System.out.println("Total number of links and images >>>>>>>"+linksList.size());

        List<WebElement> activeLinks = new ArrayList<WebElement>();

        //Step 2 : Iterate LinkList exclude all links/images which does not have href attribute

        for (int i =0; i<linksList.size(); i++){
            System.out.println(linksList.get(i).getAttribute("href"));

            if(linksList.get(i).getAttribute("href")!=null&&(! linksList.get(i).getAttribute("href").contains("javascript")&& (! linksList.get(i).getAttribute("href").contains("mailto")))){
            activeLinks.add(linksList.get(i));

        }
        System.out.println("Total number of active links and images >>>>>>>"+activeLinks.size());

        //Step 3 : Check the href url with http connection api
        for(int j =0; j<activeLinks.size(); j++){
            HttpURLConnection connection=(HttpURLConnection)new URL(activeLinks.get(j).getAttribute("href")).openConnection();
            connection.connect();
           String response = connection.getResponseMessage();
           connection.disconnect();
            System.out.println(activeLinks.get(j).getAttribute("href")+"----->>>>>> :):):):)"+ response);
        }
    }

}
}
