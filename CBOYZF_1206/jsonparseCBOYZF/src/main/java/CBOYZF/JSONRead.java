package CBOYZF;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRead {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("CBOYZF_1206\\kurzusfelvetelCBOYZF.json")) {
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            OutputStreamWriter wr = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
            wr.write(jsonObject.toString());
            wr.flush();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}