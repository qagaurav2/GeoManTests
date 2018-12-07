package pages;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BasePage {

	private By pageLoaderLocator = By.cssSelector(".fa-refresh");
	private By toastLocator = By.cssSelector(".md-toast-content");
	private By currentDateLocator = By.xpath("//td[@class='md-calendar-date md-calendar-date-today md-focus']");    
	private By companySearchFieldLocator = By.xpath("//md-autocomplete[@md-items='item in companies']//input");
	private By unitSearchFieldLocator = By.xpath("//md-autocomplete[@id='company']//input");
	private By patientSearchFieldLocator = By.xpath("//md-autocomplete[@md-selected-item='$parent.selectedPatient']//input");
	private By casesSearchFieldLocator = By.xpath("//md-autocomplete[@md-selected-item='selectedCase']//input");
	private By agentUserSearchFieldLocator = By.xpath("//md-autocomplete[@md-search-text='searchTextAgent']//input");
	private By searchedListLocator = By.xpath("//span[contains(@md-highlight-text,'searchText')]");
	private By searchedListLocator2 = By.xpath("//span[contains(@md-highlight-text,'searchText')]/span");
	private By tableHeadersLocator = By.xpath("//table//th");
	private By tableRowsLocator = By.xpath("//table/tbody/tr");
	private By interventionsTableRowsLocator = By.xpath("//table[@ng-if='interventionsArray && !viewMode']/tbody/tr");
	private By radioBtnLocator = By.tagName("md-radio-button");
	private By checkboxLocator = By.tagName("md-checkbox");
	public By fromDateFieldLocator = By.xpath("//md-datepicker[@ng-model='dateFrom']//input");
	public By toDateFieldLocator = By.xpath("//md-datepicker[@ng-model='dateTo']//input");
	public By dateFromCalenderAfterCurrentDate = By.xpath("//td[@class='md-calendar-date md-calendar-date-today md-focus']/following-sibling::td[1]");

	// Application common methods

	/**
	 * This method use to get text from toast visible on application
	 * @param driver
	 * @return message in string
	 */
	public String getToastMessage(WebDriver driver) {
		waitForElementVisibility(driver, toastLocator);
		WebElement toastMessageElement = driver.findElement(toastLocator);
		return getText(driver, toastMessageElement);
	}

	/**
	 * This method use to select date of current month from calendar
	 * @param driver
	 * @param date in single digit, 9,4,5 ..
	 */
	public void selectDateFromCalenderOfCurrentMonth(WebDriver driver) {	
		driver.findElement(currentDateLocator).click();
	}

	public String getClassAttributeOfDate(WebDriver driver) {
		String getClassAtt = driver.findElement(dateFromCalenderAfterCurrentDate).getAttribute("class");
		return getClassAtt;
	}


	//	public void selectDateFromCalenderOfCurrentMonth(WebDriver driver, String value) {		
	//		String selectedDateId = driver.findElement(currentDateLocator).getAttribute("id");
	//		String date = selectedDateId.substring(0,18);
	//		String dateId = date+value;
	//		WebElement dateElement = driver.findElement(By.cssSelector("#"+dateId+" span"));
	//		click(driver, dateElement);
	//		pause(100);
	//	}

	/**
	 * This method use to wait until loader getting disappeared 
	 * @param driver
	 */
	public void waitUntilLoaderDisappeared(WebDriver driver) {
		waitForPageLoad(driver);
		waitForElementInvisibility(driver, pageLoaderLocator);
		pause(1000);
	}

	/**
	 * This method use to enter company code in Company search field
	 * @param driver
	 * @param value
	 */
	public void enterCompanyInSearchField(WebDriver driver,String value) {
		List<WebElement> companyFieldElements = driver.findElements(companySearchFieldLocator);
		WebElement companyFieldElement = null;
		if(companyFieldElements.size()==0) {
			companyFieldElement = driver.findElement(unitSearchFieldLocator);
		}
		else {
			companyFieldElement = driver.findElement(companySearchFieldLocator);
		}
		sendKeys(driver, companyFieldElement, value);
	}

	/**
	 * This method use to enter patient code in Patient search field
	 * @param driver
	 * @param value
	 */
	public void enterPatientInSearchField(WebDriver driver,String value) {
		WebElement patientFieldElement = driver.findElement(patientSearchFieldLocator);
		sendKeys(driver, patientFieldElement, value);
	}


	/**
	 * This method use to enter cases name in cases search field
	 * @param driver
	 * @param value
	 */
	public void enterCasesInSearchField(WebDriver driver,String value) {
		WebElement casesFieldElement = driver.findElement(casesSearchFieldLocator);
		sendKeys(driver, casesFieldElement, value);
	}

	/**
	 * This method use to enter agentUser name in agent user search field
	 * @param driver
	 * @param value
	 */
	public void enterAgentUserInSearchField(WebDriver driver,String value) {
		WebElement agentUserFieldElement = driver.findElement(agentUserSearchFieldLocator);
		sendKeys(driver, agentUserFieldElement, value);
	}

	/**
	 * This method use to select option from searched populated list
	 * @param driver
	 * @param list option
	 */
	public void selectSearchedListOption(WebDriver driver, String option) {
		pause(2000);
		List<WebElement> listElements = driver.findElements(searchedListLocator);
		if(listElements.size()!=0) {
			for (WebElement listElement : listElements) {
				String value = listElement.getText();
				if(value.contains(option)) {
					click(driver, listElement);
					waitForElementInvisibility(driver, searchedListLocator);
					break;
				}
			}
		}
		else {
			listElements = driver.findElements(searchedListLocator2);
			for (WebElement listElement : listElements) {
				String value = listElement.getText();
				if(value.contains(option)) {
					click(driver, listElement);
					waitForElementInvisibility(driver, searchedListLocator2);
					break;
				}
			}
		}
	}

	/**
	 * this method use to enter date in from date field
	 * @param driver
	 * @param date
	 */
	public void enterFromDate(WebDriver driver, String date) {
		WebElement fromDateFieldElement = driver.findElement(fromDateFieldLocator);
		sendKeys(driver, fromDateFieldElement, date);
	}

	/**
	 * this method use to enter date in to date field
	 * @param driver
	 * @param date
	 */
	public void enterToDate(WebDriver driver, String date) {
		WebElement toDateFieldElement = driver.findElement(toDateFieldLocator);
		sendKeys(driver, toDateFieldElement, date);
	}

	/**
	 * This method use to get table headers 
	 * @param driver
	 * @return list in string
	 */
	public List<String> getTableHeaders(WebDriver driver) {
		waitForElementVisibility(driver, tableHeadersLocator);
		List<WebElement> tableHeadersElements = driver.findElements(tableHeadersLocator);
		//waitForElementVisibility(driver, tableHeadersElements.get(0));
		List<String> headerList = new ArrayList<String>();
		for (WebElement rowElement : tableHeadersElements) {
			headerList.add(getText(driver, rowElement));
		}
		return headerList;
	}

	/**
	 * This method use to get data of column of table
	 * @param driver
	 * @param header
	 * @return data list in string
	 */
	public List<String> getDataOfColumn(WebDriver driver,String header) {
		int i=0;
		List<String> headers = getTableHeaders(driver);
		for (String userHeader : headers) {
			if(userHeader.equals(header)) {
				i++; break;
			}
			i++;
		}
		List<String> dataList = new ArrayList<String>();
		pause(1000);
		if(driver.findElements(interventionsTableRowsLocator).size()!=0) {
			List<WebElement> rowsElements =  driver.findElements(interventionsTableRowsLocator);
			for (WebElement rowElement : rowsElements) {
				try {
					WebElement columnElement = rowElement.findElement(By.xpath("td["+i+"]"));
					dataList.add(getText(driver, columnElement));
				}
				catch (StaleElementReferenceException e) {
					pause(1000);
					WebElement columnElement = rowElement.findElement(By.xpath("td["+i+"]"));
					dataList.add(getText(driver, columnElement));
				}
			}
		}
		else {
			List<WebElement> rowsElements =  driver.findElements(tableRowsLocator);
			for (WebElement rowElement : rowsElements) {
				try {
					WebElement columnElement = rowElement.findElement(By.xpath("td["+i+"]"));
					dataList.add(getText(driver, columnElement));
				}
				catch(StaleElementReferenceException e) {
					pause(2000);
					WebElement columnElement = rowElement.findElement(By.xpath("td["+i+"]"));
					dataList.add(getText(driver, columnElement));
				}
			} 
		}

		return dataList;
	}

	/**
	 * This method use to get detail from view page for given column
	 * @param driver
	 * @param typeOfData: name of column
	 * @return String
	 * @throws Exception
	 */
	public String getViewPageDetail(WebDriver driver, String typeOfData) throws Exception {
		List<WebElement> rowsElements = driver.findElements(tableRowsLocator);
		for (WebElement rowElement : rowsElements) {
			String type = rowElement.findElement(By.tagName("th")).getText();
			if(type.equals(typeOfData)) {
				return rowElement.findElement(By.tagName("td")).getText();
			}
		}
		throw new Exception(typeOfData+ " not found on page");
	}

	/**
	 * This method use to select radio option
	 * @param driver
	 * @param radioOption: radio button aria-label
	 */
	public void selectRadioBtn(WebDriver driver, String radioOption) {
		pause(1000);
		List<WebElement> radioBtnElements = driver.findElements(radioBtnLocator);
		for (WebElement radioBtnElement : radioBtnElements) {
			String option = radioBtnElement.getAttribute("aria-label");
			if(option.equals(radioOption)) {
				if(radioBtnElement.getAttribute("aria-checked").equals("false")) {
					radioBtnElement.click();
					break;
				}
			}
		}
	}


	/**
	 * This method use to select checkbox option
	 * @param driver
	 * @param checkboxOption: checkbox aria-label
	 */
	public void selectCheckbox(WebDriver driver, String checkboxOption) {
		pause(1000);
		List<WebElement> checkboxElements = driver.findElements(checkboxLocator);
		for (WebElement checkboxElement : checkboxElements) {
			String option = checkboxElement.getAttribute("aria-label");
			if(option.equals(checkboxOption)) {
				if(checkboxElement.getAttribute("aria-checked").equals("false")) {
					checkboxElement.click();
					break;
				}
			}
		}
	}

	/**
	 * This method use to unselect checkbox option
	 * @param driver
	 * @param checkboxOption: checkbox aria-label
	 */
	public void unselectCheckbox(WebDriver driver, String checkboxOption) {
		pause(1000);
		List<WebElement> checkboxElements = driver.findElements(checkboxLocator);
		for (WebElement checkboxElement : checkboxElements) {
			String option = checkboxElement.getAttribute("aria-label");
			if(option.equals(checkboxOption)) {
				if(checkboxElement.getAttribute("aria-checked").equals("true")) {
					checkboxElement.click();
					break;
				}
			}
		}
	}

	public void clickActionBtn(WebDriver driver, List<WebElement> btnElements, String uniqueDataValue, String header) throws Exception {
		boolean flag = false;
		List<String> dataList = getDataOfColumn(driver, header);
		for(int i=0;i<dataList.size();i++) {
			if(dataList.get(i).equals(uniqueDataValue)) {
				click(driver,btnElements.get(i));
				flag = true;
				break;
			}
		}
		if(flag==false) {
			throw new Exception(btnElements+ " button not visible for "+uniqueDataValue);
		}
	}



	// custom methods

	/**
	 * This method use to wait until page loaded
	 * @param driver
	 */
	public void waitForPageLoad(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	/**
	 * This method use to enter value in input field
	 * @param driver
	 * @param element
	 * @param value in string
	 */
	public void sendKeysWithoutClearField(WebDriver driver, WebElement element, String value) {
		pause(300);
		element.sendKeys(value);
		pause(300);
	}

	/**
	 * This method use to clear and enter value in input field
	 * @param driver
	 * @param element
	 * @param value in string
	 */
	public void sendKeys(WebDriver driver, WebElement element, String value) {
		waitForElementVisibility(driver, element);
		element.clear();
		pause(100);
		element.sendKeys(value);
		pause(100);
		if(!(element.getAttribute("value").equals(value))) {
			pause(100);
			element.clear();
			pause(100);
			element.sendKeys(value);
			pause(100);
		}
		pause(200);
	}

	/**
	 * This method use to wait for given milliseconds
	 * @param milliseconds
	 */
	public void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException i) {
			i.printStackTrace();
		}
	}

	/**
	 * This method use to select dropDoown options by Text
	 * @param driver
	 * @param element
	 * @param dropdown option in string
	 */
	public void selectDropdownOption(WebDriver driver, WebElement element, String value) {
		waitForElementVisibility(driver, element);
		Select dropdown= new Select(element);
		dropdown.selectByVisibleText(value);
	}

	/**
	 * This method use to select dropDoown options by Index
	 * @param driver
	 * @param element
	 * @param dropdown option in index
	 */
	public void selectDropdownOption(WebDriver driver, WebElement element, int index) {
		waitForElementVisibility(driver, element);
		Select dropdown= new Select(element);
		dropdown.selectByIndex(index);
	}


	/**
	 * This method use to select dropDoown options by Value
	 * @param driver
	 * @param element
	 * @param dropdown option value in string
	 */
	public void selectDropdownOptionByValue(WebDriver driver, WebElement element, String value) {
		waitForElementVisibility(driver, element);
		Select dropdown= new Select(element);
		dropdown.selectByValue(value);
	}

	public void click(WebDriver driver, WebElement element) {
		//waitForElementVisibility(driver, element);
		waitForElementClickable(driver, element);
		try {
			element.click();
		}
		catch(StaleElementReferenceException e) {
			pause(200);
			element.click();
		}
	}

	public String getText(WebDriver driver,WebElement element) {
		waitForElementVisibility(driver, element); 
		try {
			return element.getText();
		}
		catch(StaleElementReferenceException e) {
			pause(1000);
			return element.getText();
		}
	}

	public String getAttribute(WebDriver driver,WebElement element) {
		waitForElementVisibility(driver, element); 
		return element.getAttribute("value");
	}

	public String getBackgroundColorInHex(WebDriver driver,WebElement element) {
		waitForElementVisibility(driver, element); 
		String color = element.getCssValue("background-color");
		String hexColor = Color.fromString(color).asHex();	
		return hexColor;
	}

	public void acceptAlert(WebDriver driver) {
		new WebDriverWait(driver, 60).ignoring(NoAlertPresentException.class).until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		waitForPageLoad(driver);
	}


	public static boolean isElementPresent(WebDriver driver, WebElement elementLocator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(elementLocator));
			return true;
		} catch (TimeoutException t) {
			return false;
		}
	}

	public static boolean isElementDisplayed(WebDriver driver, WebElement elementLocator) {
		return (isElementPresent(driver, elementLocator) ? elementLocator.isDisplayed() : false);
	}

	public void waitForFieldToBePopulated(WebDriver driver, WebElement field, String value) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.textToBePresentInElementValue(field, value));
	}
	public static void waitForElementVisibility(WebDriver driver, WebElement elem)	{
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOf(elem));	
	}

	public static void waitForElementVisibility(WebDriver driver, By locator)	{
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));	
	}

	public void minimumWaitForElementVisibility(WebDriver driver, By elem)	{
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 20);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(elem));	
	}

	public void waitForAlert(WebDriver driver)	{
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.alertIsPresent());	
	}

	public void waitForElementInvisibility(WebDriver driver, By locator) {
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void waitForElementInvisibility(WebDriver driver, WebElement element) {
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 120);		
		webDriverWait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void waitForElementInVisibility(WebDriver driver, By elem,String text) {
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.invisibilityOfElementWithText(elem, text));
	}

	public void waitForALLElementsVisibility(WebDriver driver, By elem)	{
		WebDriverWait webDriverWait =  new WebDriverWait(driver, 20);
		webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elem));
	}

	public void waitForElementClickable(WebDriver driver, WebElement elem)	{
		try {
			WebDriverWait webDriverWait =  new WebDriverWait(driver, 30);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(elem));	
		}
		catch(StaleElementReferenceException e) {
			pause(1000);
			WebDriverWait webDriverWait =  new WebDriverWait(driver, 30);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(elem));	
		}

	}



	public void scrollToElement(WebDriver driver, WebElement elem){
		waitForElementVisibility(driver, elem);
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(false);", elem);
		pause(200);
	}

	public void scrollDown(WebDriver driver){
		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,200)");
	}

	public void scrollUp(WebDriver driver){
		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,-200)");
	}

	public void scrollToBottom(WebDriver driver) {
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}

	public void scrollToTop(WebDriver driver) {
		((JavascriptExecutor)driver).executeScript("window.scrollTo(document.body.scrollHeight,0)");

	}

	public void scrollToMid(WebDriver driver, WebElement ele) {
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";

		((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, ele);
	}

	public void clearField(WebDriver driver,WebElement element) {
		element.clear();
	}


}
