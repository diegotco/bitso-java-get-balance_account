## What does this code do?

This code connects to the Bitso cryptocurrency exchange to fetch account balances, focusing on retrieving the total balance and the Bitcoin (BTC) balance. The Main class handles the process: it first gets the full account balance, checks if the response is successful, and then looks for the BTC balance using a helper method. Additionally, it fetches the BTC balance directly from the API to ensure accuracy and prints the available balance. The program uses JSON parsing with Gson to read and extract the relevant balance data from the API responses.

The BitsoAuthentication class manages the secure connection to the API. It generates an HMAC-SHA256 signature to authenticate requests using an API key and secret. The fetchBalance() method sends HTTP GET requests to Bitso's balance endpoint, optionally filtering for a specific currency like "BTC". If the request is successful, it returns the balance details as a JSON string. Overall, the program securely fetches, processes, and displays cryptocurrency balances, ensuring accurate results through authentication and structured data handling.


## Considerations

-This code is compatible with Java 17 or the latest version.

-Bitso requires a signature using a hash-based message authentication code (HMAC) with the SHA-256 hash function. Do not use Base64 encoding. For an example, please refer to the documentation at https://docs.bitso.com/bitso-api/docs/create-signed-requests

## Errors

Generally, errors are present in how the Signature is formed. Please check the following items:

1. The nonce.
2. The payload
3. The endpoint
4. Others
