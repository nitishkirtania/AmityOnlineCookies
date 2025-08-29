package pages;

import org.openqa.selenium.WebDriver;
import Random.RandomDetails;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ApplyNowPOM {

	WebDriver driver;
	RandomDetails random;

	// Constructor to initialize WebDriver and PageFactory elements
	public ApplyNowPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize PageFactory elements
	}

	@FindBy(xpath = "//div[contains(@class,'header-opt_applyBtnWrapper__NmhC_')]//button[contains(@class,'header-opt_applyNowBtn__M2I5W ClientSideButton_btnContainer__kilP_')]")
	public WebElement ApplynowBTN;

	@FindBy(xpath = "//button[@class='NationalInternational_national__FBIVx']")
	public WebElement indianBTN;

	@FindBy(xpath = "//button[@class='NationalInternational_interNational__RKrPp NationalInternational_notSelectedButton__lh0q7']")
	public WebElement internationalElement;

	@FindBy(xpath = "//div[@class='basis-1/2']//input[@placeholder='Enter your full name']")
	public WebElement nameElement;

	@FindBy(css = "div[class='mt-[0.4166666667rem] sm:mt-[0.3rem]'] div[class='CustomDropdownGlobal_countryCode__yswjf ']")
	public WebElement countrycodeElement;

	@FindBy(xpath = "//div[contains(@class,'basis-1/2')]//input[contains(@placeholder,'Enter your no.')]")
	public WebElement mobiElement;

	@FindBy(xpath = "//div[@class='basis-1/2']//input[@placeholder='abc@xyz.com']")
	public WebElement emailElement;

	@FindBy(xpath = "//form[@class='ConnectModalForm_root__zYe2H pr-[2rem]']//button[@type='submit'][normalize-space()='Submit']")
	public WebElement submitElement;

	@FindBy(xpath ="//div[@class='basis-1/2']//select[@class='appearance-none lining-nums w-full text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
	public WebElement degreeElement;


	@FindBy(xpath = "//div[@class='ConnectModalForm_commonMargins__efaYq w-full']//select[@class='appearance-none lining-nums w-full text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
	public WebElement prograElement;

	@FindBy(xpath = "//form[@class='ConnectModalForm_root__zYe2H pr-[2rem]']//button[@type='submit'][normalize-space()='Submit']")
	public WebElement submit2elElement;

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
	
	@FindBy(xpath = "//button[normalize-space()='Start Scholarship Test']")
    public WebElement Start_Scholership_Test;
 
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement attempt_test_element;
    
    @FindBy(xpath = "//button[@class='StripData_stripContainerNew__Slybs']//span[1]")
    public WebElement scholershipyelloWebElement;
    
    @FindBy(xpath = "//div[@class='ConnectModalForm_commonMargins__efaYq w-full lg:mt-4']//select[@class='appearance-none lining-nums w-full text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")   
    public WebElement programscholarship;
	
	@FindBy(xpath = "//input[@placeholder='Enter your no.']")
	public WebElement mobile_no_lp_nw_Element;

	@FindBy(xpath = "//input[@placeholder='abc@xyz.com']")
	public WebElement email_lp_nw_Element;

	@FindBy(xpath = "//div[@class='basis-1/2']//select[@class='appearance-none lining-nums w-full text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
	public WebElement degree_lp_nw_Element;

	@FindBy(xpath = "//div[@class='TemplateOneConnectModalForm_commonMargins__EWh9H w-full']//select[@class='appearance-none lining-nums w-full text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
	public WebElement program_lp_nw_Element;

	@FindBy(xpath = "//input[@placeholder='Enter your full name']")
	public WebElement full_name_lp_nw_Element;

	@FindBy(xpath ="//button[normalize-space()='Submit']")
	public WebElement bba_webinar_sibmit;

	@FindBy(xpath = "//span[normalize-space()='Continue Application']")
	public WebElement continue_application_lp_nw_Element;

	@FindBy(xpath = "//button[@class='font-semibold font-raleway uppercase w-full border Input_btn___g__n bg-primary-blue border-blue-950 text-white']")
	public WebElement submit_button_blog;

	



}

