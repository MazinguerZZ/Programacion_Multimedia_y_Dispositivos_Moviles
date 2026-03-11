/**
 * HttpUtils
 *
 * Clase de utilidad para realizar peticiones HTTP.
 * En este caso se usa para hacer peticiones GET a una API
 * y obtener frases en formato JSON.
 *
 * Está separada del resto del código para mantener
 * la aplicación organizada y más fácil de entender.
 */
package com.pmm.a23.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    // Etiqueta para mostrar mensajes en el Logcat
    private static final String TAG = "HttpUtils";

    /**
     * Realiza una petición HTTP GET a la URL indicada.
     *
     * Este método:
     *  - Se conecta a la URL
     *  - Lee la respuesta del servidor
     *  - Devuelve el contenido como un String
     *
     * Si ocurre algún error, devuelve null.
     *
     * IMPORTANTE: este método debe ejecutarse fuera del hilo principal.
     *
     * @param urlString dirección URL a la que se hace la petición
     * @return respuesta del servidor en texto o null si hay error
     */
    public static String getRequest(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // Crear el objeto URL a partir del String recibido
            URL url = new URL(urlString);

            // Abrir la conexión HTTP
            connection = (HttpURLConnection) url.openConnection();

            // Configurar la petición
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Tiempo máximo para conectar (5 segundos)
            connection.setReadTimeout(5000);    // Tiempo máximo para leer datos (5 segundos)
            connection.setRequestProperty("Accept", "application/json");

            // Obtener el código de respuesta del servidor
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            // Si la respuesta es correcta (200 OK)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leemos la respuesta del servidor
                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                // Leer línea a línea la respuesta
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Mostramos la respuesta en el log (solo para depuración)
                Log.d(TAG, "Response: " + response.toString());

                // Devolvemos la respuesta completa
                return response.toString();
            } else {
                // Si el servidor responde con error
                Log.e(TAG, "HTTP Error: " + responseCode);
                return null;
            }

        } catch (Exception e) {
            // Cualquier error de red o conexión acaba aquí
            Log.e(TAG, "Error en la petición HTTP: " + e.getMessage(), e);
            return null;
        } finally {
            // Cerramos recursos para evitar fugas de memoria
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error cerrando recursos: " + e.getMessage());
            }
        }
    }

    /**
     * Método auxiliar para convertir una respuesta JSON en un objeto Frase.
     *
     * NOTA: este método es solo un ejemplo y no se usa actualmente.
     * El parseo real del JSON se hace directamente en la actividad.
     *
     * @param jsonResponse respuesta del servidor en formato JSON
     * @return objeto Frase o null si ocurre un error
     */
    public static com.pmm.a23.data.Frase parseFraseFromJson(String jsonResponse) {
        try {
            // Comprobamos que la respuesta no esté vacía
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                // Aquí iría el parseo real del JSON (JSONObject, Gson, etc.)
                // Por ahora devolvemos una frase de ejemplo
                return new com.pmm.a23.data.Frase(
                        jsonResponse.substring(0, Math.min(50, jsonResponse.length())),
                        "Autor desde JSON"
                );
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parseando JSON: " + e.getMessage());
        }

        // Si algo falla, devolvemos null
        return null;
    }
}
