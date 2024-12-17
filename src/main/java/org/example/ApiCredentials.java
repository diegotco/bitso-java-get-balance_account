package org.example;

public class ApiCredentials {

    private static final String API_KEY = System.getenv("BITSO_API_KEY");
    private static final String API_SECRET = System.getenv("BITSO_API_SECRET");

    // Getter for API Key
    public static String getApiKey() {
        return API_KEY;
    }

    // Getter for API Secret
    public static String getApiSecret() {
        return API_SECRET;
    }
}
