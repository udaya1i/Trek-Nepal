package com.trek.core.security.constant;

/**
 * @author udaya
 */
public interface TrekkerAuthority {

    String VIEW_TREK_DETAIL = "hasAuthority('VIEW_TREK_DETAIL')";
    String COMMENT = "hasAuthority('COMMENT')";
    String USER_DETAIL = "hasAuthority('USER_DETAIL')";
    String UPLOAD_CONTENT = "hasAuthority('UPLOAD_CONTENT')";
}