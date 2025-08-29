package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPagesPOM {
	WebDriver driver;
	public LandingPagesPOM(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//span[contains(text(),'July’25 Session Admissions Open')]")
	public WebElement yellowstripCTA;
	
	@FindBy(xpath = "//input[@placeholder='Enter your no.']")
	public WebElement mobilenumberElement;
	
	@FindBy(xpath = "//input[@placeholder='Enter your full name']")
	public WebElement fullnamElement;
	
	@FindBy(xpath = "//input[@placeholder='abc@xyz.com']")
	public WebElement emailidElement;
	
	@FindBy(xpath = "//button[@class='font-semibold font-raleway uppercase w-full border Input_btn___g__n bg-primary-blue border-blue-950 text-white']")
	public WebElement submitbuttonElement;
	
	@FindBy(xpath ="//div[@class='basis-1/2']//select[@class='appearance-none lining-nums w-full text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
	public WebElement degreeElement;

	@FindBy(xpath = "//div[@class='SelectDark_SelectContainer__TyXTH w-full']//select[@id='program_lookup']")
	public WebElement prograElement;
	
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
	
	@FindBy(xpath = "//span[normalize-space()='Continue Application']")
	public WebElement continueapplicationElement;
	
	@FindBy(xpath = "//div[@class='TemplateOneConnectModalForm_commonMargins__EWh9H w-full']//select[@class='appearance-none lining-nums w-full text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
	public WebElement openprogramElement;
	
	@FindBy(css = "button[type='submit']")
	public WebElement opensubmitElement;
	
	@FindBy(xpath = "//h2[normalize-space()='Apply Now']")
	public WebElement applynowBluecart;

	@FindBy(xpath = "//form[@class='LandingPageConnectModalFormTempleteOne_root__0HQXJ']//button[@type='submit'][normalize-space()='Submit']")
	public WebElement submit_button_one;

	@FindBy(xpath = "//div[@class='LandingPageConnectModalFormTempleteOne_commonMargins__0rKj0 w-full']//select[@id='program_lookup']")
	public WebElement program_select_Element;

	@FindBy(xpath = "//form[@class='LandingPageConnectModalFormTempleteOne_root__0HQXJ']//button[@type='submit'][normalize-space()='Submit']")
	public WebElement submit_button_twoElement;

	@FindBy(xpath = "//form[@class='ConnectModalFormLP_root__GvIoA']//button[@type='submit'][normalize-space()='Submit']")
	public WebElement submit_button_mba;

}

