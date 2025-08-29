package All_Programs;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Date;
import java.util.List;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.Status;
import base.LeadSummary;
import base.base;
import pages.ApplyNowPOM;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

public class Blog extends base {

    @BeforeMethod(alwaysRun = true)
        public void redirectTo_Blog() throws InterruptedException {
            driver.get("https://qa-fe.amityonline.com/blog/cloud-security-engineer-salary-in-india");
            Thread.sleep(2000);
        }

    @Test(priority = 1)
    public void Enquire_Now_India() throws Exception {
        test = reports.createTest("Enquire Now India");
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
        // wait.until(ExpectedConditions.elementToBeClickable(hPom.lp_nw_enquireNow_Element));
        WebElement Enquire_button = driver.findElement(By.xpath("//div[@class='header-opt_applyBtnWrapper__NmhC_']//span[@class='ClientSideButton_btnText__5gMgu'][normalize-space()='ENQUIRE NOW']"));
        js.executeScript("arguments[0].click();", Enquire_button);
        test.log(Status.PASS, "Enquire Now button clicked");

        String randomMobileNumber = "233" + random.getRandomMobileNumber();
        String randomName = "TestQA " + random.GetRandomName();
        String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@yopmail.com";
        String emailPrefix = randomEmail.split("@")[0];

        try {
            test.info("Random Name:" + randomName);
            test.info("Random Mobile Number:" + randomMobileNumber);
            test.info("Random Email: " + randomEmail);

            WebElement mobilefirstInput = driver.findElement(By.xpath("//input[@id='mobile_no']"));
            wait.until(ExpectedConditions.elementToBeClickable(mobilefirstInput));
            mobilefirstInput.sendKeys(randomMobileNumber);

            WebElement FullNamefirst = driver.findElement(By.xpath("//div[@class='basis-1/2']//input[@id='full_name']"));
            wait.until(ExpectedConditions.elementToBeClickable(FullNamefirst));
            FullNamefirst.sendKeys(randomName);

            WebElement emailfirst = driver.findElement(By.xpath("//div[@class='basis-1/2']//input[@id='email_id']"));
            wait.until(ExpectedConditions.elementToBeClickable(emailfirst));
            emailfirst.sendKeys(randomEmail);

            Apom.submit_button_blog.click();
            try {
				String handle = driver.getWindowHandle();
				driver.switchTo().window(handle);
				wait.until(ExpectedConditions.elementToBeClickable(enquirNow.degreeElement)).sendKeys("PG");
				wait.until(ExpectedConditions.elementToBeClickable(enquirNow.prograElement)).sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");
				enquirNow.submit2elElement.click();
				Thread.sleep(2000);
				test.info("Degree and Program selection completed successfully.");
			} catch (Exception e) {
				test.warning("Degree/Program selection step skipped: " + e.getMessage());
			}
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
            Blog.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL, expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);
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
    public void Enquire_Now_International() throws Exception {
        test = reports.createTest("Enquire Now International");
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
        // wait.until(ExpectedConditions.elementToBeClickable(hPom.lp_nw_enquireNow_Element));
        WebElement Enquire_button = driver.findElement(By.xpath("//div[@class='header-opt_applyBtnWrapper__NmhC_']//span[@class='ClientSideButton_btnText__5gMgu'][normalize-space()='ENQUIRE NOW']"));
        js.executeScript("arguments[0].click();", Enquire_button);
        test.log(Status.PASS, "Enquire Now button clicked");

        String randomMobileNumber = "233" + random.getRandomMobileNumber();
        String randomName = "TestQA " + random.GetRandomName();
        String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@yopmail.com";
        String emailPrefix = randomEmail.split("@")[0];

        try {
            test.info("Random Name:" + randomName);
            test.info("Random Mobile Number:" + randomMobileNumber);
            test.info("Random Email: " + randomEmail);
            WebElement county_inter = driver.findElement(By.xpath("(//div[@id='country'])[2]"));
            county_inter.click();
            Thread.sleep(2000);
            WebElement uae_select = driver.findElement(By.xpath("(//span[@class='CustomDropdownGlobal_spanWidth__1yxyL'])[2]"));
            uae_select.click();

            WebElement mobilefirstInput = driver.findElement(By.xpath("//input[@id='mobile_no']"));
            wait.until(ExpectedConditions.elementToBeClickable(mobilefirstInput));
            mobilefirstInput.sendKeys(randomMobileNumber);

            WebElement FullNamefirst = driver.findElement(By.xpath("//div[@class='basis-1/2']//input[@id='full_name']"));
            wait.until(ExpectedConditions.elementToBeClickable(FullNamefirst));
            FullNamefirst.sendKeys(randomName);

            WebElement emailfirst = driver.findElement(By.xpath("//div[@class='basis-1/2']//input[@id='email_id']"));
            wait.until(ExpectedConditions.elementToBeClickable(emailfirst));
            emailfirst.sendKeys(randomEmail);

            Apom.submit_button_blog.click();
            try {
				String handle = driver.getWindowHandle();
				driver.switchTo().window(handle);
				wait.until(ExpectedConditions.elementToBeClickable(enquirNow.degreeElement)).sendKeys("PG");
				wait.until(ExpectedConditions.elementToBeClickable(enquirNow.prograElement)).sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");
				enquirNow.submit2elElement.click();
				Thread.sleep(2000);
				test.info("Degree and Program selection completed successfully.");
			} catch (Exception e) {
				test.warning("Degree/Program selection step skipped: " + e.getMessage());
			}
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

            // wait.until(ExpectedConditions.visibilityOf(Apom.OTPCell1)).sendKeys(random.GetOTP());
            // Apom.OTPCell2.sendKeys(random.GetOTP());
            // Apom.OTPCell3.sendKeys(random.GetOTP());
            // Apom.OTPCell4.sendKeys(random.GetOTP());
            // Apom.OTPCell5.sendKeys(random.GetOTP());
            // Apom.OTPCell6.sendKeys(random.GetOTP());

            // Apom.VerifyOTP.click();
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate returned data
            Blog.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL, expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);
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


    // ------------Apply Now CTA-Header------

    @Test(priority = 3)
    public void verifyApplyNowHeader_India() throws Exception {
        test = reports.createTest("Verify Apply Now - India (Header Section)");
        closescholarshippopup();
        closePopupIfPresent();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "Specialisation in Cyber SecurityT";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";

        // ========== INDIAN FLOW ==========
        long startTime = System.nanoTime(); // start time for Indian flow
        try {
            String originalWindow = driver.getWindowHandle();
            WebElement ApplynowBTN = wait.until(ExpectedConditions.elementToBeClickable(Apom.ApplynowBTN));
            ApplynowBTN.click();

            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            // Re-initialize POM and Wait in new window context
            Apom = new ApplyNowPOM(driver);
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(Apom.indianBTN)).click();

            // Generate and Log Random Data
            String randomMobileNumber = "239" + random.getRandomMobileNumber();
            String randomName = "TestQA" + " " + random.GetRandomName();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Random Name: " + randomName);
            test.info("Random Email: " + randomEmail);
            test.info("Random Mobile Number: " + randomMobileNumber);

            Apom.mobiElement.sendKeys(randomMobileNumber);
            Apom.nameElement.sendKeys(randomName);
            Apom.emailElement.sendKeys(randomEmail);
            wait.until(ExpectedConditions.visibilityOf(Apom.submitElement));
            Apom.submitElement.click();

            Thread.sleep(3000);
            try {
                String handle = driver.getWindowHandle();
                driver.switchTo().window(handle);
                wait.until(ExpectedConditions.elementToBeClickable(Apom.degreeElement)).sendKeys("PG");
                wait.until(ExpectedConditions.elementToBeClickable(Apom.prograElement))
                        .sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");
                wait.until(ExpectedConditions.visibilityOf(Apom.submit2elElement));
                Apom.submit2elElement.click();
                Thread.sleep(3000);
                test.info("Degree and Program selection completed successfully.");
            } catch (Exception e) {
                // test.warning("Degree/Program selection step skipped: " + e.getMessage());
                System.out.println("Skipped degree/program step: " + e.getMessage());
            }

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
            Cookie appCookie = waitForCookie("ApplicationUrl", 5, 2000);
            if (appCookie != null) {
                String decodedAppUrl = URLDecoder.decode(appCookie.getValue(), StandardCharsets.UTF_8);
                ApplicationUrl = decodedAppUrl.replace("/lander", "/DashBoard");
                test.info("✅ Final Application URL Cookie: " + ApplicationUrl);
            } else {
                test.fail("❌ ApplicationUrl cookie not found after retries.");
            }
            Thread.sleep(3000);

            wait.until(ExpectedConditions.visibilityOf(Apom.OTPCell1));
            Apom.OTPCell1.sendKeys(random.GetOTP());
            Apom.OTPCell2.sendKeys(random.GetOTP());
            Apom.OTPCell3.sendKeys(random.GetOTP());
            Apom.OTPCell4.sendKeys(random.GetOTP());
            Apom.OTPCell5.sendKeys(random.GetOTP());
            Apom.OTPCell6.sendKeys(random.GetOTP());
            wait.until(ExpectedConditions.elementToBeClickable(Apom.VerifyOTP));
            Apom.VerifyOTP.click();

            Thread.sleep(5000);
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate returned data
            Blog.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL,
                    expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium,
                    softAssert);

            try {
                // Check if the "Thank You" message is shown due to API failure
                WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                if (thankYouMessage.isDisplayed()) {
                    test.log(Status.SKIP, "API may have failed. Received Thank You message after OTP verification.");
                    test.info("Displayed Message: " + thankYouMessage.getText());
                    System.out.println("Flow Stopped: " + thankYouMessage.getText());
                    return; // Exit test gracefully, skip the rest
                }
            } catch (TimeoutException e) {
                test.info("No Thank You message found – continuing to check for Start Application button.");
            }

            // Proceed to normal flow – check for Start Application button
            try {
                WebElement isSuccess = wait
                        .until(ExpectedConditions.elementToBeClickable(Apom.startapplicationElement));
                if (isSuccess.isEnabled()) {
                    softAssert.assertTrue(true, "Indian flow - Start Application button enabled check");
                    test.log(Status.PASS, "Apply Now Header Indian Journey Successful");
                    System.out.println("Apply Now Header Indian Journey Successful");
                } else {
                    test.log(Status.FAIL, "Indian Flow Failed: Start Application button not enabled");
                    softAssert.fail("Indian Flow Failed - Start Application button not enabled");
                    System.out.println("Apply Now Header Indian Journey Failed");
                }
            } catch (TimeoutException e) {
                test.log(Status.FAIL, "Indian Flow Failed: Start Application button did not appear. " + e.getMessage());
                softAssert.fail("Indian Flow Failed - Start Application button not found");
                System.out.println("Apply Now Header Indian Journey Failed - Button not found");
            }

            String student_login = driver.getCurrentUrl();
            // Remove http:// or https:// from both URLs
            String actualTrimmed = student_login.replaceFirst("^https?://", "");
            String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");

            if (actualTrimmed.equals(expectedTrimmed)) {
                System.out.println("✅ URL matched: " + student_login);
                test.log(Status.PASS,
                        "✅ URL matched Application Url of Cookies to application journey page : " + student_login);
            } else {
                System.out.println("❌ URL mismatch.");
                System.out.println("Expected: " + ApplicationUrl);
                System.out.println("Actual: " + student_login);
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Indian Flow Failed: " + e.getMessage());
            softAssert.fail("Indian Flow Failed - " + e.getMessage());
            System.out.println("Apply Now Indian Journey Failed");
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime = System.nanoTime();
        long durationInSeconds = (endTime - startTime) / 1_000_000_000;
        test.info("Total Load Time: " + formatSeconds(endTime - startTime));
        softAssert.assertAll();
    }


    @Test(priority = 4)
    public void verifyApplyNowHeader_International() throws Exception {
        test = reports.createTest("Verify Apply Now - International (Header Section)");
        closescholarshippopup();
        closePopupIfPresent();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "Specialisation in Cyber SecurityT";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";
        long startTime = System.nanoTime();
        // ========== INTERNATIONAL FLOW ==========

        startTime = System.nanoTime(); // reset time for international flow
        try {
            String originalWindow1 = driver.getWindowHandle();
            WebElement ApplynowBTN2 = wait.until(ExpectedConditions.elementToBeClickable(Apom.ApplynowBTN));
            ApplynowBTN2.click();

            for (String windowHandle1 : driver.getWindowHandles()) {
                if (!windowHandle1.equals(originalWindow1)) {
                    driver.switchTo().window(windowHandle1);
                    break;
                }
            }

            // 👇 Re-initialize POM and Wait in new window context
            Apom = new ApplyNowPOM(driver);
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(Apom.internationalElement)).click();
            Apom.countrycodeElement.click();
            Thread.sleep(1000);
            WebElement unitedElement = driver
                    .findElement(By.xpath("//div[@class='CustomDropdownGlobal_dropdownItem__Qu0Dw'][1]"));
            Actions codeActions = new Actions(driver);
            codeActions.moveToElement(unitedElement).click().perform();

            String randomMobileNumber = "233" + random.getRandomMobileNumber();
            String randomName = "TestQA" + " " + random.GetRandomName();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Random Name: " + randomName);
            test.info("Random Email: " + randomEmail);
            test.info("Random Mobile Number: " + randomMobileNumber);

            wait.until(ExpectedConditions.elementToBeClickable(Apom.nameElement));
            Apom.nameElement.sendKeys(randomName);
            Apom.mobiElement.sendKeys(randomMobileNumber);
            Apom.emailElement.sendKeys(randomEmail);
            wait.until(ExpectedConditions.visibilityOf(Apom.submitElement));
            Apom.submitElement.click();

            try {
                String handle2 = driver.getWindowHandle();
                driver.switchTo().window(handle2);

                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(Apom.degreeElement)).sendKeys("PG");
                wait.until(ExpectedConditions.elementToBeClickable(Apom.prograElement))
                        .sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");
                wait.until(ExpectedConditions.visibilityOf(Apom.submit2elElement));
                Apom.submit2elElement.click();
                test.info("Degree and Program selection completed successfully.");
            } catch (Exception e) {
                test.warning("Degree/Program selection step skipped: " + e.getMessage());
                System.out.println("Skipped degree/program step: " + e.getMessage());
            }
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

            Thread.sleep(7000);
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate returned data
            Blog.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL,
                    expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium,
                    softAssert);

            try {
                // Check if the "Thank You" message is shown due to API failure
                WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                if (thankYouMessage.isDisplayed()) {
                    test.log(Status.SKIP, "API may have failed. Received Thank You message after OTP verification.");
                    test.info("Displayed Message: " + thankYouMessage.getText());
                    System.out.println("Flow Stopped: " + thankYouMessage.getText());
                    return; // Exit test gracefully, skip the rest
                }
            } catch (TimeoutException e) {
                test.info("No Thank You message found – continuing to check for Start Application button.");
            }

            // Proceed to normal flow – check for Start Application button
            try {
                WebElement isSuccess = wait
                        .until(ExpectedConditions.elementToBeClickable(Apom.startapplicationElement));
                if (isSuccess.isEnabled()) {
                    softAssert.assertTrue(true, "International flow - Start Application button enabled check");
                    test.log(Status.PASS, "Apply Now Header International Journey Successful");
                    System.out.println("Apply Now Header International Journey Successful");
                } else {
                    test.log(Status.FAIL, "International Flow Failed: Start Application button not enabled");
                    softAssert.fail("International Flow Failed - Start Application button not enabled");
                    System.out.println("Apply Now Header International Journey Failed");
                }
            } catch (TimeoutException e) {
                test.log(Status.FAIL,
                        "International Flow Failed: Start Application button did not appear. " + e.getMessage());
                softAssert.fail("International Flow Failed - Start Application button not found");
                System.out.println("Apply Now Header International Journey Failed - Button not found");
            }
            Thread.sleep(5000);
            String student_login = driver.getCurrentUrl();
            // Remove http:// or https:// from both URLs
            String actualTrimmed = student_login.replaceFirst("^https?://", "");
            String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");

            if (actualTrimmed.equals(expectedTrimmed)) {
                System.out.println("✅ URL matched: " + student_login);
                test.log(Status.PASS,
                        "✅ URL matched Application Url of Cookies to application journey page : " + student_login);
            } else {
                System.out.println("❌ URL mismatch.");
                System.out.println("Expected: " + ApplicationUrl);
                System.out.println("Actual: " + student_login);
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "International Flow Failed: " + e.getMessage());
            softAssert.fail("International Flow Failed - " + e.getMessage());
            System.out.println("Apply Now International Journey Failed");
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime1 = System.nanoTime();
        long durationInSeconds1 = (endTime1 - startTime) / 1_000_000_000;
        test.info("Total Load Time : " + formatSeconds(endTime1 - startTime));
        checkVisualErrorsOnScreen();
        softAssert.assertAll();
    }


    @Test(priority = 5)
    public void verifyBlueRFISection_India_International() throws InterruptedException {
        test = reports.createTest("Verify Blue RFI - Indian & International(HomePage)");
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "Specialisation in Cyber Security";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";
        long startTime = System.nanoTime();
        try {
            // Close popups and initialize
            closescholarshippopup();
            closePopupIfPresent();
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // ========== INDIAN FLOW ==========
            try {
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(
                        By.xpath("//h2[normalize-space()='Apply Now']")));
                Thread.sleep(2000);

                String randomName = "TestQA" + " " + random.GetRandomName();
                String randomMobileNumber = "239" + random.getRandomMobileNumber();
                String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

                test.info("Random Name: " + randomName);
                test.info("Random Email: " + randomEmail);
                test.info("Random Mobile Number: " + randomMobileNumber);
                wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.full_name_Element));
                BlueRFI.full_name_Element.sendKeys(randomName);
                BlueRFI.phone_number_Element.sendKeys(randomMobileNumber);
                BlueRFI.email_section_Element.sendKeys(randomEmail);

                WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.submitbuttonElement));
                js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", submitBtn);

                try {
                    String handle = driver.getWindowHandle();
                    driver.switchTo().window(handle);

                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.degree_select_Element)).sendKeys("PG");
                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.program_select_Element))
                            .sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");

                    WebElement submitBtn2 = wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.submittbutton2));
                    js.executeScript("arguments[0].click();", submitBtn2);

                    test.info("Degree and Program selection completed successfully.");
                } catch (Exception e) {
                    test.warning("Degree/Program selection step skipped: " + e.getMessage());
                }

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
                wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.OTPCell1));
                BlueRFI.OTPCell1.sendKeys(random.GetOTP());
                BlueRFI.OTPCell2.sendKeys(random.GetOTP());
                BlueRFI.OTPCell3.sendKeys(random.GetOTP());
                BlueRFI.OTPCell4.sendKeys(random.GetOTP());
                BlueRFI.OTPCell5.sendKeys(random.GetOTP());
                BlueRFI.OTPCell6.sendKeys(random.GetOTP());
                BlueRFI.VerifyOTP.click();
                Thread.sleep(3000);
                LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
                // ✅ Validate Lead Summary
                Blog.validateLeadSummaryData(
                        lead, randomEmail, randomName, randomMobileNumber,
                        expectedURL, expectedProgram, expectedSpecialization,
                        lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);

                try {
                    // Check if the "Thank You" message is shown due to API failure
                    WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                    if (thankYouMessage.isDisplayed()) {
                        test.log(Status.SKIP,
                                "API may have failed. Received Thank You message after OTP verification.");
                        test.info("Displayed Message: " + thankYouMessage.getText());
                        System.out.println("Flow Stopped: " + thankYouMessage.getText());
                        return; // Exit test gracefully, skip the rest
                    }
                } catch (TimeoutException e) {
                    test.info("No Thank You message found – continuing to check for Start Application button.");
                }

                // Proceed to normal flow – check for Start Application button
                try {
                    WebElement isSuccess = wait
                            .until(ExpectedConditions.elementToBeClickable(BlueRFI.startapplicationElement));
                    if (isSuccess.isEnabled()) {
                        softAssert.assertTrue(true, "Blue RFI Indian flow - Start Application button enabled check");
                        test.log(Status.PASS, "Blue RFI Apply Now Indian Journey Successful");
                        System.out.println("Blue RFI Apply Now Indian Journey Successful");
                    } else {
                        test.log(Status.FAIL, "Blue RFI Indian Flow Failed: Start Application button not enabled");
                        softAssert.fail("Blue RFI Indian Flow Failed - Start Application button not enabled");
                        System.out.println("Blue RFI Apply Now Indian Journey Failed");
                    }
                } catch (TimeoutException e) {
                    test.log(Status.FAIL,
                            "Blue RFI Indian Flow Failed: Start Application button did not appear. " + e.getMessage());
                    softAssert.fail("Blue RFI Indian Flow Failed - Start Application button not found");
                    System.out.println("Blue RFI Apply Now Indian Journey Failed - Button not found");
                }
                // Thread.sleep(6000);
                String student_login = driver.getCurrentUrl();
                // Remove http:// or https:// from both URLs
                String actualTrimmed = student_login.replaceFirst("^https?://", "");
                String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");

                if (actualTrimmed.equals(expectedTrimmed)) {
                    System.out.println("✅ URL matched: " + student_login);
                    test.log(Status.PASS,
                            "✅ URL matched Application Url of Cookies to application journey page : " + student_login);
                } else {
                    System.out.println("❌ URL mismatch.");
                    System.out.println("Expected: " + ApplicationUrl);
                    System.out.println("Actual: " + student_login);
                    test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
                }
            } catch (Exception e) {
                test.log(Status.FAIL, "Indian Flow Failed: " + e.getMessage());
                softAssert.fail("Indian Flow Failed - " + e.getMessage());
                System.out.println("Blue RFI Indian Journey failed");
            } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
           } 
            Thread.sleep(2000);
            long endTime = System.nanoTime();
            long durationInSeconds = (endTime - startTime) / 1_000_000_000;
            test.info("Total Load Time: " + formatSeconds(endTime - startTime));
            driver.navigate().back();
            Thread.sleep(2000);

            // ========== INTERNATIONAL FLOW ==========
            startTime = System.nanoTime(); // restart time tracking
            try {
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(
                        By.xpath("//h2[normalize-space()='Apply Now']")));
                Thread.sleep(2000);

                String randomName = "TestQA" + " " + random.GetRandomName();
                String randomMobileNumber = "239" + random.getRandomMobileNumber2();
                String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

                test.info("Random Name: " + randomName);
                test.info("Random Email: " + randomEmail);
                test.info("Random Mobile Number: " + randomMobileNumber);

                wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.full_name_Element));
                BlueRFI.countrycodElement.click();

                WebElement unitedElement = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='CustomDropdownGlobal_dropdown__2uWby']//div[9]//span[1]")));
                String countryCode = unitedElement.getText();
                test.info("country Code: " + countryCode);
                Actions codeActions = new Actions(driver);
                codeActions.moveToElement(unitedElement).click().perform();

                BlueRFI.full_name_Element.sendKeys(randomName);
                BlueRFI.phone_number_Element.sendKeys(randomMobileNumber);
                BlueRFI.email_section_Element.sendKeys(randomEmail);

                WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.submitbuttonElement));
                js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", submitBtn);

                try {
                    String handle = driver.getWindowHandle();
                    driver.switchTo().window(handle);

                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.degree_select_Element)).sendKeys("PG");
                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.program_select_Element))
                            .sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");

                    WebElement submitBtn2 = wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.submittbutton2));
                    js.executeScript("arguments[0].click();", submitBtn2);

                    test.info("Degree and Program selection completed successfully.");
                } catch (Exception e) {
                    test.warning("Degree/Program selection step skipped: " + e.getMessage());
                }

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

                try {
                    // Check if the "Thank You" message is shown due to API failure
                    WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                    if (thankYouMessage.isDisplayed()) {
                        test.log(Status.SKIP,
                                "API may have failed. Received Thank You message after OTP verification.");
                        test.info("Displayed Message: " + thankYouMessage.getText());
                        System.out.println("Flow Stopped: " + thankYouMessage.getText());
                        return; // Exit test gracefully, skip the rest
                    }
                } catch (TimeoutException e) {
                    test.info("No Thank You message found – continuing to check for Start Application button.");
                }
                LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
                // ✅ Validate Lead Summary
                Blog.validateLeadSummaryData(
                        lead, randomEmail, randomName, randomMobileNumber,
                        expectedURL, expectedProgram, expectedSpecialization,
                        lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);

                // Proceed to normal flow – check for Start Application button
                try {
                    WebElement isSuccess = wait.until(ExpectedConditions.visibilityOf(BlueRFI.startapplicationElement));
                    if (isSuccess.isDisplayed()) {
                        softAssert.assertTrue(true,
                                "Blue RFI International flow - Start Application button enabled check");
                        test.log(Status.PASS, "Blue RFI Apply Now International Journey Successful");
                        System.out.println("Blue RFI Apply Now International Journey Successful");
                    } else {
                        test.log(Status.FAIL,
                                "Blue RFI International Flow Failed: Start Application button not enabled");
                        softAssert.fail("Blue RFI International Flow Failed - Start Application button not enabled");
                        System.out.println("Blue RFI Apply Now International Journey Failed");
                    }
                } catch (TimeoutException e) {
                    test.log(Status.FAIL,
                            "Blue RFI International Flow Failed: Start Application button did not appear. "
                                    + e.getMessage());
                    softAssert.fail("Blue RFI International Flow Failed - Start Application button not found");
                    System.out.println("Blue RFI Apply Now International Journey Failed - Button not found");
                }
                // Thread.sleep(6000);
                String student_login = driver.getCurrentUrl();
                // Remove http:// or https:// from both URLs
                String actualTrimmed = student_login.replaceFirst("^https?://", "");
                String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");

                if (actualTrimmed.equals(expectedTrimmed)) {
                    System.out.println("✅ URL matched: " + student_login);
                    test.log(Status.PASS,
                            "✅ URL matched Application Url of Cookies to application journey page : " + student_login);
                } else {
                    System.out.println("❌ URL mismatch.");
                    System.out.println("Expected: " + ApplicationUrl);
                    System.out.println("Actual: " + student_login);
                    test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
                }
            } catch (Exception e) {
                long endTime1 = System.nanoTime();
                long durationInSeconds1 = (endTime1 - startTime) / 1_000_000_000;
                test.log(Status.FAIL, "International Flow Failed: " + e.getMessage());
                test.info("Load Time on Failure (International): " + durationInSeconds1 + " seconds");
                softAssert.fail("International Flow Failed - " + e.getMessage());
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Blue RFI Test Failed: " + e.getMessage());
            softAssert.fail("Blue RFI Test Failed - " + e.getMessage());
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime1 = System.nanoTime();
        long durationInSeconds1 = (endTime1 - startTime) / 1_000_000_000;
        test.info("Total Load Time : " + formatSeconds(endTime1 - startTime));
        checkVisualErrorsOnScreen();
        softAssert.assertAll();
    }


    @Test(priority = 6)
    public void sticky_buttons_Call_icon_India() throws InterruptedException {
        test = reports.createTest("Page sticky button India (Call Icon)");
        closescholarshippopup();
        closePopupIfPresent();
        long startTime = System.nanoTime();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";
        // Phone sticky icon start here
        try {
            wait.until(ExpectedConditions.elementToBeClickable(Sticky_Buttons_Call_whats.call_icon_Element));
            js.executeScript("arguments[0].click();", Sticky_Buttons_Call_whats.call_icon_Element);
            Thread.sleep(3000);
            Apom.indianBTN.click();
            // Generate and Log Random Data
            String randomMobileNumber = "234" + random.getRandomMobileNumber();
            String randomName = "TestQA" + " " + random.GetRandomName();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Random Name: " + randomName);
            test.info("Random Email: " + randomEmail);
            test.info("Random Mobile Number: " + randomMobileNumber);

            Apom.mobiElement.sendKeys(randomMobileNumber);
            Apom.nameElement.sendKeys(randomName);
            Apom.emailElement.sendKeys(randomEmail);
            Apom.submitElement.click();

            Thread.sleep(3000);
            try {
                String handle = driver.getWindowHandle();
                driver.switchTo().window(handle);
                wait.until(ExpectedConditions.elementToBeClickable(Apom.degreeElement)).sendKeys("PG");
                wait.until(ExpectedConditions.elementToBeClickable(Apom.prograElement))
                        .sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");

                Apom.submit2elElement.click();
                Thread.sleep(6000);
                test.info("Degree and Program selection completed successfully.");
            } catch (Exception e) {
                // test.warning("Degree/Program selection step skipped: " + e.getMessage());
                System.out.println("Skipped degree/program step: " + e.getMessage());
            }

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
            wait.until(ExpectedConditions.elementToBeClickable(Apom.OTPCell1));
            Thread.sleep(6000);
            Apom.OTPCell1.sendKeys(random.GetOTP());
            Apom.OTPCell2.sendKeys(random.GetOTP());
            Apom.OTPCell3.sendKeys(random.GetOTP());
            Apom.OTPCell4.sendKeys(random.GetOTP());
            Apom.OTPCell5.sendKeys(random.GetOTP());
            Apom.OTPCell6.sendKeys(random.GetOTP());

            Apom.VerifyOTP.click();
            Thread.sleep(7000);
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate Lead Summary
            Blog.validateLeadSummaryData(
                    lead, randomEmail, randomName, randomMobileNumber,
                    expectedURL, expectedProgram, expectedSpecialization,
                    lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);

            try {
                // Check if the "Thank You" message is shown due to API failure
                WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                if (thankYouMessage.isDisplayed()) {
                    test.log(Status.SKIP, "API may have failed. Received Thank You message after OTP verification.");
                    test.info("Displayed Message: " + thankYouMessage.getText());
                    System.out.println("Flow Stopped: " + thankYouMessage.getText());
                    return; // Exit test gracefully, skip the rest
                }
            } catch (TimeoutException e) {
                test.info("No Thank You message found – continuing to check for Start Application button.");
            }
            // Proceed to normal flow – check for Start Application button
            try {
                WebElement isSuccess = wait
                        .until(ExpectedConditions.elementToBeClickable(Apom.startapplicationElement));
                if (isSuccess.isEnabled()) {
                    softAssert.assertTrue(true, "Call Icon Indian flow - Start Application button enabled check");
                    test.log(Status.PASS, "Call Icon Indian Journey Successful");
                    System.out.println("Call Icon Indian Journey Successful");
                } else {
                    test.log(Status.FAIL, "Call Icon Indian Flow Failed: Start Application button not enabled");
                    softAssert.fail("Call Icon Indian Flow Failed - Start Application button not enabled");
                    System.out.println("Call Icon Indian Journey Failed");
                }
            } catch (TimeoutException e) {
                test.log(Status.FAIL,
                        "Call Icon Indian Flow Failed: Start Application button did not appear. " + e.getMessage());
                softAssert.fail("Call Icon Indian Flow Failed - Start Application button not found");
                System.out.println("Call Icon Indian Journey Failed - Button not found");
            }
            Thread.sleep(6000);
            String student_login = driver.getCurrentUrl();
            // Remove http:// or https:// from both URLs
            String actualTrimmed = student_login.replaceFirst("^https?://", "");
            String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");

            if (actualTrimmed.equals(expectedTrimmed)) {
                System.out.println("✅ URL matched: " + student_login);
                test.log(Status.PASS,
                        "✅ URL matched Application Url of Cookies to application journey page : " + student_login);
            } else {
                System.out.println("❌ URL mismatch.");
                System.out.println("Expected: " + ApplicationUrl);
                System.out.println("Actual: " + student_login);
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Call Icon +91 Indian Journey is Failed: " + e.getMessage());
            softAssert.fail("Call Icon +91 Journey is Failed" + e.getMessage());
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime = System.nanoTime();
        long durationInSeconds = (endTime - startTime) / 1_000_000_000;
        test.info("⏱️ Page Load Time: " + formatSeconds(endTime - startTime));
        checkVisualErrorsOnScreen();
        softAssert.assertAll();
    }


    @Test(priority = 7)
    public void sticky_buttons_Call_icon_International() throws InterruptedException {
        test = reports.createTest("Page sticky button International (Call Icon)");
        closescholarshippopup();
        closePopupIfPresent();
        long startTime = System.nanoTime();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";
        // ========== INTERNATIONAL FLOW ==========
        try {
            wait.until(ExpectedConditions.elementToBeClickable(Sticky_Buttons_Call_whats.call_icon_Element));
            js.executeScript("arguments[0].click();", Sticky_Buttons_Call_whats.call_icon_Element);
            String originalWindow1 = driver.getWindowHandle();
            String ApplicationUrl = null;
            // long startTime = System.nanoTime();
            for (String windowHandle1 : driver.getWindowHandles()) {
                if (!windowHandle1.equals(originalWindow1)) {
                    driver.switchTo().window(windowHandle1);
                    break;
                }
                // 👇 Re-initialize POM and Wait in new window context
                Apom = new ApplyNowPOM(driver);
                Thread.sleep(2000);
                try {
                    Apom.internationalElement.click();
                    Apom.countrycodeElement.click();
                    Thread.sleep(1000);
                    WebElement unitedElement = driver
                            .findElement(By.xpath("//div[@class='CustomDropdownGlobal_dropdownItem__Qu0Dw'][1]"));
                    Actions codeActions = new Actions(driver);
                    codeActions.moveToElement(unitedElement).click().perform();

                    String randomMobileNumber = "235" + random.getRandomMobileNumber();
                    String randomName = "TestQA" + " " + random.GetRandomName();
                    String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

                    test.info("Random Name: " + randomName);
                    test.info("Random Email: " + randomEmail);
                    test.info("Random Mobile Number: " + randomMobileNumber);

                    Apom.nameElement.sendKeys(randomName);
                    Apom.mobiElement.sendKeys(randomMobileNumber);
                    Apom.emailElement.sendKeys(randomEmail);
                    Apom.submitElement.click();

                    Thread.sleep(2000);
                    test.info("Degree and Program selection completed successfully.");
                    try {
                        String handle2 = driver.getWindowHandle();
                        driver.switchTo().window(handle2);
                        wait.until(ExpectedConditions.elementToBeClickable(Apom.degreeElement)).sendKeys("PG");
                        wait.until(ExpectedConditions.elementToBeClickable(Apom.prograElement))
                                .sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");

                        Apom.submit2elElement.click();
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        // test.warning("Degree/Program selection step skipped: " + e.getMessage());
                        System.out.println("Skipped degree/program step: " + e.getMessage());
                    }

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

                    // String ApplicationUrl = null;
                    Cookie appCookie = waitForCookie("ApplicationUrl", 5, 1000);
                    if (appCookie != null) {
                        String decodedAppUrl = URLDecoder.decode(appCookie.getValue(), StandardCharsets.UTF_8);
                        ApplicationUrl = decodedAppUrl.replace("/lander", "/DashBoard");
                        test.info("✅ Final Application URL Cookie: " + ApplicationUrl);
                    } else {
                        test.fail("❌ ApplicationUrl cookie not found after retries.");
                    }
                    Thread.sleep(2000);
                    LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
                    // ✅ Validate Lead Summary
                    Blog.validateLeadSummaryData(
                            lead, randomEmail, randomName, randomMobileNumber,
                            expectedURL, expectedProgram, expectedSpecialization,
                            lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);

                    try {
                        // Check if the "Thank You" message is shown due to API failure
                        WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                        if (thankYouMessage.isDisplayed()) {
                            test.log(Status.SKIP,
                                    "API may have failed. Received Thank You message after OTP verification.");
                            test.info("Displayed Message: " + thankYouMessage.getText());
                            System.out.println("Flow Stopped: " + thankYouMessage.getText());
                            return; // Exit test gracefully, skip the rest
                        }
                    } catch (TimeoutException e) {
                        test.info("No Thank You message found – continuing to check for Start Application button.");
                    }

                    // Proceed to normal flow – check for Start Application button
                    try {
                        WebElement isSuccess = wait
                                .until(ExpectedConditions.elementToBeClickable(Apom.startapplicationElement));
                        if (isSuccess.isEnabled()) {
                            softAssert.assertTrue(true, " International flow - Start Application button enabled check");
                            test.log(Status.PASS, " International Journey Successful");
                            System.out.println(" International Journey Successful");
                        } else {
                            test.log(Status.FAIL, " International Flow Failed: Start Application button not enabled");
                            softAssert.fail(" International Flow Failed - Start Application button not enabled");
                            System.out.println(" International Journey Failed");
                        }
                    } catch (TimeoutException e) {
                        test.log(Status.FAIL,
                                " International Flow Failed: Start Application button did not appear. "
                                        + e.getMessage());
                        softAssert.fail(" International Flow Failed - Start Application button not found");
                        System.out.println(" International Journey Failed - Button not found");
                    }
                } catch (Exception e) {
                    test.log(Status.FAIL,
                            "Apply Now 'United Arab Emirates' +971 International Journey is Failed" + e.getMessage());
                    softAssert.fail("Apply Now 'United Arab Emirates' +971 Journey is Failed" + e.getMessage());
                }
                Thread.sleep(6000);
                String student_login = driver.getCurrentUrl();
                // Remove http:// or https:// from both URLs
                String actualTrimmed = student_login.replaceFirst("^https?://", "");
                String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");

                if (actualTrimmed.equals(expectedTrimmed)) {
                    System.out.println("✅ URL matched: " + student_login);
                    test.log(Status.PASS,
                            "✅ URL matched Application Url of Cookies to application journey page : " + student_login);
                } else {
                    System.out.println("❌ URL mismatch.");
                    System.out.println("Expected: " + ApplicationUrl);
                    System.out.println("Actual: " + student_login);
                    test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
                }
            }
        } catch (Exception e) {
            test.log(Status.FAIL,
                    "Apply Now 'United Arab Emirates' +971 International Journey is Failed" + e.getMessage());
            softAssert.fail("Apply Now 'United Arab Emirates' +971 Journey is Failed" + e.getMessage());
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        } 
        long endTime = System.nanoTime();
        long durationInSeconds = (endTime - startTime) / 1_000_000_000;
        test.info("⏱️ Total Load Time : " + formatSeconds(endTime - startTime));
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
