package com.heisyenberg.springcrmbot.models;

public enum ChatState {
    START,
    AWAITING_AUTHENTICATION,
    AWAITING_LOGIN,
    AWAITING_REGISTRATION,
    LOGGED_IN,
    AWAITING_CLIENT_DATA,
    AWAITING_PRODUCT_DATA,
    AWAITING_SALE_DATA
}
