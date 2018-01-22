package CodeChallenge.MobileChallenge;

import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class MobileChallenge {
	private AndroidDriver driver;
	private Properties properties;

// Test Step 1: Login to app -> verify user has been login to app -> navigae back to main activity	
  @Test (priority = 1)
  public void test1() throws IOException {
	
	driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS); 
	
	  properties = readProperties();
	  String email = properties.getProperty("email");
	  String password = properties.getProperty("password");

	  //take a web element list using accesibility ID of navigate up and click on first element
	  List<WebElement> li = driver.findElementsByAccessibilityId("Navigate up");
	  li.get(0).click();
	
	  //click on login --> the only locator found for the login is xpath
	  driver.findElement(By.id("tv_login")).click();
	  //driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[2]")).click();
	  
	 //enter username and password and login
	  driver.findElement(By.id("et_ldap_login_username")).sendKeys(email);
	  driver.findElement(By.id("et_ldap_login_password")).sendKeys(password);
	  driver.findElement(By.id("btn_ldap_login_continue")).click();
  
	  //check whether user has logged in successfully
	  li.get(0).click();
	  WebElement loginName = driver.findElement(By.id("tv_login_name"));
	  if (loginName.getText().equals(email)) {
		  System.out.println("User has been logged in succesfully"); 
	  }
	  else {
		  System.out.println("User not logged in");
	  }
	  
	  //Navigate again to main activity
	  driver.findElement(By.id("iv_logo")).click();	  
  }
  
//Method to verify image presence
  public boolean verifyImagePresence(WebElement imageElement) {
	  // this method can be used to verify image presence
	  Boolean ImagePresent = false;
	  Dimension size = imageElement.getSize();
	  System.out.println(size);
	  if(size.height > 0 && size.width > 0)
	  {
		  ImagePresent = true;
		  return ImagePresent;
	  }
	  else {
		  return ImagePresent;
	  }
	  
  }
  
  /*Test Step 2: wait until latest link appear -> navigate to latest link -> 
   *-> click on main article ->verify image presence -> verify video presence 
   */
  @Test (priority = 2)
  public void test2() {	
	  //wait until the latest link appear
	  String xpath_latestLink = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.support.v7.app.ActionBar.Tab[3]/android.widget.LinearLayout/android.widget.TextView";
	  
	  WebDriverWait wait = new WebDriverWait(driver,20);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_latestLink)));

	  //click on latest link 
	  driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.support.v7.app.ActionBar.Tab[3]/android.widget.LinearLayout/android.widget.TextView")).click();
	  WebElement e = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.support.v4.view.ViewPager/android.widget.RelativeLayout/android.view.ViewGroup/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView"));
	 
	  //verify article image presence
	  WebElement imageElement = driver.findElement(By.id("article_image"));
	  
	  if (imageElement !=null) {
		  System.out.println("Image Available");
	  }
	  else {
		  System.out.println("Image not Available");
	  }
	  
	  //verify video presence
	  WebElement videoPlayButton = driver.findElement(By.id("iv_video_play"));
	  
	  if (videoPlayButton !=null) {
		  System.out.println("Video Available");
	  }
	  else {
		  System.out.println("Video not Available");
	  }
	  
  }
  
  //launch app 
  @BeforeTest
  public void beforeTest() {
	  //Setup desired capabilities, pass appActivity and appPackage to appium
	  DesiredCapabilities capabilities = new DesiredCapabilities();
	  
	  capabilities.setCapability( "platformName", "android");
	  capabilities.setCapability( "deviceName", "emulator-5554");
	  capabilities.setCapability( "appActivity", "com.sph.straitstimes.views.activities.SplashActivity");
	  capabilities.setCapability( "appPackage", "com.buuuk.st");
	  capabilities.setCapability( "noReset", true);
	  
		//Instantiate Appium Driver
		try {
			 driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);		
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
  }
  
  // Reads the test case properties from properties file
  public Properties readProperties() throws IOException {
	  //this method can be used to read test condition values from config.properties file
	  properties = new Properties();
	  FileInputStream ip = new FileInputStream("/Users/nilmi/Documents/SPH/WebChallenge/src/test/java/CodeChallenge/WebChallenge/config.properties");
	  properties.load(ip);
	  return properties;
  }

  //this section executes after test
  @AfterTest
  public void afterTest() {
	  //quit driver after test
	  driver.quit();
  }

}
