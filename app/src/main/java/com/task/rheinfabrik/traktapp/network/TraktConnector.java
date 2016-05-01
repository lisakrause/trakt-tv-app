package com.task.rheinfabrik.traktapp.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

/**
 * The class TraktConnector provides an http client that communicates with the trakt.tv
 * REST Service.
 */
public class TraktConnector
{
    /**
     * The request header in which the client ID of this app is transmitted.
     */
    private final static String API_KEY_HEADER = "trakt-api-key";
    /**
     * The client ID of this app that is put into one of the request headers.
     */
    private final static String CLIENT_ID = "ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086";


    /**
     * The request header in which the trakt.tv API version is transmitted.
     */
    private final static String API_VERSION_HEADER = "trakt-api-version";
    /**
     * The API version of the trakt.tv API that is put into one of the request headers.
     */
    private final static String TRAKT_API_VERSION = "2";

    /**
     * The timeout that is set for connecting to the trakt.tv server.
     */
    private final static int CONNECTION_TIMEOUT = 2000;

    /**
     * The timeout that is set for receiving data from the trakt.tv server.
     */
    private final static int READ_TIMEOUT = 5000;


    /**
     * Downloads a list of movies on the basis of the given URL and parameters, e.g. popular movies.
     *
     * @param url The URL that is used to send an http request to the trakt.tv server.
     * @param parameters The parameters that are used in the request (might also be null).
     * @return The response body in JSON format as a String.
     * @throws IOException If there are problems connecting to the server, getting the response code
     * or receiving the response.
     */
    public static String getMoviesFromTrakt(String url, HashMap<String, String> parameters)
                                                                            throws TraktException,
                                                                                    IOException

    {
        //Only if there are parameters set, try to attach them to the URL
        if(parameters != null)
        {

            Set<String> parametersSet = parameters.keySet();

            //----Build parameter string to attach to the URL
            StringBuilder parameterUrlString = new StringBuilder(url);
            parameterUrlString.append("?");

            for(String parameter : parametersSet)
            {
                parameterUrlString.append(parameter);
                parameterUrlString.append("=");
                parameterUrlString.append(parameters.get(parameter));

                parameterUrlString.append("&");

            }

            //since there is one '&' added that is not needed, remove it
            parameterUrlString.deleteCharAt(parameterUrlString.length()-1);

            //URL finished with parameters
            url = parameterUrlString.toString();
        }

        //----setup URL object
        URL moviesUrl = new URL(url);

        //----setup connection with request headers and timeouts
        HttpURLConnection traktConnection = (HttpURLConnection) moviesUrl.openConnection();

        traktConnection.setRequestProperty(API_KEY_HEADER, CLIENT_ID);
        traktConnection.setRequestProperty(API_VERSION_HEADER,TRAKT_API_VERSION);

        traktConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        traktConnection.setReadTimeout(READ_TIMEOUT);

        //----check response code
        int statusCode = traktConnection.getResponseCode();
        //if there were problems of any kind...
        if (statusCode != HttpURLConnection.HTTP_OK) {
            //...stop download
            if (traktConnection != null) {
                traktConnection.disconnect();
            }
            throw new TraktException("Request terminated with " + statusCode);
        }

        //----download response body
        InputStream inputStream = new BufferedInputStream(traktConnection.getInputStream());

        //---transform response body to string
        String responseBody = getBodyString(inputStream);

        //----disconnect
        if (traktConnection != null) {
            traktConnection.disconnect();
        }

        //---return result
        return responseBody;
    }

    /**
     * Transforms the response body of the http request to a String.
     *
     * @param inputStream The stream that is used to read the response body.
     * @return Returns the response body as String.
     * @throws IOException If there are problems reading the response or closing the stream.
     */
    private static String getBodyString(InputStream inputStream) throws IOException
    {
        //---setup
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        //----reading

        //attach each line of the response body to a String
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        //---close the reader for the response stream
        bufferedReader.close();

        //Returns the response body as String
        return stringBuilder.toString();
    }
}
