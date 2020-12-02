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
                "{\n" +
                        "    \"Ingresos\":[\n" +
                        "        {\n" +
                        "            \"id\":\"69\",\n" +
                        "             \"monto\":\"52\",\n" +
                        "            \"fecha\":\"2020-08-23\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\":\"16\",\n" +
                        "             \"monto\":\"345\",\n" +
                        "            \"fecha\":\"2020-08-26\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"Egresos\":[\n" +
                        "        {\n" +
                        "            \"id\":\"15\",\n" +
                        "             \"monto\":\"1\",\n" +
                        "            \"fecha\":\"2020-08-05\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\":\"13\",\n" +
                        "             \"monto\":\"2\",\n" +
                        "            \"fecha\":\"2020-09-02\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\":\"7\",\n" +
                        "             \"monto\":\"50\",\n" +
                        "            \"fecha\":\"2020-08-02\"\n" +
                        "        }\n" +
                        "    ], \"Configuracion\":\n" +
                        "    \t{\n" +
                        "    \t\t\"fechaDesde\":\"2020-08-01\",\n" +
                        "    \t\t\"fechaHasta\":\"2020-09-10\",\n" +
                        "    \t\t\"condicionVinculador\": \"PeriodoAceptabilidad\",\n" +
                        "    \t\t\"criterioVinculacion\": \"Mix\",\n" +
                        "    \t\t\"criteriosDeMix\": [\n" +
                        "    \t\t\t\"OrdenValorPrimerIngreso\",\n" +
                        "    \t\t\t\"Fecha\"\n" +
                        "    \t\t\t]\n" +
                        "    \t}\n" +
                        "} ",
                "http://localhost:9000/Prueba"
        );
        System.out.println(asd);
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

            System.out.println(myResponse.toString());
            return myResponse;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}