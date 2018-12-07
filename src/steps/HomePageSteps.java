package steps;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import pages.HomePage;

public class HomePageSteps {

	HomePage homePage;

	public void verificationOfAllLinks(WebDriver driver) throws IOException {
		homePage = new HomePage(driver);
		homePage.verifyAllLinksStatus(driver);
	}

	public void verificationImageLogo(WebDriver driver,boolean expected, SoftAssert softAssert) {
		homePage = new HomePage(driver);
		boolean actualStatus = homePage.isImageLogoPresent(driver);
		softAssert.assertEquals(actualStatus, expected,"Image not found on home page");

	}

	public void selectLocationDropdown(WebDriver driver) {
		homePage = new HomePage(driver);	
		homePage.selectValueInLocationDropDown(driver);		
	}

	public void verificationofLocationDropdown(WebDriver driver,boolean expected,SoftAssert softAssert) {
		homePage = new HomePage(driver);
		boolean actualStatus=homePage.isLocationDropdownPresent(driver);
		softAssert.assertEquals(actualStatus, expected,"Dropdown is not Present on home Page");
	}

	public void phoneNumberVerification(WebDriver driver,boolean expected,SoftAssert softAssert) {		
		homePage = new HomePage(driver);
		boolean actualStatus= homePage.isPhoneNumberPresent(driver);
		softAssert.assertEquals(actualStatus, expected,"Phone Number does not exist on Page");
	}

//	public void verifyProductListText(WebDriver driver,List<String> expectedOptions,SoftAssert softAssert) throws IOException {
//		homePage = new HomePage(driver);
//		List<String> actualOptions = homePage.verifyProductsText(driver);
//		softAssert.assertEquals(actualOptions, expectedOptions,"All elements not found in product list");
//	}
	
	public void clickSeeAllButton(WebDriver driver) {		
		homePage = new HomePage(driver);
		homePage.clickOnSeeAllBtn(driver);		
	}

	public void checkBrokenImage(WebDriver driver) {		
		homePage = new HomePage(driver);
		homePage.verifyBrokenImages(driver);
	}

	public void verifyProductLinksCount(WebDriver driver,boolean expectedStatus,SoftAssert softAssert) {
		homePage = new HomePage(driver);
		boolean actualStatus = homePage.getProductsLinksStatus(driver);
		softAssert.assertEquals(actualStatus, expectedStatus,"Product Links Not Found");
	}

	public void verifyBrandLinksCount(WebDriver driver,boolean expectedStatus,SoftAssert softAssert) {
		homePage = new HomePage(driver);
		boolean actualStatus = homePage.getBrandsLinksStatus(driver);
		softAssert.assertEquals(actualStatus, expectedStatus,"Brand Links Not Found");
	}

	public void verifySideLinksCount(WebDriver driver,boolean expectedStatus,SoftAssert softAssert) {
		homePage = new HomePage(driver);
		boolean actualStatus = homePage.getSideLinksStatus(driver);
		softAssert.assertEquals(actualStatus, expectedStatus,"Side Menu Links Not Found");
	}
}
