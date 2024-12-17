package org.example;

import java.util.List;

public class BalanceResponse {
    private boolean success;
    private Payload payload;

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    // Inner class for Payload
    public static class Payload {
        private List<Balance> balances;

        // Getters and Setters
        public List<Balance> getBalances() {
            return balances;
        }

        public void setBalances(List<Balance> balances) {
            this.balances = balances;
        }
    }

    // Inner class for individual Balance entries
    public static class Balance {
        private String currency;
        private String available;
        private String locked;
        private String total;
        private String pending_deposit;
        private String pending_withdrawal;

        // Getters and Setters
        public String getCurrency() {
            return currency;
        }

        public String getAvailable() {
            return available;
        }

        public String getLocked() {
            return locked;
        }

        public String getTotal() {
            return total;
        }

        public String getPending_deposit() {
            return pending_deposit;
        }

        public String getPending_withdrawal() {
            return pending_withdrawal;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public void setLocked(String locked) {
            this.locked = locked;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public void setPending_deposit(String pending_deposit) {
            this.pending_deposit = pending_deposit;
        }

        public void setPending_withdrawal(String pending_withdrawal) {
            this.pending_withdrawal = pending_withdrawal;
        }
    }
}
