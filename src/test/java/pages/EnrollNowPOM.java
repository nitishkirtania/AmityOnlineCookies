package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EnrollNowPOM {
	
	WebDriver driver;
	public EnrollNowPOM(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@class='header-opt_applyNowBtn__M2I5W ClientSideButton_btnContainer__kilP_']//span[@class='ClientSideButton_btnText__5gMgu'][normalize-space()='ENROLL NOW']")
	public WebElement enrollnoWebElement;
	
	//div[contains(@class,'header_menuContainer__00BgW')]//span[contains(@class,'ClientSideButton_btnText__5gMgu')][normalize-space()='ENROLL NOW']
	
	
	@FindBy(xpath = "//h2[normalize-space()='Get Started with Your Certification!']")
	public WebElement ctaTextElement;
	
	@FindBy(xpath = "//input[contains(@placeholder,'Enter your full name')]")
	public WebElement nameElement;
	
	@FindBy(xpath = "//div[@class='CustomDropdownGlobal_countryCode__yswjf ']")
	public WebElement countrycodElement;
	
	@FindBy(xpath = "//input[@placeholder='Enter your no.']")
	public WebElement numberElement;
	
	@FindBy(xpath = "//input[@placeholder='abc@xyz.com']")
	public WebElement emailElement;
	
	@FindBy(xpath = "//select[@class='appearance-none lining-nums false text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
    public WebElement dropdownElement;
	
	@FindBy(xpath = "//button[normalize-space()='Submit']")
	public WebElement sumitElement;
	
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
	
	@FindBy(xpath = "//h4[normalize-space()='Payment Page']")
	public WebElement paymentpagecontentElement;
	
	@FindBy(xpath = "//span[normalize-space()='Welcome']")
	public WebElement welcomElement;
	
	@FindBy(xpath = "//div[@class='SponsoredCertification_right__VYhYx']//div[@class='CertCard_progMain__0W_u_']//div[@class='CertCard_progCard__sD8Kc']//div[@class='CertCard_progCardDetails__5ePER']//div//button[@class='CertCard_customButton__wUgah'][normalize-space()='Enroll Now']")
    public WebElement enrollnowHerocart;	
	
	@FindBy(css = "body > div:nth-child(6) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(5) > form:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(2) > span:nth-child(1)")
	public WebElement nepal;
	
	//body > div:nth-child(7) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(5) > form:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(2) > span:nth-child(1)
	
	
	
}
