import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NameDetailsFetcher {
    public static void fetchDetails(String name) {
        fetchAgifyDetails(name);
        fetchGenderizeDetails(name);
        fetchNationalizeDetails(name);
    }

    private static void fetchAgifyDetails(String name) {
        try {
            String apiUrl = "https://api.agify.io/?name=" + name;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                String json = content.toString();
                String age = extractValue(json, "age");
                String count = extractValue(json, "count");
                System.out.println("Predicted age for '" + name + "': " + age);
                System.out.println("Number of people with the name '" + name + "': " + count);
            } else {
                System.out.println("Failed to fetch age details. HTTP Status: " + status);
            }
            con.disconnect();
        } catch (Exception e) {
            System.out.println("Error fetching age details: " + e.getMessage());
        }
    }

    private static void fetchGenderizeDetails(String name) {
        try {
            String apiUrl = "https://api.genderize.io/?name=" + name;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                String json = content.toString();
                String gender = extractStringValue(json, "gender");
                String probability = extractValue(json, "probability");
                System.out.println("Predicted gender for '" + name + "': " + gender + " (probability: " + probability + ")");
            } else {
                System.out.println("Failed to fetch gender details. HTTP Status: " + status);
            }
            con.disconnect();
        } catch (Exception e) {
            System.out.println("Error fetching gender details: " + e.getMessage());
        }
    }

    private static void fetchNationalizeDetails(String name) {
        try {
            String apiUrl = "https://api.nationalize.io/?name=" + name;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                String json = content.toString();
                String countryId = extractCountryId(json);
                System.out.println("Most likely nationality for '" + name + "': " + countryId);
            } else {
                System.out.println("Failed to fetch nationality details. HTTP Status: " + status);
            }
            con.disconnect();
        } catch (Exception e) {
            System.out.println("Error fetching nationality details: " + e.getMessage());
        }
    }

    // Helper method to extract numeric value from JSON string
    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return "N/A";
        start += pattern.length();
        int end = json.indexOf(',', start);
        if (end == -1) end = json.indexOf('}', start);
        String value = json.substring(start, end).trim();
        if (value.equals("null")) return "N/A";
        return value;
    }

    // Helper method to extract string value from JSON string
    private static String extractStringValue(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return "N/A";
        start += pattern.length();
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '"')) start++;
        int end = json.indexOf('"', start);
        if (end == -1) return "N/A";
        return json.substring(start, end);
    }

    // Helper method to extract the first country_id from the array in nationalize.io response
    private static String extractCountryId(String json) {
        String pattern = "\"country_id\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) return "N/A";
        start += pattern.length();
        int end = json.indexOf('"', start);
        if (end == -1) return "N/A";
        return json.substring(start, end);
    }
}
