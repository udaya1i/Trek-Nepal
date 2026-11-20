package com.trek.trekker.dto.request;

import com.trek.core.entity.Trekker;

public record TrekkerOauthResult(Trekker trekker, boolean newUser) {
}