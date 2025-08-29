package pages;

import org.apache.poi.hssf.record.OldCellRecord;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class HeaderPOM {

	WebDriver driver;

	public HeaderPOM(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//img[@title='Amity Logo']")
	
	//old path: "//img[@alt='amity-logo']"
	public WebElement amitylogoElement;

	@FindBy(xpath = "//div[@class='desktop-menu_dMenuItem__OKLC8 ']")
	public WebElement programsElement;

	@FindBy(xpath = "//a[@class='desktop-menu_dMenuItem__OKLC8 ']")
	public WebElement careerservicElement;
	
	//old path: "//span[@class='mr-1 uppercase laptop:font-medium header_menuText__KlhzO'][normalize-space()='CAREER SERVICES']"
	

	@FindBy(xpath = "//span[@class='mr-1 uppercase laptop:font-medium header_menuText__KlhzO'][normalize-space()='ADVANTAGES']")
	public WebElement advantagesElement;

	@FindBy(xpath = "//div[@class='desktop-program_dProgramClosedSearchWrapper__cWbgK']")
	public WebElement searchbuttotElement;
	
	//old path: "//div[@class='header_searchIcon__bLdgL header_searchIconNew__5aGyK']"

	@FindBy(xpath = "//div[@class='desktop-program_dProgramSearchWrapper__D9AsE']//input[@placeholder='Search Programs']")
	public WebElement searchbuttoncontentElement;
	
	//old path: //div[@id='searchContainer']//input[@type='text']

	@FindBy(xpath = "//button[@class='header_callUs__CyuCp']")
	public WebElement callusElement;

	@FindBy(xpath = "/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/*[name()='svg'][1]/*[name()='g'][1]/*[name()='path'][1]")
	public WebElement callusCloseElement;

	@FindBy(xpath = "//a[@aria-label='Video Call']")
	public WebElement videcallElement;


	@FindBy(xpath = "//button[normalize-space()='EXISTING STUDENT LOGIN']")
	public WebElement existingstudentloginElement;
	
	//old path: //button[@class='header_login__wzVUA']

	@FindBy(xpath = "//div[@class='text-blu font-baskervville font-normal capitalize mb-3 lg:mb-4 LoginModal_header__Nk7pU']")
	public WebElement existingstudentloginContentElement;

	@FindBy(xpath = "//div[@class='header-opt_applyBtnWrapper__NmhC_']//button[@class='header-opt_applyNowBtn__M2I5W ClientSideButton_btnContainer__kilP_']")
	public WebElement applyNowHeaderElement;
	
	//old path: //div[contains(@class,'header_menuContainer__00BgW')]//span[contains(@class,'ClientSideButton_btnText__5gMgu')][normalize-space()='APPLY NOW']

	@FindBy(xpath = "//h2[@class='ConnectModal_Modal__RightContainerTitle__WO56D pr-[2.3rem]']")
	public WebElement applynowcontentElement;

	@FindBy(xpath = "//button[contains(@class,'StripData_stripContainerNew__Slybs')]//span[1]")
	public WebElement yellowstripElement;
	
	@FindBy(xpath = "//h2[normalize-space()='Reserve Your Spot Now']")
	public WebElement yellowstripcontentElement;

	@FindBy(xpath = "//span[@class='desktop-program_programListTitle__e3xVd']")
	public WebElement allProgramElement;

	@FindBy(xpath = "//a[@title='Master of Business Administration with specialization in Human Resource Analytics (MBA)']")
	public WebElement mbaElement;

	@FindBy(xpath = "//h2[@class='ConnectModal_Modal__RightContainerTitleCallUs__0NOrs pr-[2.3rem]']")
	public WebElement calluspopcontentElement;

	@FindBy(xpath = "//a[normalize-space()='With Attractive Scholarships']")
	public WebElement AttractiveScholarship;
	
	@FindBy(xpath = "//span[normalize-space()='MBA']")
	public WebElement mbaHerocart;
	
	@FindBy(xpath = "//span[normalize-space()='BBA']")
	public WebElement bbaHerocart;
	
	@FindBy(xpath = "//span[normalize-space()='MCA']")
	public WebElement mcaHerocart;
	
	@FindBy(xpath = "//span[normalize-space()='BCA']")
	public WebElement bcaHerocart;
	
	@FindBy(xpath = "//div[@class='swiper-button-prev']")
	public WebElement leftCarousElement;
	
	@FindBy(xpath = "//div[@class='header-opt_applyBtnWrapper__NmhC_']//button[@class='header-opt_enquireNowBtn__EQD8D ClientSideButton_btnContainer__kilP_']")
	public WebElement enquireNowWebElement;
		
	@FindBy(xpath = "//span[normalize-space()='Continue Application']")
	public WebElement continueapplicationElement;
	
	@FindBy(xpath = "//h2[normalize-space()='Start Your Application Now']")
	public WebElement startyourapplicationcontentElement;
	
	@FindBy(xpath = "//input[@placeholder='Enter Email Id']")
	public WebElement existingstudentmailidElement;
	
	@FindBy(xpath = "//input[@placeholder='Enter Password ']")
	public WebElement existingstudentpasswordElement;
	
	@FindBy(xpath = "//span[normalize-space()='SUBMIT']")
	public WebElement existingstudentsubmitbuttonElement;

	@FindBy(xpath = "//button[normalize-space()='Enquire Now']")
	public WebElement lp_nw_enquireNow_Element;

	@FindBy(xpath = "//span[normalize-space()='Official Landing Page of Amity University Online']")
	public WebElement dynamic_text_element_lp_nw;


	@FindBy(xpath = "//button[@class='styles_btn__UuWR1']")
	public WebElement enquireNow_MiddleEast;
	
	
	
	
	
	public void Career() {
		careerservicElement.click();
	}

	public void VideoCall() {
		videcallElement.click();
	}

	public void YStrip() {
		JavascriptExecutor js3=((JavascriptExecutor)driver);
		js3.executeScript("arguments[0].click();", yellowstripElement);
	}
//-------------------------------------------Handling Carousel--------------------------------------------------	
	public void clickCarouselLeft() {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].click();", leftCarousElement);
	        Thread.sleep(1500); // wait for the slide to finish
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to click carousel left: " + e.getMessage());
	    }
	}
	
	public void clickIfPresentOrSwitchThenClick(WebElement elementToClick) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    try {
	        if (elementToClick.isDisplayed()) {
	            js.executeScript("arguments[0].scrollIntoView(true);", elementToClick);
	            js.executeScript("arguments[0].click();", elementToClick);
	        } else {
	            clickCarouselLeft(); // move to the other slide
	            if (elementToClick.isDisplayed()) {
	                js.executeScript("arguments[0].scrollIntoView(true);", elementToClick);
	                js.executeScript("arguments[0].click();", elementToClick);
	            } else {
	                throw new RuntimeException("Element not found on either slide.");
	            }
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to click element: " + e.getMessage());
	    }
	}
//----------------------------------------------------------------------------------------------------------------------	
	
	public void AScholarship() {
		clickIfPresentOrSwitchThenClick(AttractiveScholarship);
	}
	
	public void heroMBA() {
		clickIfPresentOrSwitchThenClick(mbaHerocart);
	}

	public void heroBBA() {
		clickIfPresentOrSwitchThenClick(bbaHerocart);
	}
	
	public void heroMCA() {
		clickIfPresentOrSwitchThenClick(mcaHerocart);
	}
	
	public void heroBCA() {
		clickIfPresentOrSwitchThenClick(bcaHerocart);
	}
	
}

