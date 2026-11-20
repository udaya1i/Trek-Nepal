package com.trek.core.security.util;

import java.util.List;

/**
 * @author udaya
 */
public interface SecurityContextHolder {

    <T> T getCurrentUser();

    String getLoggedUsername();

    List<String> getAuthorities();
}
