package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.MediaType;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.markuputils.ExtentColor;
import config.ApplicationData;
import constants.Constants;
import driverfactory.DriverFactory;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IClassListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;


public class TestListener implements ITestListener, IClassListener {

	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;
	private static String filePathex = System.getProperty("user.dir")+"\\Extent Reports\\extentreport.html"; /* Give path wherever you want to save your extent report */
	private static DriverFactory webDriver = new DriverFactory();
	private static Media med;
	private static MediaEntityModelProvider mp;
	String screen, screen1;
	WebDriver driver;
	String filePath = System.getProperty("user.dir")+"\\Failure Screenshots\\"; /* Give path wherever you want to save your screenshot */
	int count = 1, count1 = 1;
	String line = "<td class=\"result\">";
	String method[] = new String[100];
	String method1[] = new String[100];
	ITestResult result;
	FileOperations fileOperations = new FileOperations();
	Constants constants = new Constants();
	String token = null;
	static ApplicationData applicationDataObj = new ApplicationData();


	public void onStart(ITestContext context) {
		System.out.println("Suite start");
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onBeforeClass(ITestClass testclass) {
		extent = getExtent();
		test = extent.createTest(testclass.getRealClass().getSimpleName());
		System.out.println("Class beginning " + testclass.getRealClass().getSimpleName());
	}

	public void onTestStart(ITestResult result) {

		System.out.println(" Test case is started");
	}

	public void onTestSuccess(ITestResult result) {
		test.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
		.pass(MarkupHelper.createLabel("PASS", ExtentColor.GREEN)).log(Status.PASS, result.getTestName());
		try {
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Test case is executed successfully");
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("***** Skip " + result.getName() + " test has failed *****");
		method1[count1 - 1] = result.getName().toString().trim();
		this.result = result;
		takeScreenShot(method1[count1 - 1], webDriver.getCurrentWebDriver());
		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(screen1);
		mp = new MediaEntityModelProvider(med);
		test.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
		.skip(MarkupHelper.createLabel("SKIP", ExtentColor.BLUE))
		.skip("Screenshot " + result.getMethod().getMethodName(), mp).log(Status.SKIP, result.getThrowable());
		count++;

		System.out.println("Test case is skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("***** Error " + result.getName() + " test has failed *****");
		method[count - 1] = result.getName().toString().trim();
		this.result = result;
		takeScreenShot(method[count - 1], webDriver.getCurrentWebDriver());
		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(screen1);
		mp = new MediaEntityModelProvider(med);
		test.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
		.fail(MarkupHelper.createLabel("FAIL", ExtentColor.RED))
		.fail("Screenshot " + result.getMethod().getMethodName(), mp).log(Status.FAIL, result.getThrowable());
		count++;

		// New Code
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Test case is failed");
	}

	public void onAfterClass(ITestClass testclass) {
		System.out.println("Class ending" + testclass.getRealClass().getSimpleName());
	}

	public void onFinish(ITestContext context) {
		extent.flush();

		System.out.println("Suite Finish");
	}

	public void takeScreenShot(String methodName, WebDriver driver) {
		this.driver = driver;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			File scrFile2 = new File(filePath + methodName + ".png");
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			FileUtils.copyFile(scrFile, scrFile2);
			System.out.println("***Placed screen shot in " + filePath + " ***");
			screen1 = scrFile2.toString();
			screen = "<img src='" + scrFile2.toString() + "' width='200' height='200'  > ";
			Reporter.setEscapeHtml(false);
			Reporter.log(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ExtentReports getExtent() {
		if (extent == null) {
			extent = new ExtentReports();
			extent.setSystemInfo("Name", "Gaurav Singh");
			extent.setSystemInfo("Browser", applicationDataObj.getBrowserType());
			extent.setSystemInfo("OS", DriverFactory.getOperatingSystemType());
			extent.attachReporter(getHtmlReporter());
		}
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter() {
		htmlReporter = new ExtentHtmlReporter(filePathex);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("GeoMans Automation");
		htmlReporter.config().setReportName("GeoMans Automation Report");
		return htmlReporter;
	}
}
