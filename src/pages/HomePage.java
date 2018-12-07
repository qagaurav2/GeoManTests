package pages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage extends BasePage{

	private int invalidImageCount;

	public HomePage(WebDriver driver) {		 
		PageFactory.initElements(driver, this);		
	}

	//	@FindBy(how = How.XPATH, using = "//tbody/tr")
	//	private List<WebElement> allLinksElement;

	@FindBy(how = How.XPATH, using="//div[@class='header__logo']/img")
	private WebElement imgLogoElement;

	@FindBy(how = How.XPATH, using="//div[@class='header__contacts']//select")
	private WebElement locationDropdownElement;

	@FindBy(how = How.XPATH, using="//div[@class='header__support']/a")
	private WebElement phoneNoElement;

	@FindBy(how = How.XPATH, using="//a[@class='appliances__item']//h2")
	private List<WebElement> listOfProductsTextElements;

	@FindBy(how = How.XPATH, using="//div[@id='brands-list-container']//img")
	private List<WebElement> brandImages;

	@FindBy(how = How.XPATH, using="//div[@class='footer__nav-brands-item'][1]//a")
	private List<WebElement> productFooterLinks;

	@FindBy(how = How.XPATH, using="//div[@class='footer__nav-brands-item'][2]//a")
	private List<WebElement> brandsFooterLinks;

	@FindBy(how = How.XPATH, using="//ul[@class='footer__nav-list']//a")
	private List<WebElement> sideFooterLinks;


	@FindBy(how = How.XPATH, using="//div[@class='swiper-scrollbar-drag']")
	private WebElement scrollBarElement;

	@FindBy(how = How.XPATH, using="//div[@class='swiper-scrollbar']")
	private WebElement scrollWiperElement;

	@FindBy(how = How.XPATH, using="//span[text() = ' See All']")
	private WebElement seeAllBtnElement;


	public void verifyAllLinksStatus(WebDriver driver) throws IOException {

		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total links are "+links.size()); 

		for(int i=0; i<links.size(); i++) {
			WebElement element = links.get(i);
			String url=element.getAttribute("href");	
			if(url!=null) {
				if(!url.contains("tel:+")) {
					verifyLink(url); 
				}
			} else {
				System.out.println("Value of I counter for non href with tag a" + i);
				System.out.println(element.getText());
			}
		} 
	}

//	public List<String> verifyProductsText(WebDriver driver) throws IOException {
//		List<WebElement> productsText = listOfProductsTextElements;
//		List<String> options = new ArrayList<String>();
//		scrollToElement(driver, scrollBarElement);	
//		for(WebElement productText : productsText) {		
//			String text = getText(driver, productText);
//			if(text.contains("WASHER") || text.contains("DRYER")) 		
//				click(driver, scrollWiperElement);
//			options.add(getText(driver, productText));
//
//			System.out.println(options);
//		}
//		return options;	
//	}

	public static void verifyLink(String urlLink) {
		try {
			URL link = new URL(urlLink);
			HttpURLConnection httpConn =(HttpURLConnection)link.openConnection();
			httpConn.setConnectTimeout(2000);
			httpConn.connect();
			if(httpConn.getResponseCode()== 200) { 
				System.out.println(urlLink+" - "+httpConn.getResponseMessage());
			}
			if(httpConn.getResponseCode()== 404) {
				System.out.println(urlLink+" - "+httpConn.getResponseMessage());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public void verifyBrokenImages(WebDriver driver) {
		try {
			invalidImageCount = 0;
			List<WebElement> imagesList = brandImages;
			System.out.println("Total no. of images are " + imagesList.size());
			for (WebElement imgElement : imagesList) {
				if (imgElement != null) {
					verifyimageActive(imgElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void verifyimageActive(WebElement imgElement) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("Broken Image Found");
			}
			invalidImageCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getProductsLinksStatus(WebDriver driver) {
		int productLinksCount = productFooterLinks.size();
		if(productLinksCount >= 1) {
			return true;
		}
		return false;
	}

	public boolean getBrandsLinksStatus(WebDriver driver) {
		int productLinksCount = brandsFooterLinks.size();
		if(productLinksCount >= 1) {
			return true;
		}
		return false;
	}

	public boolean getSideLinksStatus(WebDriver driver) {
		int productLinksCount = sideFooterLinks.size();
		if(productLinksCount >= 1) {
			return true;
		}
		return false;
	}



	public boolean isImageLogoPresent(WebDriver driver) {
		return isElementDisplayed(driver, imgLogoElement);	
	}

	public boolean isLocationDropdownPresent(WebDriver driver) {
		return isElementDisplayed(driver, locationDropdownElement);		
	}

	public boolean isPhoneNumberPresent(WebDriver driver) {
		return isElementDisplayed(driver, phoneNoElement);		
	}

	public void selectValueInLocationDropDown(WebDriver driver) {		
		selectDropdownOptionByValue(driver, locationDropdownElement, "1776");
	}

	public void clickOnSeeAllBtn(WebDriver driver) {
		click(driver, seeAllBtnElement);
	}
} 
