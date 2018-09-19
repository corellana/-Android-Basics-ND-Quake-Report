package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    /**
     * Then we have a private constructor, which means no one should create
     * an instance of this class. This is a utility class, so it'll contain static variables and
     * static methods, where you can call the method directly, without having to have an object
     * instance of class.

     */
    private QueryUtils() {
    }

    // Add in the fetchEarthquakeData() helper method that ties all the steps together - creating
    // a URL, sending the request, processing the response. Since this is the only “public”
    // QueryUtils method that the EarthquakeAsyncTask needs to interact with, make all other helper
    // methods in QueryUtils “private”.

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

    /**
     * Add for Networking QUIZ
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }



    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing the given JSON response.
     * BEFORE "A JSON response".
     */

    // Modify the extractEarthquakes() method that handles JSON parsing. I renamed it
    // to extractFeatureFromJson() and to take a JSON response String as input.
    // Instead of hardcoding the extractFeatureFromJson() method to only be able to parse the hardcoded
    // SAMPLE_JSON_RESPONSE String, this method becomes more reusable in different contexts if we
    //  accept a String input.

    // (B)public static ArrayList<Earthquake> extractEarthquakes() {
    private static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        // (B) ArrayList<Earthquake> earthquakes = new ArrayList<>();
        List<Earthquake> earthquakes = new ArrayList<>();
        // BEFORE >>> ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // (B) Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            // (IE)Now we begin creating a loop by initializing the counter variable i to be 0.
            // Before each iteration of the loop we test that the condition. i is less than the
            // length of the earthquakeArray is still true.
            // If it is true, we can execute all the lines of code within the loop.
            // At the end of one iteration, we increase the value of i by 1, indicated here by the
            // i++ syntax.
            // And then we can go ahead and loop around again.

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < earthquakeArray.length(); i++) {

                // TODO: Get earthquake JSONObject at position i
                // Get a single earthquake at position i within the list of earthquakes

                // (IE) Next we can pull out the JSON object at the specified position of the earthquakeArray.
                // We start with position 0, which is the initial value of the counter i variable.
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);


                // TODO: Get “properties” JSONObject
                // (IE) Once we have the current earthquake JSON object, we can extract out the JSON
                // object associated with the properties key. Remember that this lists out many of
                // the earthquakes' attributes. As you can see, these JSON objects are deeply nested
                // within the overall response.

                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                // But finally, we're at the level where we can access the individual values of the
                // properties JSONObject by referring to the key mag for magnitude, place for
                // location, and time for the time in milliseconds that the earthquake happened.
                //
                // For now, we can extract all these values as strings, because we're just displaying
                // them straight to the screen, and not doing any additional calculation or
                // formatting on them.

                // Extract the value for the key called "mag"
                double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = properties.getString("place");

                // Extract the value for the key called "time"
                long time = properties.getLong("time");

                // Extract the value for the key called "url"
                String url = properties.getString("url");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
                Earthquake earthquake = new Earthquake(magnitude, location, time, url);

                // TODO: Add earthquake to list of earthquakes
                //Then we can create a new earthquake object from these three strings.

                // Add the new {@link Earthquake} to the list of earthquakes.
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // (IE) After the loop finishes executing once per each earthquake in the features array,
        //  we will then build up a whole array list of earthquakes. Then we can return this list as
        // the return value of the method.

        // Return the list of earthquakes
        return earthquakes;
    }



}


