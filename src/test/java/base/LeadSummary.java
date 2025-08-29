package base;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

public class LeadSummary {
    public String email;
    public String fullName;
    public String phone;
    public String pageURL;
    public String programName;
    public String specialization;
    public String enquarySource;
    public String utmSource;
    public String utmMedium;
    public String countryName;
    public String commConsent;
    public String prospectID;

    private static final String ACCESS_KEY = "uDeoe67771a528bf0492603c";
    private static final String SECRET_KEY = "190edcdb360bd74Demo4e205bb1138";
    private static boolean isUnirestInitialized = false;

    public static void initializeUnirest() {
        if (!isUnirestInitialized) {
            Unirest.config()
                   .connectTimeout(10000)
                   .socketTimeout(30000);
            isUnirestInitialized = true;
        }
    }

    public LeadSummary(String email, String fullName, String phone, String pageURL, String programName, String specialization, String enquarySource, String utmSource, String utmMedium, String countryName, String commConsent, String prospectID) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.pageURL = pageURL;
        this.programName = programName;
        this.specialization = specialization;
        this.enquarySource = enquarySource;
        this.utmSource = utmSource;
        this.utmMedium = utmMedium;
        this.countryName = countryName;
        this.commConsent = commConsent;
        this.prospectID = prospectID;
    }

    public static LeadSummary getLeadSummaryByEmail(String email) {
    int maxRetries = 5; // 1 initial attempt + 1 retry
    int delaySeconds = 5;

    for (int attempt = 1; attempt <= maxRetries; attempt++) {
        try {
            initializeUnirest();
            HttpResponse<String> response = Unirest.get("https://api-in21.leadsquared.com/v2/LeadManagement.svc/Leads.GetByEmailaddress")
                .queryString("accessKey", ACCESS_KEY)
                .queryString("secretKey", SECRET_KEY)
                .queryString("EmailAddress", email)
                .asString();

            JSONArray jsonArray = new JSONArray(response.getBody());

            // 🔁 Retry if no lead data found
            if (jsonArray.length() == 0) {
                if (attempt < maxRetries) {
                    System.out.println("Attempt " + attempt + ": No lead data found. Retrying after " + delaySeconds + " seconds...");
                    Thread.sleep(delaySeconds * 3000L);
                    continue;
                } else {
                    System.out.println("❌ No lead data found after retry.");
                    return null;
                }
            }

            // ✅ Parse lead data
            JSONObject lead = jsonArray.getJSONObject(0);

            String email_ls = lead.optString("EmailAddress");
            String firstName = lead.optString("FirstName");
            String phone = lead.optString("Phone");
            String page_url = lead.optString("mx_PageURL");
            String programName = lead.optString("mx_Program");
            String specialization = lead.optString("mx_Specialization");
            String enquirySource = lead.optString("mx_Enquery_Source");
            String utmSource = lead.optString("mx_utm_source");
            String utmMedium = lead.optString("mx_mx_utm_medium");
            String countryName = lead.optString("mx_Country");
            String commConsent = lead.optString("mx_Comm_Consent");
            String prospectID = lead.optString("ProspectID");

            // 🔁 Retry if countryName is missing
            if ((countryName == null || countryName.trim().isEmpty()) && attempt < maxRetries) {
                System.out.println("Attempt " + attempt + ": Country name is missing, retrying after " + delaySeconds + " seconds...");
                Thread.sleep(delaySeconds * 3000L);
                continue;
            }

            // 📞 Normalize phone number
            String phoneNormalized = phone.replaceAll("\\+\\d{1,3}-", "").trim();

            return new LeadSummary(email_ls, firstName, phoneNormalized, page_url, programName,
                    specialization, enquirySource, utmSource, utmMedium, countryName, commConsent, prospectID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    System.out.println("❌ Failed to retrieve complete lead data after retry.");
    return null;
}





}

