package com.learn.reactive.constants;

public class ResponseCode {

    /******************** Success ********************/
    // Get
    public static final String FOUND = "200";

    // Post & Put
    public static final String CREATED = "201";

    // Delete
    public static final String DELETED = "200";

    /******************** Error **********************/
    // Get
    public static final String NOT_FOUND = "400";

    // Post & Put
    public static final String NOT_CREATED = "500";

    // Delete
    public static final String NOT_DELETED = "600";
}
