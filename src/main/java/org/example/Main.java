package org.example;

import com.google.gson.Gson;
import org.example.execution.BitsoAuthentication;

public class Main {
    public static void main(String[] args) {
        try {
            BitsoAuthentication bitsoAuth = new BitsoAuthentication();
            Gson gson = new Gson();

            // Fetch complete balance (all currencies)
            String completeBalanceJson = bitsoAuth.fetchBalance(null);
            System.out.println("TOTAL Balance: " + completeBalanceJson);

            // Parse complete balance
            BalanceResponse completeBalance = gson.fromJson(completeBalanceJson, BalanceResponse.class);

            if (completeBalance.isSuccess()) {
                // Extract and print BTC balance
                BalanceResponse.Balance btcBalance = getBalanceForCurrency(completeBalance, "btc");
                if (btcBalance == null) {
                    System.out.println("BTC balance not found.");
                }
            } else {
                System.out.println("Failed to fetch complete balance.");
            }

            // Alternatively, fetch BTC balance directly
            String btcBalanceJson = bitsoAuth.fetchBalance("btc");

            // Parse BTC balance
            BalanceResponse btcBalanceResponse = gson.fromJson(btcBalanceJson, BalanceResponse.class);
            if (btcBalanceResponse.isSuccess()) {
                BalanceResponse.Balance btcBalanceDirect = getBalanceForCurrency(btcBalanceResponse, "btc");
                if (btcBalanceDirect != null) {
                    System.out.println("BTC Available Balance: " + btcBalanceDirect.getAvailable());
                } else {
                    System.out.println("BTC balance not found in direct fetch.");
                }
            } else {
                System.out.println("Failed to fetch BTC balance directly.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to extract balance for a specific currency.
     *
     * @param balanceResponse The BalanceResponse object containing balances.
     * @param currency        The currency code to filter by (e.g., "btc").
     * @return The Balance object if found, else null.
     */
    private static BalanceResponse.Balance getBalanceForCurrency(BalanceResponse balanceResponse, String currency) {
        for (BalanceResponse.Balance balance : balanceResponse.getPayload().getBalances()) {
            if (balance.getCurrency().equalsIgnoreCase(currency)) {
                return balance;
            }
        }
        return null;
    }
}
