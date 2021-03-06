package com.learn.reactive.constants;

public class ResponseMsg {

    /******************** Success ********************/
    // Get
    public static final String FOUND = "Available";
    public static final String SUCCESS = "Success";

    // Post & Put
    public static final String CREATED = "Created";
    public static final String UPDATED = "Updated";

    // Delete
    public static final String DELETED = "DELETED";
    public static final String NO_DATA_FOUND = "No data available";

    /******************* Error ***********************/
    // Get
    public static final String NOT_FOUND = "Not Available";
    public static final String AUTHORS_NOT_FOUD = "Author(s) are not available";
    public static final String BOOKS_NOT_FOUND = "Books are not available";
    public static final String FAILED = "Failed";

    // Post & Put
    public static final String NOT_CREATED = "Not Created";
    public static final String NOT_UPDATED = "Not Updated";

    // Delete
    public static final String NOT_DELETED = "Not Deleted";

    public static class AuthorResponseMsg {


        /******************** Error **********************/
        public static final String INVALID_AUTHOR = "Invalid author";
    }
}
