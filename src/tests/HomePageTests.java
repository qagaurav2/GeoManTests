package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steps.CommonSteps;
import steps.HomePageSteps;


public class HomePageTests {
	private WebDriver driver;
	private SoftAssert softAssert ;

	private HomePageSteps homePageSteps = new HomePageSteps();
	private CommonSteps commonSteps = new CommonSteps();
	private ArrayList<String> productTextOptions = new ArrayList<String>(Arrays.asList("Gas Range","Cooktop","Dishwasher","Washer","Dryer","Fridge"));


	@BeforeClass(alwaysRun=true)
	public void setUp() throws Exception {
		softAssert = new SoftAssert() ;
		driver = commonSteps.openURL();
		softAssert.assertAll();
	}

	@Test(priority = 1,description = "Verification Of All Links")
	public void linksVerificationOnHomePage() throws IOException  {
		softAssert = new SoftAssert();		
		homePageSteps.verificationOfAllLinks(driver);
		softAssert.assertAll();	
	}

	@Test(priority = 2,description = "Verify the Image Logo")
	public void imageLogoPresentOnHomePage() {
		softAssert = new SoftAssert();		
		homePageSteps.verificationImageLogo(driver,true, softAssert);
		softAssert.assertAll();	
	}	

	@Test(priority = 3, description = "Verify Phone Number")
	public void phoneNumberPresentOnHomePage() {
		softAssert = new SoftAssert();
		homePageSteps.phoneNumberVerification(driver, true, softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 4,description = "Select Value From Location Drop-down")
	public void selectValueLocationDropdown() {
		softAssert = new SoftAssert();
		homePageSteps.selectLocationDropdown(driver);
		softAssert.assertAll();
	}

	@Test(priority = 5,description = "Verify Brands Images and Products Text")
	public void verifyBrandsImages() throws IOException, InterruptedException {
		softAssert = new SoftAssert();
		//homePageSteps.verifyProductListText(driver, productTextOptions, softAssert);
		homePageSteps.clickSeeAllButton(driver);
		homePageSteps.checkBrokenImage(driver);
		softAssert.assertAll();	
	}

	@Test(priority = 6,description = "Verify Footer")
	public void verifyFooter() throws IOException, InterruptedException {
		softAssert = new SoftAssert();
		driver.navigate().back();
		homePageSteps.verifyProductLinksCount(driver, true, softAssert);
		homePageSteps.verifyBrandLinksCount(driver, true, softAssert);
		homePageSteps.verifySideLinksCount(driver, true, softAssert);
		softAssert.assertAll();	
	}
	
	@AfterClass(alwaysRun=true)
	public void classTeardown() {
		if(driver!=null) {
			driver.quit();
		}
	}

}
