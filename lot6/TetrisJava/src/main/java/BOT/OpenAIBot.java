package BOT;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject1.DebugFunction;
import java.io.IOException;
import java.util.ArrayList;

public class OpenAIBot {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private ArrayList<String> instructions;

    // Constructor
    public OpenAIBot(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        this.instructions = new ArrayList<>(); // Initialize the ArrayList
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

    // Changed return type to ArrayList<String> instead of String[]
    public String getInstruction() {
        if (instructions.isEmpty()) {
            return null; // ou une valeur par défaut appropriée
        }
        return instructions.get(0);
    }

    public String convertGrilleToString(int[][] grille) {
        DebugFunction.printArray2D(grille);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                result.append(grille[i][j]);
            }
            result.append("a la ligne.");  // Ajoute un saut de ligne à la fin de chaque ligne de la grille
        }
        return result.toString();  // Convertit StringBuilder en String avant de le retourner
    }

    public void createNewInstructions(int[][] grille) throws IOException {

        String strGrille = convertGrilleToString(grille);
        String megaPrompt = "Voici la grille :" + strGrille + "Voici les règles : " + Prompt.getPromptGame()
                + Prompt.getPromptGrille();

        String testPrompt = "comment tu t'appelle ?";

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
                System.err.println("content : " + content + "fin");

                // Parse the content string into separate instruction items and add them to the ArrayList
                String[] instructionItems = convertStringToArray(content);
                for (String item : instructionItems) {
                         instructions.add(item);
                    }
                
                System.err.print("First instruction: " + (!instructions.isEmpty() ? instructions.get(0) : "No instructions"));
            } else {
                System.err.print("Invalid response format");
            }
        }
    }

    private String[] convertStringToArray(String message) {
        // Utiliser une expression régulière pour trouver les mots entre virgules
        String[] words = message.split(" ");  // Séparer la chaîne par les virgules
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

    

//    // Method to remove the first instruction
//    public void removeInstruction() {
//        if (!instructions.isEmpty()) {
//            instructions.remove(0);
//        }
//    }

    // Method to remove all instructions
    public void clearInstructions() {
        instructions.clear();
    }
}
