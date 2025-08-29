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
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.Status;
import base.LeadSummary;
import base.base;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

public class Lp_Nw_MBA extends base {

    @BeforeMethod(alwaysRun = true)
            public void redirectToLp_Nw_MBA() throws InterruptedException {
                driver.get("https://qa-fe.amityonline.com/lp-nw/mba");
                Thread.sleep(2000);
            }

    @Test(priority = 1)
    public void Enquire_Now() throws Exception {
        test = reports.createTest("Enquire Now Form Lp Nw");
        closescholarshippopup();
        closePopupIfPresent();
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
        wait.until(ExpectedConditions.elementToBeClickable(hPom.lp_nw_enquireNow_Element));
        js.executeScript("arguments[0].click();", hPom.lp_nw_enquireNow_Element);
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
            Apom.bba_webinar_sibmit.click();
            // START HERE COOKIES
            String decodedVisited = null;
            String cleanVisited = null;
            Cookie visitedURLsCookie = waitForCookie("VisitedURLs", 5, 1000); // 5 retries, 1s gap

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
            Cookie lsqCookie = waitForCookie("LSQ_ID2", 5, 2000); // 5 retries, 1 second apart
            if (lsqCookie != null) {
                lsqId = lsqCookie.getValue();
                test.info("✅ LSQ_ID2 Cookie: " + lsqId);
            } else {
                test.fail("❌ LSQ_ID2 cookie not found after retries.");
            }

            String ApplicationUrl = null;
            Cookie appCookie = waitForCookie("ApplicationUrl", 5, 8000);
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
            Lp_Nw_MBA.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL, expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);
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
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime1 = System.nanoTime();
		long durationInSeconds1 = (endTime1 - startTime) / 1_000_000_000;
		test.info("Total Load Time: " + formatSeconds(endTime1 - startTime));
		checkVisualErrorsOnScreen();
		softAssert.assertAll();
    }


    @Test(priority = 2)
    public void yellow_strip() throws Exception {
        test = reports.createTest("Yellow Strip CTA (Landing Pages)");
        closescholarshippopup();
        closePopupIfPresent();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "BACHELOR OF BUSINESS ADMINISTRATION";
        String expectedSpecialization = "";
        String Enquirysource = "Enquire";
        String utm_medium = "request_a_callback";

		long startTime = System.nanoTime();
        try {
            WebElement strip_Element = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//span[contains(text(),'July’25 Session Admissions Open')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", strip_Element);
            String yellow_text = strip_Element.getText().trim();
            test.log(Status.PASS, "Yellow Strip Click : " + yellow_text);
            // Thread.sleep(2000);
            // WebElement left_image_element = wait.until(ExpectedConditions.visibilityOfElementLocated(
            //         By.xpath("//img[@class='LandingPageConnectModalTempleteOne_img__QYqIV']")));
            // left_image_element.isDisplayed();
            // test.log(Status.PASS, "Start Your Application! Image Displayed");
            Thread.sleep(1000);
            String originalWindow1 = driver.getWindowHandle();
            for (String windowHandle1 : driver.getWindowHandles()) {
                if (!windowHandle1.equals(originalWindow1)) {
                    driver.switchTo().window(windowHandle1);
                    break;
                }
            }
            Thread.sleep(2000);
            String randomMobileNumber = "231" + random.getRandomMobileNumber();
            String randomName = "TestQA" + " " + random.GetRandomName();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Random Name: " + randomName);
            test.info("Random Email: " + randomEmail);
            test.info("Random Mobile Number: " + randomMobileNumber);
            WebElement mobilefirstInput = driver.findElement(By.xpath("//input[@id='enter_phone_no']"));
            wait.until(ExpectedConditions.elementToBeClickable(mobilefirstInput));
            mobilefirstInput.sendKeys(randomMobileNumber);

            WebElement FullNamefirst = driver.findElement(By.xpath("//div[@class='basis-1/2']//input[@id='full_name']"));
            wait.until(ExpectedConditions.elementToBeClickable(FullNamefirst));
            FullNamefirst.sendKeys(randomName);

            WebElement emailfirst = driver.findElement(By.xpath("//div[@class='basis-1/2']//input[@id='email_id']"));
            wait.until(ExpectedConditions.elementToBeClickable(emailfirst));
            emailfirst.sendKeys(randomEmail);
            lppages.submit_button_mba.click();
            Thread.sleep(3000);
            // START HERE COOKIES
            String decodedVisited = null;
            String cleanVisited = null;
            Cookie visitedURLsCookie = waitForCookie("VisitedURLs", 5, 1000); // 5 retries, 1s gap

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
            Cookie lsqCookie = waitForCookie("LSQ_ID2", 5, 1000); // 5 retries, 1 second apart
            if (lsqCookie != null) {
                lsqId = lsqCookie.getValue();
                test.info("✅ LSQ_ID2 Cookie: " + lsqId);
            } else {
                test.fail("❌ LSQ_ID2 cookie not found after retries.");
            }

            String ApplicationUrl = null;
            Cookie appCookie = waitForCookie("ApplicationUrl", 5, 1000);
            if (appCookie != null) {
                String decodedAppUrl = URLDecoder.decode(appCookie.getValue(), StandardCharsets.UTF_8);
                ApplicationUrl = decodedAppUrl.replace("/lander", "/DashBoard");
                test.info("✅ Final Application URL Cookie: " + ApplicationUrl);
            } else {
                test.fail("❌ ApplicationUrl cookie not found after retries.");
            }

            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(lppages.OTPCell1)).sendKeys(random.GetOTP());
            lppages.OTPCell2.sendKeys(random.GetOTP());
            lppages.OTPCell3.sendKeys(random.GetOTP());
            lppages.OTPCell4.sendKeys(random.GetOTP());
            lppages.OTPCell5.sendKeys(random.GetOTP());
            lppages.OTPCell6.sendKeys(random.GetOTP());
            lppages.VerifyOTP.click();
            Thread.sleep(3000);
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate returned data
            Lp_Nw_MBA.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL, expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);

            Thread.sleep(5000);
            try {
                // Check for the "Thank You" message
                WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@class=' ThankYouBanner_Title__LtMZO']")));

                if (thankYouMessage.isDisplayed()) {
                    test.log(Status.SKIP, "API may have failed. Received Thank You message after OTP verification.");
                    test.info("Displayed Message: " + thankYouMessage.getText());
                    // System.out.println("Flow Stopped: " + thankYouMessage.getText());
                    // return; // Stop further execution
                }
            } catch (TimeoutException e) {
                test.info("No Thank You message found – continuing to check for Start Application button.");
            }
            Thread.sleep(7000);
            WebElement continueapplication = wait.until(ExpectedConditions.elementToBeClickable(lppages.continueapplicationElement));
			js.executeScript("arguments[0].click();", continueapplication);
            // Check for Start Application button

            try {
                WebElement isSuccess = wait
                        .until(ExpectedConditions.elementToBeClickable(lppages.startapplicationElement));
                if (isSuccess.isEnabled()) {
                    softAssert.assertTrue(true, "Start Application button enabled check");
                    test.log(Status.PASS, "Yellow Strip CTA Indian Journey Successful");
                    System.out.println("Yellow Strip CTA Indian Journey Successful");
                } else {
                    test.log(Status.FAIL, "Start Application button not enabled");
                    softAssert.fail("Start Application button not enabled");
                    System.out.println("Yellow Strip CTA Indian Journey Failed");
                }
            } catch (TimeoutException e) {
                test.log(Status.FAIL, "Start Application button did not appear: " + e.getMessage());
                softAssert.fail("Start Application button not found");
                System.out.println("Yellow Strip CTA Indian Journey Failed - Button not found");
            }
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
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl);
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Yellow Strip is Failed: " + e.getMessage());
            softAssert.fail(e.getMessage());
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime1 = System.nanoTime();
		long durationInSeconds1 = (endTime1 - startTime) / 1_000_000_000;
		test.info("Total Load Time: " + formatSeconds(endTime1 - startTime));
		checkVisualErrorsOnScreen();
		softAssert.assertAll();
    }

    // ----------Download---Brochure-------------

    @Test(priority = 3)
    public void Download_Brochure_India() throws Exception {
        test = reports.createTest("Download Brochure India");
        closescholarshippopup();
        closePopupIfPresent();
        long startTime = System.nanoTime();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "BLOCKCHAIN TECHNOLOGY AND MANAGEMENT";
        String Enquirysource = "Enquire";
		String utm_medium = "download_brochure";
        dbrochure.clickbrochure();
        String handle4 = driver.getWindowHandle();
        driver.switchTo().window(handle4);
        try {
            String randomName = "TestQA" + " " + random.GetRandomName();
            String randomMobileNumber = "233" + random.getRandomMobileNumber();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Random Name: " + randomName);
            test.info("Random Mobile Number: " + randomMobileNumber);
            test.info("Random Email: " + randomEmail);

            // dbrochure.selectdropdown();
            dbrochure.contactnumberElement.sendKeys(randomMobileNumber);
            dbrochure.usernamElement.sendKeys(randomName);
            dbrochure.useremailElement.sendKeys(randomEmail);
            dbrochure.usersubmitbuttonElement.click();
            // START HERE COOKIES
            String decodedVisited = null;
            String cleanVisited = null;
            Cookie visitedURLsCookie = waitForCookie("VisitedURLs", 5, 1000); // 5 retries, 1s gap

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
            Cookie appCookie = waitForCookie("ApplicationUrl", 5, 3000);
            if (appCookie != null) {
                String decodedAppUrl = URLDecoder.decode(appCookie.getValue(), StandardCharsets.UTF_8);
                ApplicationUrl = decodedAppUrl.replace("/lander", "/DashBoard");
                test.info("✅ Final Application URL Cookie: " + ApplicationUrl);
            } else {
                test.fail("❌ ApplicationUrl cookie not found after retries.");
            }

            Thread.sleep(6000);
            wait.until(ExpectedConditions.elementToBeClickable(dbrochure.OTPCell1));
            Thread.sleep(2000);
            dbrochure.OTPCell1.sendKeys(random.GetOTP());
            dbrochure.OTPCell2.sendKeys(random.GetOTP());
            dbrochure.OTPCell3.sendKeys(random.GetOTP());
            dbrochure.OTPCell4.sendKeys(random.GetOTP());
            dbrochure.OTPCell5.sendKeys(random.GetOTP());
            dbrochure.OTPCell6.sendKeys(random.GetOTP());

            dbrochure.VerifyOTP.click();
            Thread.sleep(3000);
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate Lead Summary
            Lp_Nw_MBA.validateLeadSummaryData(
                    lead, randomEmail, randomName, randomMobileNumber,
                    expectedURL, expectedProgram, expectedSpecialization,
                    lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);
            Thread.sleep(3000);
            // dbrochure.proceedbutton.click();
            // Thread.sleep(2000);
            // dbrochure.switchwindow();
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();

            while (iterator.hasNext()) {
                String currentHandle = iterator.next();
                if (!currentHandle.equals(handle4)) {
                    driver.switchTo().window(currentHandle);
                    break;
                }
            }

            Thread.sleep(2000);
            try {
                // Check if the "Thank You" message is shown due to API failure
                WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                if (thankYouMessage.isDisplayed()) {
                    test.log(Status.SKIP, "API may have failed. Received Thank You message after OTP verification.");
                    test.info("Displayed Message: " + thankYouMessage.getText());
                    // System.out.println("Flow Stopped: " + thankYouMessage.getText());
                    // return; // Exit test gracefully, skip the rest
                }
            } catch (TimeoutException e) {
                test.info("ℹ️ No “Thank You” message found — proceeding to validate Start Application button...");
            }

            WebElement continueapplication = wait.until(ExpectedConditions.elementToBeClickable(lppages.continueapplicationElement));
			js.executeScript("arguments[0].click();", continueapplication);

            // Proceed to normal flow – check for Start Application button
            try {
                WebElement isSuccess = wait
                        .until(ExpectedConditions.elementToBeClickable(dbrochure.startapplicationElement));
                if (isSuccess.isEnabled()) {
                    softAssert.assertTrue(true,
                            " Download brochure Indian flow - Start Application button enabled check");
                    test.log(Status.PASS, "✅ Indian Flow:  Download Brochure and verified successfully");
                    System.out.println(" Download brochure Indian Journey Successful");
                } else {
                    test.log(Status.FAIL,
                            " Download brochure Indian Flow Failed: Start Application button not enabled");
                    softAssert.fail(" Download brochure Indian Flow Failed - Start Application button not enabled");
                    System.out.println(" Download brochure Indian Journey Failed");
                }
            } catch (TimeoutException e) {
                test.log(Status.FAIL, " Download brochure Indian Flow Failed: Start Application button did not appear. "
                        + e.getMessage());
                softAssert.fail(" Download brochure Indian Flow Failed - Start Application button not found");
                System.out.println(" Download brochure Indian Journey Failed - Button not found");
            }
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
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl);
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Download Brochure for +91 india is not working" + e.getMessage());
            softAssert.fail("Download Brochure for +91 india is not working");
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime1 = System.nanoTime();
		long durationInSeconds1 = (endTime1 - startTime) / 1_000_000_000;
		test.info("Total Load Time: " + formatSeconds(endTime1 - startTime));
		checkVisualErrorsOnScreen();
		softAssert.assertAll();
    }


    // ----------Explore Curriculam-------------

    @Test(priority = 4)
    public void Explore_curriculam_India() throws Exception {
        test = reports.createTest("Explore Curriculam India");
        // closescholarshippopup();
        // closePopupIfPresent();
        long startTime = System.nanoTime();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "BLOCKCHAIN TECHNOLOGY AND MANAGEMENT";
        String Enquirysource = "Enquire";
		String utm_medium = "download_brochure";
        WebElement expolor_curriculam_button = driver.findElement(By.xpath("//span[normalize-space()='EXPLORE CURRICULUM']"));
        js.executeScript("arguments[0].scrollIntoView();", expolor_curriculam_button);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", expolor_curriculam_button);
        String handle4 = driver.getWindowHandle();
        driver.switchTo().window(handle4);
        try {
            String randomName = "TestQA" + " " + random.GetRandomName();
            String randomMobileNumber = "233" + random.getRandomMobileNumber();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Random Name: " + randomName);
            test.info("Random Mobile Number: " + randomMobileNumber);
            test.info("Random Email: " + randomEmail);

            // dbrochure.selectdropdown();
            dbrochure.contactnumberElement.sendKeys(randomMobileNumber);
            dbrochure.usernamElement.sendKeys(randomName);
            dbrochure.useremailElement.sendKeys(randomEmail);
            dbrochure.usersubmitbuttonElement.click();
            // START HERE COOKIES
            String decodedVisited = null;
            String cleanVisited = null;
            Cookie visitedURLsCookie = waitForCookie("VisitedURLs", 5, 1000); // 5 retries, 1s gap

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
            Cookie lsqCookie = waitForCookie("LSQ_ID2", 5, 1000); // 5 retries, 1 second apart
            if (lsqCookie != null) {
                lsqId = lsqCookie.getValue();
                test.info("✅ LSQ_ID2 Cookie: " + lsqId);
            } else {
                test.fail("❌ LSQ_ID2 cookie not found after retries.");
            }

            String ApplicationUrl = null;
            Cookie appCookie = waitForCookie("ApplicationUrl", 5, 1000);
            if (appCookie != null) {
                String decodedAppUrl = URLDecoder.decode(appCookie.getValue(), StandardCharsets.UTF_8);
                ApplicationUrl = decodedAppUrl.replace("/lander", "/DashBoard");
                test.info("✅ Final Application URL Cookie: " + ApplicationUrl);
            } else {
                test.fail("❌ ApplicationUrl cookie not found after retries.");
            }

            Thread.sleep(6000);
            wait.until(ExpectedConditions.elementToBeClickable(dbrochure.OTPCell1));
            Thread.sleep(2000);
            dbrochure.OTPCell1.sendKeys(random.GetOTP());
            dbrochure.OTPCell2.sendKeys(random.GetOTP());
            dbrochure.OTPCell3.sendKeys(random.GetOTP());
            dbrochure.OTPCell4.sendKeys(random.GetOTP());
            dbrochure.OTPCell5.sendKeys(random.GetOTP());
            dbrochure.OTPCell6.sendKeys(random.GetOTP());

            dbrochure.VerifyOTP.click();
            Thread.sleep(3000);
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate Lead Summary
            Lp_Nw_MBA.validateLeadSummaryData(
                    lead, randomEmail, randomName, randomMobileNumber,
                    expectedURL, expectedProgram, expectedSpecialization,
                    lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);
            Thread.sleep(3000);
            // dbrochure.proceedbutton.click();
            // Thread.sleep(2000);
            // dbrochure.switchwindow();
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();

            while (iterator.hasNext()) {
                String currentHandle = iterator.next();
                if (!currentHandle.equals(handle4)) {
                    driver.switchTo().window(currentHandle);
                    break;
                }
            }

            Thread.sleep(2000);
            try {
                // Check if the "Thank You" message is shown due to API failure
                WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                if (thankYouMessage.isDisplayed()) {
                    test.log(Status.SKIP, "API may have failed. Received Thank You message after OTP verification.");
                    test.info("Displayed Message: " + thankYouMessage.getText());
                    // System.out.println("Flow Stopped: " + thankYouMessage.getText());
                    // return; // Exit test gracefully, skip the rest
                }
            } catch (TimeoutException e) {
                test.info("ℹ️ No “Thank You” message found — proceeding to validate Start Application button...");
            }

            WebElement continueapplication = wait.until(ExpectedConditions.elementToBeClickable(lppages.continueapplicationElement));
			js.executeScript("arguments[0].click();", continueapplication);

            // Proceed to normal flow – check for Start Application button
            try {
                WebElement isSuccess = wait
                        .until(ExpectedConditions.elementToBeClickable(dbrochure.startapplicationElement));
                if (isSuccess.isEnabled()) {
                    softAssert.assertTrue(true,
                            " Download brochure Indian flow - Start Application button enabled check");
                    test.log(Status.PASS, "✅ Indian Flow:  Download Brochure and verified successfully");
                    System.out.println(" Download brochure Indian Journey Successful");
                } else {
                    test.log(Status.FAIL,
                            " Download brochure Indian Flow Failed: Start Application button not enabled");
                    softAssert.fail(" Download brochure Indian Flow Failed - Start Application button not enabled");
                    System.out.println(" Download brochure Indian Journey Failed");
                }
            } catch (TimeoutException e) {
                test.log(Status.FAIL, " Download brochure Indian Flow Failed: Start Application button did not appear. "
                        + e.getMessage());
                softAssert.fail(" Download brochure Indian Flow Failed - Start Application button not found");
                System.out.println(" Download brochure Indian Journey Failed - Button not found");
            }
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
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl);
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Download Brochure for +91 india is not working" + e.getMessage());
            softAssert.fail("Download Brochure for +91 india is not working");
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
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
