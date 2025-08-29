package pages;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class DownloadBrochure {
	WebDriver driver;
	public DownloadBrochure(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//span[normalize-space()='Download brochure']")
	public WebElement downloadbrochurElement;
	
	@FindBy(xpath = "//select[@name='countryCode']")
	public WebElement countrycodWebElement;
	
	@FindBy(xpath = "//input[@name='phone']")
	public WebElement contactnumberElement;
	
	@FindBy(xpath = "//div[@class='Form_control__eYGP8']//input[@placeholder='Enter your full name']")
	public WebElement usernamElement;
	
	@FindBy(xpath = "//input[@placeholder='Enter your email']")
	public WebElement useremailElement;
	
	@FindBy(xpath = "//span[normalize-space()='SUBMIT']")
	public WebElement usersubmitbuttonElement;
	
	//----------------------------OTP--------------------
	
	@FindBy(xpath = "//input[@id='otp_0']")
	public WebElement OTPCell1;

	@FindBy(xpath = "//input[@id='otp_1']")
	public WebElement OTPCell2;

	@FindBy(xpath = "//input[@id='otp_2']")
	public WebElement OTPCell3;

	@FindBy(xpath = "//input[@id='otp_3']")
	public WebElement OTPCell4;

	@FindBy(xpath = "//input[@id='otp_4']")
	public WebElement OTPCell5;

	@FindBy(xpath = "//input[@id='otp_5']")
	public WebElement OTPCell6;
	
	@FindBy(xpath = "//span[normalize-space()='VERIFY OTP']")
	public WebElement VerifyOTP;
	
	@FindBy(xpath = "//span[normalize-space()='PROCEED']")
	public WebElement proceedbutton;
	
	@FindBy(xpath = "//button[@id='startApplicationgta']")
	public WebElement startapplicationElement;
	
	
	public void clickbrochure() {
		downloadbrochurElement.click();
	}
	
	public void selectdropdown() {
		Select dropdown2=new Select(countrycodWebElement);
		dropdown2.selectByIndex(0);
	}
	
	public void selectdropdown1() {
		Select dropdown3=new Select(countrycodWebElement);
		dropdown3.selectByIndex(5);
	}
	
	//-------------------------switch window methods for next window tab--------------------------
	
	public void switchwindow() {
		
		String parentwindow2 = driver.getWindowHandle();
		Set<String> allwin2 = driver.getWindowHandles();
		Iterator<String> itr2 = allwin2.iterator();
		while(itr2.hasNext()) {
			String childwindow2 = itr2.next();
			if(!parentwindow2.equals(childwindow2)) {
				driver.switchTo().window(childwindow2);
				
			}
			
		}
	}
	

}
