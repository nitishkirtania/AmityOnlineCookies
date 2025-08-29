package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OpenFormPOM {

    WebDriver driver;
	
	// Constructor to initialize WebDriver and PageFactory elements
	public OpenFormPOM(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

    @FindBy(xpath = "//div[@class='jsx-b6bb89237204caff w-[100%]']//input[@placeholder='Enter your full name']")
		public WebElement name;
		
		@FindBy(xpath = "//select[@class='appearance-none lining-nums false text-stone-500 bg-white text-lg leading-loose tracking-tight outline-none pr-10 false Input_selectDropdown__rRnfH']")
		public WebElement country;
		
		@FindBy(xpath = "//div[@class='jsx-b6bb89237204caff w-full']//input[@placeholder='Enter your no.']")
		public WebElement mobile;
		
		@FindBy(xpath = "//div[@class='jsx-b6bb89237204caff w-full']//input[@placeholder='abc@xyz.com']")
		public WebElement email;
		
		@FindBy(xpath = "//form[@class='jsx-b6bb89237204caff ConnectModalForm_root__zYe2H h-full flex flex-col justify-between	']//button[@type='submit'][normalize-space()='Submit']")
		public WebElement submitbutton;
		
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
		
	
		public void selectdropdown() {
			Select dropdown1=new Select(country);
			dropdown1.selectByIndex(0);
		}
		
		public void selectdropdown1() {
			Select dropdown1=new Select(country);
			dropdown1.selectByIndex(2);
		}
		
		
		public void clicksubmit() {
			submitbutton.click();
			
		}

}
