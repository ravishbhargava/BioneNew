package com.bione.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Developer: Bione
 *
 * The Common Response model class
 */
public class CommonResponse {

    @SerializedName("code")
    private String statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("customer")
    private Object data;


    /**
     * Get message from api response
     *
     * @return message message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get status code of api response
     *
     * @return statusCode status code
     */
    public String getStatusCode() {
        return statusCode;
    }


    @Override
    public String toString() {
        return statusCode + " " + message + "\n" + data;
    }

    /**
     * Parses the response into an instance of provided class
     *
     * @param classRef the class reference
     * @param <T>      the class type
     * @return the parsed response object
     */
    public <T> T toResponseModel(final Class<T> classRef) {
        return new Gson().fromJson(new Gson().toJson(data), classRef);
    }
}
