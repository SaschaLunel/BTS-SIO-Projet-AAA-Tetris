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
    private String[] instructions;

    // Constructor
    public OpenAIBot(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends a message to OpenAI and returns the response.
     *
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
        return instructions;
    }

    public String convertGrilleToString(int[][] grille) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                result.append(grille[i][j]);
            }
            result.append("\n");  // Ajoute un saut de ligne à la fin de chaque ligne de la grille
        }
        return result.toString();  // Convertit StringBuilder en String avant de le retourner
    }

    public void createNewInstructions(int[][] grille) throws IOException {

        String strGrille = convertGrilleToString(grille);
        String megaPrompt = "Voici la grille :" + strGrille + "\n"
                + "Voici les règles : " + Prompt.getPromptGame() + "\n"
                + Prompt.getPromptGrille();

        String testPrompt = "renvoie juste 'coucou, coucou'";

        String jsonRequest = createJsonRequest(megaPrompt);

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(jsonRequest, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("HTTP Error: " + response);
                throw new IOException("Unexpected code " + response);
            }

            JsonNode jsonResponse = objectMapper.readTree(response.body().string());

            JsonNode choicesNode = jsonResponse.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode choice = choicesNode.get(0);
                JsonNode messageNode = choice.path("message");
                String content = messageNode.path("content").asText("");
                instructions = convertStringToArray(content);
                System.err.println(instructions[0]);
            } else {
                System.err.println("Invalid response format");
            }
        }
    }

    private String[] convertStringToArray(String message) {
        // Utiliser une expression régulière pour trouver les mots entre virgules
        String[] words = message.split(",");  // Séparer la chaîne par les virgules
        // Retirer les espaces inutiles avant et après chaque mot
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();  // Supprimer les espaces avant et après
        }
        return words;
    }

    private String createJsonRequest(String prompt) {
        return String.format("{"
                + "\"model\": \"gpt-4\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"%s\"}],"
                + "\"max_tokens\": 150"
                + "}", prompt.replace("\"", "\\\""));
    }
}
