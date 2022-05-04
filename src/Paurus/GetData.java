package Paurus;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class GetData {
    public static JSONArray getObjectData() {
       JSONArray jArray = null;
        try {
            //GET SERVICE
            URL url = new URL("https://reqres.in/api/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            //WE CHECK IF WE GOT CORRECT RESPONSE CODE
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

            //READ DATA
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                  inline += scanner.nextLine();
            }
            scanner.close();

            //PARSE inline TO JSONARRAY
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(inline);
            jArray = (JSONArray) json.get("data");

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return jArray;
    }
}
