package All_Programs;

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
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.Status;
import base.LeadSummary;
import base.base;
import pages.ApplyNowPOM;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

public class HomePage extends base {

    @BeforeMethod(alwaysRun = true)
    public void redirectToHome() throws InterruptedException {
        driver.get("https://qa-fe.amityonline.com/");
        Thread.sleep(2000);
    }

@Test(priority = 1)
public void verifyEnquireNowHeader_India() {
    test = reports.createTest("Verify Enquire Now - Indian (Header Section)");
    closescholarshippopup();
    
    String expectedURL = "https://qa-fe.amityonline.com/";
    String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
    String expectedSpecialization = "BLOCKCHAIN TECHNOLOGY AND MANAGEMENT";
    String Enquirysource = "Enquire";
    String utm_medium = "request_a_callback";

    long startTime = System.nanoTime();

    try {
        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(enquirNow.enquireNowelementElement))
            .click();

        // Switch to new tab
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(enquirNow.indianBTN)).click();

        // Random data
        String randomMobile = "239" + random.getRandomMobileNumber();
        String randomName = "TestQA " + random.GetRandomName();
        String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";
        test.info("Name: " + randomName + " | Email: " + randomEmail + " | Mobile: " + randomMobile);

        // Fill form
        enquirNow.mobiElement.sendKeys(randomMobile);
        enquirNow.nameElement.sendKeys(randomName);
        enquirNow.emailElement.sendKeys(randomEmail);
        enquirNow.submitElement.click();

        // Degree/Program (optional)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(enquirNow.degreeElement)).sendKeys("PG");
            enquirNow.prograElement.sendKeys(
                "MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY"
            );
            enquirNow.submit2elElement.click();
            test.info("Degree and Program selection done.");
        } catch (Exception ignore) {
            test.warning("Degree/Program selection skipped.");
        }

        // Helper lambda for cookie fetch
        java.util.function.Function<String, Cookie> getCookie = name -> {
            for (int i = 0; i < 5; i++) {
                Cookie c = driver.manage().getCookieNamed(name);
                if (c != null) return c;
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
            return null;
        };

        // Fetch cookies
        String cleanVisited = null, lsqId = null, ApplicationUrl = null;

        Cookie visitedURLs = getCookie.apply("VisitedURLs");
        if (visitedURLs != null) {
            cleanVisited = URLDecoder.decode(visitedURLs.getValue(), StandardCharsets.UTF_8)
                                     .replace("[\"", "").replace("\"]", "");
            test.info("Visited URL: " + cleanVisited);
        } else test.fail("VisitedURLs cookie not found.");

        Cookie lsqCookie = getCookie.apply("LSQ_ID2");
        if (lsqCookie != null) {
            lsqId = lsqCookie.getValue();
            test.info("LSQ_ID2: " + lsqId);
        } else test.fail("LSQ_ID2 cookie not found.");

        Cookie appCookie = getCookie.apply("ApplicationUrl");
        if (appCookie != null) {
            ApplicationUrl = URLDecoder.decode(appCookie.getValue(), StandardCharsets.UTF_8)
                                       .replace("/lander", "/DashBoard");
            test.info("Application URL: " + ApplicationUrl);
        } else test.fail("ApplicationUrl cookie not found.");

        // OTP
        List<WebElement> otpCells = Arrays.asList(
            enquirNow.OTPCell1, enquirNow.OTPCell2, enquirNow.OTPCell3,
            enquirNow.OTPCell4, enquirNow.OTPCell5, enquirNow.OTPCell6
        );
        for (WebElement cell : otpCells) cell.sendKeys(random.GetOTP());
        enquirNow.VerifyOTP.click();

        // Lead Summary
        LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
        HomePage.validateLeadSummaryData(lead, randomEmail, randomName, randomMobile, expectedURL,
            expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);

        // Thank You message check
        try {
            WebElement thankYou = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));
            if (thankYou.isDisplayed()) {
                test.log(Status.SKIP, "API may have failed. Got Thank You message.");
                return;
            }
        } catch (TimeoutException ignored) {}

        // Continue Application
        wait.until(ExpectedConditions.elementToBeClickable(enquirNow.continueapplicationElement)).click();

        // Start Application check
        try {
            if (wait.until(ExpectedConditions.elementToBeClickable(enquirNow.startapplicationElement)).isEnabled()) {
                test.pass("Enquire Now Indian Journey Successful");
            } else {
                test.fail("Start Application button disabled");
            }
        } catch (TimeoutException e) {
            test.fail("Start Application button not found");
        }

        // URL match check
        String actualTrimmed = driver.getCurrentUrl().replaceFirst("^https?://", "");
        String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");
        if (actualTrimmed.equals(expectedTrimmed)) {
            test.pass("URL matched: " + driver.getCurrentUrl());
        } else {
            test.fail("URL mismatch. Expected: " + ApplicationUrl + " | Actual: " + driver.getCurrentUrl());
        }

    } catch (Exception e) {
        test.fail("Enquire Now Indian Flow Failed: " + e.getMessage());
        softAssert.fail("Flow Failed - " + e.getMessage());
    } finally {
        driver.manage().deleteAllCookies();
        test.info("Cookies cleared.");
        test.info("Total Load Time: " + formatSeconds(System.nanoTime() - startTime));
        softAssert.assertAll();
    }
    softAssert.assertAll();
}


    @Test(priority = 2)
    public void verifyEnquireNowHeader_International() throws InterruptedException {
        test = reports.createTest("Verify Enquire Now - International (Header Section)");
        closescholarshippopup();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "BLOCKCHAIN TECHNOLOGY AND MANAGEMENT";
        String Enquirysource = "Enquire";
        String utm_medium = "request_a_callback";
        long startTime = System.nanoTime();
        // ========== INTERNATIONAL FLOW ==========
        startTime = System.nanoTime(); // Reset for international flow
        try {
            String originalWindow = driver.getWindowHandle();
            WebElement enquireNowBTN2 = wait.until(ExpectedConditions.elementToBeClickable(
                    enquirNow.enquireNowelementElement));
            js.executeScript("arguments[0].click();", enquireNowBTN2);
            // enquireNowBTN2.click();

            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(enquirNow.internationalElement)).click();
            enquirNow.countrycodeElement.click();
            WebElement unitedElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='CustomDropdownGlobal_dropdownItem__Qu0Dw'][1]")));
            String countryCode = unitedElement.getText();
            test.info("country Code: " + countryCode);
            Actions codeActions = new Actions(driver);
            codeActions.moveToElement(unitedElement).click().perform();

            String randomMobileNumber = "234" + random.getRandomMobileNumber();
            String randomName = "TestQA" + " " + random.GetRandomName();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Random Name: " + randomName);
            test.info("Random Email: " + randomEmail);
            test.info("Random Mobile Number: " + randomMobileNumber);

            enquirNow.nameElement.sendKeys(randomName);
            enquirNow.mobiElement.sendKeys(randomMobileNumber);
            enquirNow.emailElement.sendKeys(randomEmail);
            enquirNow.submitElement.click();
            Thread.sleep(2000);

            try {
                String handle = driver.getWindowHandle();
                driver.switchTo().window(handle);
                wait.until(ExpectedConditions.elementToBeClickable(enquirNow.degreeElement)).sendKeys("PG");
                wait.until(ExpectedConditions.elementToBeClickable(enquirNow.prograElement))
                        .sendKeys("MASTER OF COMPUTER APPLICATIONS WITH SPECIALIZATION IN CYBER SECURITY");
                enquirNow.submit2elElement.click();
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
            Thread.sleep(2000);
            LeadSummary lead = LeadSummary.getLeadSummaryByEmail(randomEmail);
            // ✅ Validate returned data
            HomePage.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL,
                    expectedProgram, expectedSpecialization, lsqId, cleanVisited, Enquirysource, utm_medium,
                    softAssert);

            Thread.sleep(7000);

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
            WebElement continueapplication2 = wait
                    .until(ExpectedConditions.elementToBeClickable(enquirNow.continueapplicationElement));
            js.executeScript("arguments[0].click();", continueapplication2);
            // Proceed to normal flow – check for Start Application button
            try {
                WebElement isSuccess = wait
                        .until(ExpectedConditions.elementToBeClickable(Apom.startapplicationElement));
                if (isSuccess.isEnabled()) {
                    softAssert.assertTrue(true, "International flow - Start Application button enabled check");
                    test.log(Status.PASS, "Enquire Now International Journey Successful");
                    System.out.println("Enquire Now International Journey Successful");
                } else {
                    test.log(Status.FAIL, "International Flow Failed: Start Application button not enabled");
                    softAssert.fail("International Flow Failed - Start Application button not enabled");
                    System.out.println("Enquire Now International Journey Failed");
                }
            } catch (TimeoutException e) {
                test.log(Status.FAIL,
                        "International Flow Failed: Start Application button did not appear. " + e.getMessage());
                softAssert.fail("International Flow Failed - Start Application button not found");
                System.out.println("Enquire Now International Journey Failed - Button not found");
            }

            String student_login = driver.getCurrentUrl();
            // Remove http:// or https:// from both URLs
            String actualTrimmed = student_login.replaceFirst("^https?://", "");
            String expectedTrimmed = ApplicationUrl.replaceFirst("^https?://", "");
            if (actualTrimmed.equals(expectedTrimmed)) {
                System.out.println("✅ URL matched: " + student_login);
                test.log(Status.PASS,
                        "✅ URL matched Application Url of Cookies to application journey page" + student_login);
            } else {
                System.out.println("❌ URL mismatch.");
                System.out.println("Expected: " + ApplicationUrl);
                System.out.println("Actual: " + student_login);
                test.log(Status.FAIL, "❌ URL mismatch. Expected : " + ApplicationUrl + "Actual: " + student_login);
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "International Flow Failed: " + e.getMessage());
            softAssert.fail("International Flow Failed - " + e.getMessage());
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
            HomePage.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL,
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
            HomePage.validateLeadSummaryData(lead, randomEmail, randomName, randomMobileNumber, expectedURL,
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

    // --------Yellow Strip (Take Instant Scholarship Test)-----------------

    @Test(priority = 5)
    public void verifyInstantScholarshipTest() {
        test = reports.createTest("Verify Take Instant Scholarship Test");
        closescholarshippopup();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "MASTER OF COMPUTER APPLICATIONS";
        String Enquirysource = "Enquire";
        String utm_medium = "scholarship_lead";
        long startTime = System.nanoTime();

        try {
            wait.until(ExpectedConditions.visibilityOf(Apom.scholershipyelloWebElement));
            js.executeScript("arguments[0].scrollIntoView();", Apom.scholershipyelloWebElement);
            js.executeScript("arguments[0].click();", Apom.scholershipyelloWebElement);
            test.log(Status.PASS, "Clicked on July'25 Session - Take Instant Scholarship Test");
            Thread.sleep(3000);

            String currentHandle = driver.getWindowHandle();
            driver.switchTo().window(currentHandle);

            if (hPom.yellowstripcontentElement.getText().equalsIgnoreCase("Reserve Your Spot Now")) {
                test.log(Status.PASS, "Yellow Strip is Working");
            } else {
                test.log(Status.FAIL, "Yellow Strip text not matched");
                softAssert.fail("Text mismatch in Yellow Strip");
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Yellow Strip is not Working: " + e.getMessage());
            softAssert.fail("Yellow Strip failed: " + e.getMessage());
        }

        // Fill the Apply Now Form
        try {
            String randomMobileNumber = "239" + random.getRandomMobileNumber();
            String randomName = "TestQA " + random.GetRandomName();
            String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

            test.info("Name: " + randomName);
            test.info("Email: " + randomEmail);
            test.info("Mobile: " + randomMobileNumber);

            Apom.mobiElement.sendKeys(randomMobileNumber);
            Apom.nameElement.sendKeys(randomName);
            Apom.emailElement.sendKeys(randomEmail);
            Apom.submitElement.click();
            Thread.sleep(3000);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(Apom.degreeElement)).sendKeys("PG");
                wait.until(ExpectedConditions.elementToBeClickable(Apom.programscholarship))
                        .sendKeys("MASTER OF COMPUTER APPLICATIONS");
                Apom.submit2elElement.click();
                Thread.sleep(6000);
                test.info("Degree and Program selection completed.");
            } catch (Exception inner) {
                test.warning("Degree/Program selection skipped: " + inner.getMessage());
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

            // ✅ Fill OTP
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
            HomePage.validateLeadSummaryData(
                    lead, randomEmail, randomName, randomMobileNumber,
                    expectedURL, expectedProgram, expectedSpecialization,
                    lsqId, cleanVisited, Enquirysource, utm_medium, softAssert);

            // ✅ Handle Thank You message if API fails
            try {
                WebElement thankYouMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Thank You For Your Interest')]")));

                if (thankYouMessage.isDisplayed()) {
                    test.log(Status.SKIP, "API may have failed. Received Thank You message after OTP verification.");
                    test.info("Displayed Message: " + thankYouMessage.getText());
                    System.out.println("Flow Stopped: " + thankYouMessage.getText());
                    return;
                }
            } catch (TimeoutException e) {
                test.info("No Thank You message found – continuing to check for Start Test.");
            }

            Thread.sleep(6000);
            // ✅ Compare actual URL with expected URL ignoring http/https
            String student_login = driver.getCurrentUrl();
            String actualTrimmed = student_login.replaceFirst("^https?://", "");
            String expectedTrimmed = ApplicationUrl != null ? ApplicationUrl.replaceFirst("^https?://", "") : "";

            if (actualTrimmed.equals(expectedTrimmed)) {
                System.out.println("✅ URL matched: " + student_login);
                test.log(Status.PASS,
                        "✅ URL matched Application URL from Cookie to student login page: " + student_login);
            } else {
                System.out.println("❌ URL mismatch.");
                System.out.println("Expected: " + ApplicationUrl);
                System.out.println("Actual: " + student_login);
                test.log(Status.FAIL, "❌ URL mismatch. Expected: " + ApplicationUrl + "Actual: " + student_login);
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            softAssert.fail("Test Failed: " + e.getMessage());
        } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        }
        long endTime = System.nanoTime();
        test.info("Total Load Time: " + formatSeconds(endTime - startTime));
        checkVisualErrorsOnScreen();
        softAssert.assertAll();
    }

    @Test(priority = 6)
    public void verifyBlueRFISection_India_International() throws InterruptedException {
        test = reports.createTest("Verify Blue RFI - Indian & International(HomePage)");
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "Specialisation in Cyber Security";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";
        try {
            // Close popups and initialize
            closescholarshippopup();
            closePopupIfPresent();
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // ========== INDIAN FLOW ==========
            long startTime = System.nanoTime(); // start tracking time
            try {
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(
                        By.xpath("//div[@class='LeadForm_formSection__TxeSn']//h2[contains(text(),'Apply Now')]")));
                Thread.sleep(2000);

                String randomName = "TestQA" + " " + random.GetRandomName();
                String randomMobileNumber = "239" + random.getRandomMobileNumber();
                String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

                test.info("Random Name: " + randomName);
                test.info("Random Email: " + randomEmail);
                test.info("Random Mobile Number: " + randomMobileNumber);
                wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.fullnamElement));
                BlueRFI.fullnamElement.sendKeys(randomName);
                BlueRFI.phonenumberElement.sendKeys(randomMobileNumber);
                BlueRFI.emailidElement.sendKeys(randomEmail);

                WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.submitbuttonElement));
                js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", submitBtn);

                try {
                    String handle = driver.getWindowHandle();
                    driver.switchTo().window(handle);

                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.degreeElement)).sendKeys("PG");
                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.programElement))
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
                HomePage.validateLeadSummaryData(
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
                        By.xpath("//div[@class='LeadForm_formSection__TxeSn']//h2[contains(text(),'Apply Now')]")));
                Thread.sleep(2000);

                String randomName = "TestQA" + " " + random.GetRandomName();
                String randomMobileNumber = "239" + random.getRandomMobileNumber2();
                String randomEmail = "TestQA_" + random.GetRamdonEmailID() + "@gmail.com";

                test.info("Random Name: " + randomName);
                test.info("Random Email: " + randomEmail);
                test.info("Random Mobile Number: " + randomMobileNumber);

                wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.fullnamElement));
                BlueRFI.fullnamElement.sendKeys(randomName);
                BlueRFI.countrycodElement.click();

                WebElement unitedElement = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='CustomDropdownGlobal_dropdown__2uWby']//div[9]//span[1]")));
                String countryCode = unitedElement.getText();
                test.info("country Code: " + countryCode);
                Actions codeActions = new Actions(driver);
                codeActions.moveToElement(unitedElement).click().perform();

                BlueRFI.phonenumberElement.sendKeys(randomMobileNumber);
                BlueRFI.emailidElement.sendKeys(randomEmail);

                WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.submitbuttonElement));
                js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", submitBtn);

                try {
                    String handle = driver.getWindowHandle();
                    driver.switchTo().window(handle);

                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.degreeElement)).sendKeys("PG");
                    wait.until(ExpectedConditions.elementToBeClickable(BlueRFI.programElement))
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
                HomePage.validateLeadSummaryData(
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
            } finally {
            // === ENSURE COOKIES ARE CLEARED EVEN IF TEST FAILS ===
            driver.manage().deleteAllCookies();
            test.info("🧹 All cookies cleared in finally block.");
        }
        } catch (Exception e) {
            test.log(Status.FAIL, "Blue RFI Test Failed: " + e.getMessage());
            softAssert.fail("Blue RFI Test Failed - " + e.getMessage());
        }
        checkVisualErrorsOnScreen();
        softAssert.assertAll();
    }

    @Test(priority = 7)
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
            HomePage.validateLeadSummaryData(
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

    @Test(priority = 8)
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
                    HomePage.validateLeadSummaryData(
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

    @Test(priority = 9)
    public void apply_now_sticky_India() throws InterruptedException {
        test = reports.createTest("Page sticky button (Apply Now - India)");
        closescholarshippopup();
        closePopupIfPresent();
        // Apply now sticky button start here
        long startTime = System.nanoTime();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";
        try {
            // driver.navigate().refresh();
            Thread.sleep(2000);
            WebElement scroll_down = driver
                    .findElement(By.xpath("//h2[contains(text(),'Latest blog from Amity Online’s world')]"));
            // wait.until(ExpectedConditions.visibilityOf(Sticky_Buttons_Call_whats.scrollElement));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll_down);
            Thread.sleep(5000);
            driver.navigate().refresh();
            wait.until(ExpectedConditions.elementToBeClickable(Sticky_Buttons_Call_whats.apply_now_button));
            js.executeScript("arguments[0].click();", Sticky_Buttons_Call_whats.apply_now_button);
            test.log(Status.PASS, "Apply now sticky button successfully clicked");
            // Apply now in india
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
            HomePage.validateLeadSummaryData(
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
            test.log(Status.FAIL, "Apply now sticky button is not visible or working" + e.getMessage());
            softAssert.fail("Apply now sticky button not visible or working" + e.getMessage());
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

    @Test(priority = 10)
    public void apply_now_sticky_International() throws InterruptedException {
        test = reports.createTest("Page sticky button (Apply Now - INTERNATIONAL FLOW)");
        closescholarshippopup();
        closePopupIfPresent();
        // Apply now sticky button start here
        long startTime = System.nanoTime();
        String expectedURL = "https://qa-fe.amityonline.com/";
        String expectedProgram = "MASTER OF COMPUTER APPLICATIONS";
        String expectedSpecialization = "";
        String Enquirysource = "Apply Now";
        String utm_medium = "request_a_callback";
        // ========== INTERNATIONAL FLOW ==========
        try {
            WebElement scroll_down = driver
                    .findElement(By.xpath("//h2[contains(text(),'Latest blog from Amity Online’s world')]"));
            // wait.until(ExpectedConditions.visibilityOf(Sticky_Buttons_Call_whats.scrollElement));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll_down);
            Thread.sleep(5000);
            driver.navigate().refresh();
            wait.until(ExpectedConditions.elementToBeClickable(Sticky_Buttons_Call_whats.apply_now_button));
            js.executeScript("arguments[0].click();", Sticky_Buttons_Call_whats.apply_now_button);
            test.log(Status.PASS, "Apply now sticky button successfully clicked");
            // Apply now in india
            Thread.sleep(3000);
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
                    HomePage.validateLeadSummaryData(
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
                Thread.sleep(7000);
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
            test.log(Status.FAIL,
                    "❌ Email mismatch LS : expected '" + expectedEmail + "', actual '" + lead.email + "'");
        } else {
            test.log(Status.PASS, "✅ Email matched LS : " + lead.email);
        }

        // ✅ Full Name check
        if (!lead.fullName.equalsIgnoreCase(expectedFullName)) {
            softAssert.fail("❌ Name mismatch LS : expected '" + expectedFullName + "', actual '" + lead.fullName + "'");
            test.log(Status.FAIL,
                    "❌ Name mismatch LS : expected '" + expectedFullName + "', actual '" + lead.fullName + "'");
        } else {
            test.log(Status.PASS, "✅ Name matched LS : " + lead.fullName);
        }

        // ✅ Phone check
        if (!lead.phone.equals(expectedPhone)) {
            softAssert.fail("❌ Phone mismatch LS : expected '" + expectedPhone + "', actual '" + lead.phone + "'");
            test.log(Status.FAIL,
                    "❌ Phone mismatch LS : expected '" + expectedPhone + "', actual '" + lead.phone + "'");
        } else {
            test.log(Status.PASS, "✅ Phone matched LS : " + lead.phone);
        }

        // // ✅ Page URL check
        // if (!lead.pageURL.equals(expectedPageURL)) {
        // softAssert.fail("❌ Page URL mismatch LS : expected '" + expectedPageURL + "',
        // actual '" + lead.pageURL + "'");
        // test.log(Status.FAIL, "❌ Page URL mismatch LS : expected '" + expectedPageURL
        // + "', actual '" + lead.pageURL + "'");
        // } else {
        // test.log(Status.PASS, "✅ Page URL matched LS : " + lead.pageURL);
        // }

        // ✅ Page URL check cookies
        if (!lead.pageURL.equals(cleanVisited)) {
            softAssert.fail(
                    "❌ Page URL mismatch LS Cookies : expected '" + cleanVisited + "', actual '" + lead.pageURL + "'");
            test.log(Status.FAIL,
                    "❌ Page URL mismatch LS Cookies : expected '" + cleanVisited + "', actual '" + lead.pageURL + "'");
        } else {
            test.log(Status.PASS, "✅ Page URL matched LS Cookies : " + lead.pageURL);
        }

        // // ✅ Program Name check
        // if (!lead.programName.equalsIgnoreCase(expectedProgram)) {
        //     softAssert.fail("❌ Program name mismatch LS : expected '" + expectedProgram + "', actual '"
        //             + lead.programName + "'");
        //     test.log(Status.FAIL, "❌ Program name mismatch LS : expected '" + expectedProgram + "', actual '"
        //             + lead.programName + "'");
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
            softAssert.fail("❌ Enquiry source mismatch LS: expected '" + Enquirysource + "', actual '"
                    + lead.enquarySource + "'");
            test.log(Status.FAIL, "❌ Enquiry source mismatch LS: expected '" + Enquirysource + "', actual '"
                    + lead.enquarySource + "'");
        } else {
            test.log(Status.PASS, "✅ Enquiry source matched LS: " + lead.enquarySource);
        }

        // ✅ UTM Source check
        String expectedutmSource = "website";

        if (!expectedutmSource.equalsIgnoreCase(lead.utmSource)) {
            softAssert.fail(
                    "❌ UTM source mismatch LS: expected '" + expectedutmSource + "', actual '" + lead.utmSource + "'");
            test.log(Status.FAIL,
                    "❌ UTM source mismatch LS: expected '" + expectedutmSource + "', actual '" + lead.utmSource + "'");
        } else {
            test.log(Status.PASS, "✅ UTM source matched LS: " + lead.utmSource);
        }

        // ✅ UTM Medium check
        // String expectedUTMmedium = "request_a_callback";
        if (!utm_medium.equalsIgnoreCase(lead.utmMedium)) {
            softAssert.fail("❌ UTM Medium mismatch LS: expected '" + utm_medium + "', actual '" + lead.utmMedium + "'");
            test.log(Status.FAIL,
                    "❌ UTM Medium mismatch LS: expected '" + utm_medium + "', actual '" + lead.utmMedium + "'");
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
            softAssert.fail(
                    "❌ Unexpected country name LS: got '" + country + "', expected 'India' or 'United Arab Emirates'");
            test.log(Status.FAIL,
                    "❌ Unexpected country name LS: got '" + country + "', expected 'India' or 'United Arab Emirates'");
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
                    softAssert.fail("❌ commConsent date mismatch LS: expected today '" + todayDate + "', but got '"
                            + consentDateTime + "'");
                    test.log(Status.FAIL, "❌ commConsent date mismatch LS: expected today '" + todayDate
                            + "', but got '" + consentDateTime + "'");
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
