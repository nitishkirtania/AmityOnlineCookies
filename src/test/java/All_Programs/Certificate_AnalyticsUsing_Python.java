package All_Programs;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.Status;
import base.LeadSummary;
import base.LeadSummaryTwo;
import base.base;
import pages.ApplyNowPOM;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

public class Certificate_AnalyticsUsing_Python extends base {

    @BeforeMethod(alwaysRun = true)
	public void redirectToCertificate() throws InterruptedException {
		driver.get("https://qa-fe.amityonline.com/certifications/predictive-analytics-using-python");
		Thread.sleep(2000);
	}

    @Test(priority = 1)
    public void Enquire_Now_India() throws Exception {
        test = reports.createTest("Enquire Now Open Form India (Lp Nw-Middle East MBA)");
        // closescholarshippopup();
        // closePopupIfPresent();
        String expectedURL = "";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "Machine Learning & Artificial Intelligence";
        String Enquirysource = "Enquire";
        String utm_medium = "request_a_callback";
        long startTime = System.nanoTime();

        List<WebElement> error404Elements = driver
                .findElements(By.xpath("//h1[normalize-space()='404 - Page Not Found']"));
        if (!error404Elements.isEmpty() && error404Elements.get(0).isDisplayed()) {
            test.log(Status.SKIP, "Page returned 404 - Test skipped.");
            throw new SkipException("404 - Page Not Found");
        }
        // wait.until(ExpectedConditions.elementToBeClickable(hPom.enquireNow_MiddleEast));
        WebElement enquire_button = driver.findElement(By.xpath("//button[@class='header-opt_applyNowBtn__M2I5W ClientSideButton_btnContainer__kilP_']//span[@class='ClientSideButton_btnText__5gMgu'][normalize-space()='ENROLL NOW']"));
        js.executeScript("arguments[0].click();", enquire_button);
        test.log(Status.PASS, "Enquire Now button clicked");

        String randomMobileNumber = "233" + random.getRandomMobileNumber();
        String randomName = "TestQA " + random.GetRandomName();
        String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@yopmail.com";
        String emailPrefix = randomEmail.split("@")[0];

        try {
            test.info("Random Name:" + randomName);
            test.info("Random Mobile Number:" + randomMobileNumber);
            test.info("Random Email: " + randomEmail);

            Apom.mobile_no_lp_nw_Element.sendKeys(randomMobileNumber);
            Apom.full_name_lp_nw_Element.sendKeys(randomName);
            Apom.email_lp_nw_Element.sendKeys(randomEmail);
            // wait.until(ExpectedConditions.elementToBeClickable(Apom.degree_lp_nw_Element)).sendKeys("PG");
            // wait.until(ExpectedConditions.elementToBeClickable(Apom.program_lp_nw_Element))
            //         .sendKeys("MASTER OF BUSINESS ADMINISTRATION");
            Apom.bba_webinar_sibmit.click();
            // START HERE COOKIES
            String decodedVisited = null;
            String cleanVisited = null;
            Cookie visitedURLsCookie = waitForCookie("VisitedURLs", 5, 5000); // 5 retries, 1s gap

            if (visitedURLsCookie != null) {
                decodedVisited = URLDecoder.decode(visitedURLsCookie.getValue(), StandardCharsets.UTF_8);
                cleanVisited = decodedVisited.replace("[\"", "").replace("\"]", "");
                test.info("✅ Clean Visited URL Cookie: " + cleanVisited);

                if (cleanVisited.contains("bachelor-of-business-administration-online")) {
                    System.out.println("Use cleanVisited in another section: " + cleanVisited);
                }
            } else {
                test.fail("❌ VisitedURLs cookie not found after retry.");
            }

            String lsqId = null;
            Cookie lsqCookie = waitForCookie("LSQ_ID2", 5, 3000); // 5 retries, 1 second apart
            if (lsqCookie != null) {
                lsqId = lsqCookie.getValue();
                test.info("✅ LSQ_ID2 Cookie: " + lsqId);
            } else {
                test.fail("❌ LSQ_ID2 cookie not found after retries.");
            }

            String ApplicationUrl = null;
            Cookie appCookie = waitForCookie("ApplicationUrl", 5, 5000);
            if (appCookie != null) {
                String decodedAppUrl = URLDecoder.decode(appCookie.getValue(), StandardCharsets.UTF_8);
                ApplicationUrl = decodedAppUrl.replace("/lander", "/DashBoard");
                test.info("✅ Final Application URL Cookie: " + ApplicationUrl);
            } else {
                test.fail("❌ ApplicationUrl cookie not found after retries.");
            }

            wait.until(ExpectedConditions.visibilityOf(Apom.OTPCell1)).sendKeys(random.GetOTP());
            Apom.OTPCell2.sendKeys(random.GetOTP());
            Apom.OTPCell3.sendKeys(random.GetOTP());
            Apom.OTPCell4.sendKeys(random.GetOTP());
            Apom.OTPCell5.sendKeys(random.GetOTP());
            Apom.OTPCell6.sendKeys(random.GetOTP());

            Apom.VerifyOTP.click();
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate returned data
            Certificate_AnalyticsUsing_Python.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL, expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);
            Thread.sleep(5000);
            
            wait.until(ExpectedConditions.elementToBeClickable(Apom.continue_application_lp_nw_Element));
            js.executeScript("arguments[0].scrollIntoView();", Apom.continue_application_lp_nw_Element);

            if (Apom.continue_application_lp_nw_Element.isEnabled()) {
                test.log(Status.PASS, "Enquire Now +91 Journey is Successful");
                softAssert.assertTrue(true);
            } else {
                throw new Exception("Enquire Now +91 Journey not enabled.");
            }
            Thread.sleep(5000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                    Apom.continue_application_lp_nw_Element);
            Thread.sleep(2000);
            // String dashboard_url = driver.getCurrentUrl();
            // test.log(Status.PASS, "Redirect to dashboard page : " + dashboard_url);
            WebElement start_appli = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='startApplicationgta']")));
            start_appli.isEnabled();
            test.log(Status.PASS, "Start Application Button Enabled");
            String student_login = driver.getCurrentUrl();
            // Remove http:// or https:// from both URLs
            String actualTrimmed = student_login.replaceFirst("^https?://", "");
            String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");

            if (actualTrimmed.equals(expectedTrimmed)) {
                System.out.println("✅ URL matched: " + student_login);
                test.log(Status.PASS, "✅ URL matched Application Url of Cookies to application journey page : " + student_login);
            } else {
                System.out.println("❌ URL mismatch.");
                System.out.println("Expected: " + ApplicationUrl);
                System.out.println("Actual: " + student_login);
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Enquire Now +91 Journey is Failed: " + e.getMessage());
            softAssert.fail(e.getMessage());
        }
        long endTime1 = System.nanoTime();
		long durationInSeconds1 = (endTime1 - startTime) / 1_000_000_000;
		test.info("Total Load Time: " + formatSeconds(endTime1 - startTime));
		checkVisualErrorsOnScreen();
		softAssert.assertAll();
    }


    public Cookie waitForCookie(String name, int maxRetries, int waitMillis) throws InterruptedException {
    for (int i = 0; i < maxRetries; i++) {
        Cookie cookie = driver.manage().getCookieNamed(name);
        if (cookie != null) {
            return cookie;
        }
        Thread.sleep(waitMillis); // Wait before retrying
    }
    return null; // Cookie never appeared
}



    public static void validateLeadSummaryData(LeadSummary lead,
                                           String expectedEmail,
                                           String expectedFullName,
                                           String expectedPhone,
                                           String expectedPageURL,
                                           String expectedProgram,
                                           String expectedSpecialization, String lsqId, String cleanVisited, String Enquirysource, String utm_medium,
                                           SoftAssert softAssert) {
    if (lead == null) {
        softAssert.fail("❌ Lead data is null. Cannot perform LS validation.");
        test.log(Status.FAIL, "❌ Lead data is null. LS Validation aborted.");
        return;
    }

    // ✅ Email check
    if (!lead.email.equalsIgnoreCase(expectedEmail)) {
        softAssert.fail("❌ Email mismatch LS : expected '" + expectedEmail + "', actual '" + lead.email + "'");
        test.log(Status.FAIL, "❌ Email mismatch LS : expected '" + expectedEmail + "', actual '" + lead.email + "'");
    } else {
        test.log(Status.PASS, "✅ Email matched LS : " + lead.email);
    }

    // ✅ Full Name check
    if (!lead.fullName.equalsIgnoreCase(expectedFullName)) {
        softAssert.fail("❌ Name mismatch LS : expected '" + expectedFullName + "', actual '" + lead.fullName + "'");
        test.log(Status.FAIL, "❌ Name mismatch LS : expected '" + expectedFullName + "', actual '" + lead.fullName + "'");
    } else {
        test.log(Status.PASS, "✅ Name matched LS : " + lead.fullName);
    }

    // ✅ Phone check
    if (!lead.phone.equals(expectedPhone)) {
        softAssert.fail("❌ Phone mismatch LS : expected '" + expectedPhone + "', actual '" + lead.phone + "'");
        test.log(Status.FAIL, "❌ Phone mismatch LS : expected '" + expectedPhone + "', actual '" + lead.phone + "'");
    } else {
        test.log(Status.PASS, "✅ Phone matched LS : " + lead.phone);
    }

    // // ✅ Page URL check
    // if (!lead.pageURL.equals(expectedPageURL)) {
    //     softAssert.fail("❌ Page URL mismatch LS : expected '" + expectedPageURL + "', actual '" + lead.pageURL + "'");
    //     test.log(Status.FAIL, "❌ Page URL mismatch LS : expected '" + expectedPageURL + "', actual '" + lead.pageURL + "'");
    // } else {
    //     test.log(Status.PASS, "✅ Page URL matched LS : " + lead.pageURL);
    // }

    // ✅ Page URL check cookies
    if (!lead.pageURL.equals(cleanVisited)) {
        softAssert.fail("❌ Page URL mismatch LS Cookies : expected '" + cleanVisited + "', actual '" + lead.pageURL + "'");
        test.log(Status.FAIL, "❌ Page URL mismatch LS Cookies : expected '" + cleanVisited + "', actual '" + lead.pageURL + "'");
    } else {
        test.log(Status.PASS, "✅ Page URL matched LS Cookies : " + lead.pageURL);
    }

    // // ✅ Program Name check
    // if (!lead.programName.equalsIgnoreCase(expectedProgram)) {
    //     softAssert.fail("❌ Program name mismatch LS : expected '" + expectedProgram + "', actual '" + lead.programName + "'");
    //     test.log(Status.FAIL, "❌ Program name mismatch LS : expected '" + expectedProgram + "', actual '" + lead.programName + "'");
    // } else {
    //     test.log(Status.PASS, "✅ Program name matched LS : " + lead.programName);
    // }

    // // ✅ Specialization Name check with null/blank handling
    // String expected = expectedSpecialization != null ? expectedSpecialization.trim() : "";
    // String actual = lead.specialization != null ? lead.specialization.trim() : "";

    // if (expected.isEmpty() && actual.isEmpty()) {
    //     // Both are blank/null → no message
    // } else if (expected.isEmpty()) {
    //     // Expected is blank, actual is not
    //     softAssert.fail("❌ Specialization mismatch LS: expected empty, actual '" + actual + "'");
    //     test.log(Status.FAIL, "❌ Specialization mismatch LS: expected empty, actual '" + actual + "'");
    // } else if (actual.isEmpty()) {
    //     // Actual is blank, expected is not
    //     softAssert.fail("❌ Specialization mismatch LS: expected '" + expected + "', actual empty");
    //     test.log(Status.FAIL, "❌ Specialization mismatch LS: expected '" + expected + "', actual empty");
    // } else if (!expected.equalsIgnoreCase(actual)) {
    //     // Values don't match
    //     softAssert.fail("❌ Specialization mismatch LS: expected '" + expected + "', actual '" + actual + "'");
    //     test.log(Status.FAIL, "❌ Specialization mismatch LS: expected '" + expected + "', actual '" + actual + "'");
    // } else {
    //     // Values match
    //     test.log(Status.PASS, "✅ Specialization name matched LS: " + actual);
    // }


    // ✅ Enquiry Source check
    // String expectedEnquirySource = "Apply Now";

    if (!Enquirysource.equalsIgnoreCase(lead.enquarySource)) {
        softAssert.fail("❌ Enquiry source mismatch LS: expected '" + Enquirysource + "', actual '" + lead.enquarySource + "'");
        test.log(Status.FAIL, "❌ Enquiry source mismatch LS: expected '" + Enquirysource + "', actual '" + lead.enquarySource + "'");
    } else {
        test.log(Status.PASS, "✅ Enquiry source matched LS: " + lead.enquarySource);
    }

    // ✅ UTM Source check
    String expectedutmSource = "website";

    if(!expectedutmSource.equalsIgnoreCase(lead.utmSource)) {
        softAssert.fail("❌ UTM source mismatch LS: expected '" + expectedutmSource + "', actual '" + lead.utmSource + "'");
        test.log(Status.FAIL, "❌ UTM source mismatch LS: expected '" + expectedutmSource + "', actual '" + lead.utmSource + "'");
    } else {
        test.log(Status.PASS, "✅ UTM source matched LS: " + lead.utmSource);
    }

    // ✅ UTM Medium check
    // String expectedUTMmedium = "request_a_callback";
    if(!utm_medium.equalsIgnoreCase(lead.utmMedium)) {
        softAssert.fail("❌ UTM Medium mismatch LS: expected '" + utm_medium + "', actual '" + lead.utmMedium + "'");
        test.log(Status.FAIL, "❌ UTM Medium mismatch LS: expected '" + utm_medium + "', actual '" + lead.utmMedium + "'");
    } else {
        test.log(Status.PASS, "✅ UTM Medium matched LS: " + lead.utmMedium);
    }

    // ✅ Country Name check with null/blank handling
    String country = lead.countryName != null ? lead.countryName.trim() : "";
    System.out.println("Country Name we get from API " + lead.countryName);

    if (country.isEmpty()) {
        softAssert.fail("❌ Country name not found LS (null or blank)");
        test.log(Status.FAIL, "❌ Country name not found LS (null or blank)");
    } else if (country.equalsIgnoreCase("India")) {
        test.log(Status.PASS, "✅ Country matched LS : India");
    } else if (country.equalsIgnoreCase("United Arab Emirates")) {
        test.log(Status.PASS, "✅ Country matched LS : United Arab Emirates");
    } else {
        softAssert.fail("❌ Unexpected country name LS: got '" + country + "', expected 'India' or 'United Arab Emirates'");
        test.log(Status.FAIL, "❌ Unexpected country name LS: got '" + country + "', expected 'India' or 'United Arab Emirates'");
    }
        
    // ✅ Comm Consent Date Check
    SimpleDateFormat fullFormat = new SimpleDateFormat("d/M/yyyy, hh:mm:ss a");
    SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("d/M/yyyy");
    String consentDateTime = lead.commConsent;

    if (consentDateTime != null && !consentDateTime.trim().isEmpty()) {
        try {
            Date parsedDate = fullFormat.parse(consentDateTime);
            String consentDate = dateOnlyFormat.format(parsedDate);
            String todayDate = dateOnlyFormat.format(new Date());

            if (consentDate.equals(todayDate)) {
                test.log(Status.PASS, "✅ CommConsent date matches LS today: " + consentDateTime);
            } else {
                softAssert.fail("❌ commConsent date mismatch LS: expected today '" + todayDate + "', but got '" + consentDateTime + "'");
                test.log(Status.FAIL, "❌ commConsent date mismatch LS: expected today '" + todayDate + "', but got '" + consentDateTime + "'");
            }
        } catch (Exception e) {
            softAssert.fail("❌ Invalid commConsent date format LS: " + lead.commConsent);
            test.log(Status.FAIL, "❌ Invalid commConsent date format LS: " + lead.commConsent);
        }
    } else {
        softAssert.fail("❌ commConsent LS date not found");
        test.log(Status.FAIL, "❌ commConsent LS date not found");
    }

    // ✅ Prospect-ID
    if (!lead.prospectID.equals(lsqId)) {
        softAssert.fail("❌ Prospect-ID LS : expected '" + lsqId + "', actual '" + lead.prospectID + "'");
        test.log(Status.FAIL, "❌ Prospect-ID LS : expected '" + lsqId + "', actual '" + lead.prospectID + "'");
    } else {
        test.log(Status.PASS, "✅ Prospect-ID get from LS Cookies : " + lead.prospectID);
    }
  }

}
