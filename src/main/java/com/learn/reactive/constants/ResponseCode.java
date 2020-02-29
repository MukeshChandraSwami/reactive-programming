package com.learn.reactive.constants;

public class ResponseCode {

    /******************** Success ********************/
    // Get
    public static final String FOUND = "200";
    public static final String SUCCESS = "200";

    // Post & Put
    public static final String CREATED = "201";
    public static final String UPDATED = "251";

    // Delete
    public static final String DELETED = "300";
    public static final String NO_DATA_FOUND = "301";

    /******************** Error **********************/
    // Get
    public static final String NOT_FOUND = "400";
    public static final String FAILED = "400";

    // Post & Put
    public static final String NOT_CREATED = "500";
    public static final String NOT_UPDATED = "551";

    // Delete
    public static final String NOT_DELETED = "600";
}
