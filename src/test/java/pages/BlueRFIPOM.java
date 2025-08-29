package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlueRFIPOM {
	
	WebDriver driver;
	
	public BlueRFIPOM(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='LeadForm_formSection__TxeSn']//h2[contains(text(),'Apply Now')]")
	public WebElement applynowtextElement;
	
	@FindBy(xpath = "//div[@class='HomeConnectModalForm_inputWrapper__sQwPv']//input[@placeholder='Enter your full name']")
	public WebElement fullnamElement;
	
	@FindBy(xpath = "//div[@class='CustomDropdownGlobal_countryCode__yswjf CustomDropdownGlobal_darkText__HHfKn']")
	public WebElement countrycodElement;
	
	@FindBy(xpath = "//div[@class='w-full']//input[@placeholder='Enter your no.']")
	public WebElement phonenumberElement;
	
	@FindBy(css = "body > main:nth-child(77) > section:nth-child(17) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > label:nth-child(1)")
	public WebElement phonenumbertext;
	
	@FindBy(xpath = "//div[@class='HomeConnectModalForm_inputWrapper__sQwPv']//input[@placeholder='abc@xyz.com']")
	public WebElement emailidElement;
	
	@FindBy(xpath = "(//button[normalize-space()='Submit'])[1]")
	public WebElement submitbuttonElement;
	
	@FindBy(xpath = "//div[@class='CustomDropdownGlobal_dropdown__2uWby']//div[9]//span[1]")
	public WebElement internationalunitedstatElement;
	
	@FindBy(css = "div[class='HomeConnectModalForm_inputWrapper__sQwPv'] select[class='undefined']")
	public WebElement degreeElement;
	
	@FindBy(css = "div[class='HomeConnectModalForm_commonMargins__X2ljp w-full'] select[class='undefined']")
	public WebElement programElement;
	
	@FindBy(xpath = "(//button[normalize-space()='Submit'])[1]")
	public WebElement submittbutton2;
	
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

	@FindBy(xpath = "//button[@id='startApplicationgta']")
	public WebElement startapplicationElement;

	@FindBy(xpath = "//button[@class='font-semibold font-raleway uppercase w-full border Input_btn___g__n bg-primary-blue border-blue-950 text-white']")
	public WebElement submitbuttonElement_new;

	@FindBy(xpath = "//input[@id='full_name']")
	public WebElement full_name_Element;

	@FindBy(xpath = "//input[@id='enter_phone_no']")
	public WebElement phone_number_Element;
	
	@FindBy(xpath = "//input[@id='email_id']")
	public WebElement email_section_Element;

	@FindBy(xpath = "(//select[@id='degree_lookup'])[1]")
	public WebElement degree_select_Element;

	@FindBy(xpath = "(//select[@id='degree_lookup'])[2]")
	public WebElement program_select_Element;
	
	
}
