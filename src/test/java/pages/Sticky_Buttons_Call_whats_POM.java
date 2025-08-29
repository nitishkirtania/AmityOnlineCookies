package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Sticky_Buttons_Call_whats_POM {

    WebDriver driver;
	
	public Sticky_Buttons_Call_whats_POM(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

    @FindBy(xpath = "//img[@id='call-icon']")
    public WebElement call_icon_Element;

    @FindBy(xpath = "//img[@id='wa-icon']")
    public WebElement whatsapp_icon_Element;

    @FindBy(xpath = "//h2[contains(text(),'Journey Towards Graduation: Your Online Degree Pro')]")
    public WebElement scrollElement;

    @FindBy(xpath = "//div[@class='SideButtons_stickyApplyBtn__3Oe4V']")
    public WebElement apply_now_button;

    @FindBy(xpath = "//img[@id='engtLauncherIcon']")
    public WebElement chat_icon_Element;

    @FindBy(xpath = "//a[@aria-label='Video Call']")
    public WebElement video_call_icon_Element;

    @FindBy(xpath = "//h2[normalize-space()='Meet our top-ranked faculty']")
    public WebElement meet_our_top_renk;

    @FindBy(xpath = "//h2[normalize-space()='Meet our top-ranked Faculty']")
    public WebElement meet_our_top_rank_BA_Element;

    @FindBy(xpath = "//h2[normalize-space()='Admission Process']")
	public WebElement admissionprocessElement;

     @FindBy(xpath = "//a[@aria-label='Video Call']")
    public WebElement videocallElement;

    @FindBy(xpath = "//a[@aria-label='Start video call']")
    public WebElement Start_video_call_blog_page;

    @FindBy(xpath = "(//img[@id='whatsapp-link-icon'])[1]")
    public WebElement whatsapp_icon_Element_ba;

    @FindBy(xpath = "(//a[@aria-label='Start video call'])[1]")
    public WebElement video_call_icon_Element_ba;

}
