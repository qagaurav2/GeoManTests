package driverfactory;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import config.ApplicationData;
import pages.BasePage;

public class DriverFactory {

	private static String detectedOS = null;
	public static WebDriver webdriver = null;
	ApplicationData applicationData = new ApplicationData();

	BasePage obj = new BasePage();
	public static String getOperatingSystemType() {

		String OS = System.getProperty("os.name", "generic").toLowerCase();
		if ((OS.indexOf("mac") >= 0)) {
			detectedOS = "MacOS";
		} else if (OS.indexOf("win") >= 0) {
			detectedOS = "Windows";
		} else if (OS.indexOf("nux") >= 0) {
			detectedOS = "Linux";
		} else {
			detectedOS = "Other";
		}

		return detectedOS;
	}


	public WebDriver createDriver() {
		String osType = getOperatingSystemType();
		String browserName = applicationData.getBrowserType();
		if(browserName.equalsIgnoreCase("firefox") || browserName.equalsIgnoreCase("ff")){

			if (osType.equals("Linux")) {
				System.setProperty("webdriver.chrome.driver", "src/../libs/linux/geckodriver");
			} else if (osType.equals("MacOS")) {
				System.setProperty("webdriver.chrome.driver", "src/../libs//mac/geckodriver");
			} else {
				System.setProperty("webdriver.gecko.driver", "src/../libs/win/geckodriver.exe");
			}
			webdriver = new FirefoxDriver();
			webdriver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("ch")){
			if (osType.equals("Linux")) {
				System.setProperty("webdriver.chrome.driver", "src/../libs/linux/chromedriver");
			} else if (osType.equals("MacOS")) {
				System.setProperty("webdriver.chrome.driver", "src/../libs/mac/chromedriver");
			} else {
				System.setProperty("webdriver.chrome.driver", "src/../libs/win/chromedriver.exe");
			}
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("use-fake-ui-for-media-stream");

			String destFolder = System.getProperty("user.dir");
			String destDir = destFolder +File.separator+"downloadedFiles"+File.separator;

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 1);
			chromePrefs.put("download.default_directory", destDir);
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setAcceptInsecureCerts(true);
			//cap.set
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			//WebDriver driver = new ChromeDriver(cap);
			//WebDriver driver = new ChromeDriver(options);
			webdriver = new ChromeDriver(cap);
			webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			webdriver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("internet explorer") || browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", "src/../libs/IEDriverServer.exe");
			webdriver = new InternetExplorerDriver();
			webdriver.manage().window().maximize();
		}
		else if(browserName.equalsIgnoreCase("BrowserStack") || browserName.equalsIgnoreCase("bs"))
		{
			DesiredCapabilities caps = new DesiredCapabilities();

			caps.setCapability("browser", "Chrome");
			caps.setCapability("browser_version", "62.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "10");

			caps.setCapability("browserstack.debug", "true");

		}
		else if(browserName.equalsIgnoreCase("edge") || browserName.equalsIgnoreCase("Microsoft edge"))
		{
			System.setProperty("webdriver.edge.driver","src/../libs/MicrosoftWebDriver.exe");
			webdriver=new EdgeDriver();
			webdriver.manage().window().maximize();
		}

		else if(browserName.equalsIgnoreCase("safari") || browserName.equalsIgnoreCase("mac safari"))
		{
			webdriver=new SafariDriver();
			webdriver.manage().window().maximize();
		}
		return webdriver;
	}

	public WebDriver getCurrentWebDriver(){
		if(webdriver==null)
			webdriver=createDriver();
		return webdriver;
	}

}