package Random;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RandomDetails {
	
	WebDriver driver;

    // Constructor to initialize WebDriver and PageFactory elements
    public RandomDetails(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initialize PageFactory elements
    }
	
	
	public String GetRandomName() {
		return (RandomStringUtils.randomAlphabetic(5));
		
	}
	
	
	public String getRandomMobileNumber() {
		return (RandomStringUtils.randomNumeric(7));
		
	}
	
	public String getRandomMobileNumber2() {
		return (RandomStringUtils.randomNumeric(7));
		
	}
	
	public String GetRamdonEmailID() {
		return (RandomStringUtils.randomAlphanumeric(6));
		
	}

	public String GetOTP() {
		return(RandomStringUtils.randomNumeric(1));
		
	}
	
	
}
