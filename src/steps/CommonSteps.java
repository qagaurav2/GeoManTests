package steps;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import config.ApplicationData;
import driverfactory.DriverFactory;
import utilities.SeleniumMethods;

public class CommonSteps {

	private DriverFactory driverFactory = new DriverFactory();
	private ApplicationData applicationData = new ApplicationData();

	public WebDriver openURL() throws InterruptedException {
		WebDriver driver = driverFactory.createDriver();
		driver.get(applicationData.getUrl());
		return driver;
	}

}
