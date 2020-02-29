package com.learn.reactive.constants;

public class ResponseMsg {

    /******************** Success ********************/
    // Get
    public static final String FOUND = "Available";

    // Post & Put
    public static final String CREATED = "Created";
    public static final String UPDATED = "Updated";

    // Delete
    public static final String DELETED = "DELETED";
    public static final String NO_DATA_FOUND = "No data available";

    /******************* Error ***********************/
    // Get
    public static final String NOT_FOUND = "Not Available";
    public static final String AUTHORS_NOT_FOUD = "Authors are not available";

    // Post & Put
    public static final String NOT_CREATED = "Not Created";
    public static final String NOT_UPDATED = "Not Updated";

    // Delete
    public static final String NOT_DELETED = "Not Deleted";
}
