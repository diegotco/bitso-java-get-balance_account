package org.example.execution;

import org.example.ApiCredentials;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

public class BitsoAuthentication {

    private static final String HMAC_SHA256 = "HmacSHA256";
    private final String apiKey;
    private final String apiSecret;

    public BitsoAuthentication() {
        // Load API credentials
        this.apiKey = ApiCredentials.getApiKey();
        this.apiSecret = ApiCredentials.getApiSecret();

        if (this.apiKey == null || this.apiSecret == null) {
            throw new IllegalStateException("API Key or Secret is not set in environment variables.");
        }
    }

    /**
     * Generate headers including the Authorization header as per Bitso's requirements.
     */
    public Map<String, String> generateHeaders(String method, String endpoint, String payload) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Generate nonce in seconds since epoch
        String nonce = String.valueOf(System.currentTimeMillis());

        // Generate HMAC signature
        String signature = generateSignature(nonce, method, endpoint, payload);

        // Construct the Authorization header
        String authHeader = String.format("Bitso %s:%s:%s", apiKey, nonce, signature);

        // Add headers
        headers.put("Authorization", authHeader);

        return headers;
    }

    /**
     * Generate HMAC-SHA256 signature for Bitso API authentication.
     */
    private String generateSignature(String nonce, String method, String endpoint, String payload) {
        try {
            // Construct the message as per Bitso's API requirements
            String message = nonce + method.toUpperCase() + endpoint + payload;

            // Initialize the Mac instance with HMAC-SHA256
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKeySpec);

            // Compute the HMAC on the message
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            // Convert the HMAC byte array to a hexadecimal string
            String hexSignature = HexFormat.of().formatHex(rawHmac);

            return hexSignature;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    /**
     * Fetch balance from Bitso API.
     *
     * @param currency The currency to fetch the balance for. If null or empty, fetches all balances.
     * @return The JSON response as a String.
     * @throws Exception If the request fails.
     */
    public String fetchBalance(String currency) throws Exception {
        String method = "GET";
        String endpoint = "/v3/balance";
        String queryParams = (currency != null && !currency.isEmpty()) ? "?currency=" + currency : "";
        String fullEndpoint = endpoint + queryParams;
        String payload = ""; // No payload needed for GET request

        // Generate headers (only use the path without query parameters for signature)
        String signatureEndpoint = endpoint; // Signature should not include query params
        Map<String, String> headers = generateHeaders(method, signatureEndpoint, payload);

        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Build the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.bitso.com" + endpoint)) // For stage use https://api-stage.bitso.com
                .header("Content-Type", "application/json")
                .header("Authorization", headers.get("Authorization"))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response (the balance)
        if (response.statusCode() == 200) {
            return response.body(); // The response body contains the balance details
        } else {
            throw new RuntimeException("Failed to fetch balance: " + response.statusCode() + " - " + response.body());
        }
    }
}
