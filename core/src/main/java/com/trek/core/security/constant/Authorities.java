package com.trek.core.security.constant;

/**
 * @author udaya
 */
public interface Authorities {

    //Admin Authorities
    String DESTINATION_MANAGEMENT = "hasAuthority('DESTINATION_MANAGEMENT')";
    String ADMIN_MANAGEMENT = "hasAuthority('ADMIN_MANAGEMENT')";
    String ROLE_MANAGEMENT = "hasAuthority('ROLE_MANAGEMENT')";

    //Trekker Authorities
    String TREKKER_STORY = "hasAuthority('TREKKER_STORY')";
    String TREKKER_PROFILE = "hasAuthority('TREKKER_PROFILE')";
    String REVIEW_DESTINATION = "hasAuthority('REVIEW_DESTINATION')";

}
