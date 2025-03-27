/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOT;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class OpenAIBot {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private String[] instruction;
    


    // Constructor
    public OpenAIBot(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends a message to OpenAI and returns the response.
     * @param message The user's message.
     * @return The response from OpenAI.
     * @throws IOException if an error occurs during the request.
     */
    public String sendMessage(String message) throws IOException {
        String jsonRequest = "{"
                + "\"model\": \"gpt-4\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}],"
                + "\"max_tokens\": 100"
                + "}";

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(jsonRequest, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            JsonNode jsonResponse = objectMapper.readTree(response.body().string());
            return jsonResponse.get("choices").get(0).get("message").get("content").asText();
        }
    }

    public void sendMessageGame(String toString) {
    }

    public String[] getInstruction() {
        return instruction;
    }
    
    
}
