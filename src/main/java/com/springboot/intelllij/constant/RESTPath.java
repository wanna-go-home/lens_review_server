package com.springboot.intelllij.constant;

public class RESTPath {
    public static final String API_PREFIX = "/api";

    public static final String LENS_INFO =  API_PREFIX + "/lens";
    
    public static final String USER = API_PREFIX + "/user";

    public static final String BOARDS = "/boards";
    public static final String FREE_BOARD = API_PREFIX + BOARDS + "/article";
    public static final String REVIEW_BOARD = API_PREFIX + BOARDS + "/review-board";

    public static final String ID = "/{id}";
    public static final String COMMENTS = ID + "/comments";
    public static final String COMMENT_ID = COMMENTS + "/{commentId}";

    public static final String POST_LIKE = ID + "/like";
    public static final String COMMENT_LIKE = COMMENT_ID + "/like";
}
