package utils.Vinculador;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VinculadorApi {
    public static void main(String[] args) {
        VinculadorApi vincu = new VinculadorApi();
        JSONObject asd = vincu.Post_JSON(
                "{\"Ingresos\":[{\"id\":13,\"monto\":420,\"fecha\":\"Thu Sep 17 08:21:00 UTC 2020\"}],\"Egresos\":[{\"id\":12,\"monto\":69,\"fecha\":\"Thu Sep 3 08:21:00 UTC 2020\"},{\"id\":13,\"monto\":79,\"fecha\":\"Mon Sep 21 08:21:00 UTC 2020\"}]}",
                "http://localhost:9000/Prueba"
        );
        System.out.println(asd.toString());
    }
    public JSONObject Post_JSON(String json,String query_url) {

        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            JSONObject myResponse = new JSONObject(result);
            in.close();
            conn.disconnect();
            return myResponse;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}