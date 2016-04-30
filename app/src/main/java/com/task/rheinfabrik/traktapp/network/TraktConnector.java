package com.task.rheinfabrik.traktapp.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by lisa on 30.04.2016.
 */
public class TraktConnector
{
    private final static String API_KEY_HEADER = "trakt-api-key";
    private final static String CLIENT_ID = "ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086";

    private final static String API_VERSION_HEADER = "trakt-api-version";
    private final static String TRAKT_API_VERSION = "2";

    private final static String CHAR_SET = "UTF-8";

    private final static int CONNECTION_TIMEOUT = 5000;
    private final static int READ_TIMEOUT = 5000;

    public String getMoviesFromTrakt(String url, HashMap<String, String> parameters) throws IOException
    {
        if(parameters != null)
        {
            Set<String> parametersSet = parameters.keySet();
            StringBuilder parameterUrlString = new StringBuilder(url);
            parameterUrlString.append("?");

            for(String parameter : parametersSet)
            {
                parameterUrlString.append(parameter);
                parameterUrlString.append("=");
                parameterUrlString.append(parameters.get(parameter));

                parameterUrlString.append("&");

            }

            parameterUrlString.deleteCharAt(parameterUrlString.length()-1);

            url = parameterUrlString.toString();
        }

        URL moviesUrl = new URL(url);

        HttpURLConnection traktConnection;
        traktConnection = (HttpURLConnection) moviesUrl.openConnection();
        traktConnection.setRequestProperty(API_KEY_HEADER, CLIENT_ID);
        traktConnection.setRequestProperty(API_VERSION_HEADER,TRAKT_API_VERSION);



        traktConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        traktConnection.setReadTimeout(READ_TIMEOUT);

        // handle issues
        int statusCode = traktConnection.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {
            // handle any other errors, like 404, 500,..
        }


        InputStream inputStream = new BufferedInputStream(traktConnection.getInputStream());

        String responseBody = getBodyString(inputStream);

        if (traktConnection != null) {
            traktConnection.disconnect();
        }

        return responseBody;
    }

    private static String getBodyString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }
}
