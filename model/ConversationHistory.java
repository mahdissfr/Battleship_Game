package model;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class ConversationHistory{
    private JSONObject jsonObject;
    private JSONObject[] jsonArray;

    @SuppressWarnings("unchecked")
    public void saveConversation() throws IOException {
        jsonObject = new JSONObject();
        jsonObject.put("Name", "");
        jsonObject.put("Ip", "");
        jsonObject.put("Id", "");
        jsonArray = new JSONObject[1000];
        for (int i = 0; i < 1; i++) {
            jsonArray[i].put("", "");
        }
        jsonObject.put("Conversations:", jsonArray);

        try (FileWriter file = new FileWriter("file1.txt")) {
            file.write(jsonObject.toString());
        }
    }
}
