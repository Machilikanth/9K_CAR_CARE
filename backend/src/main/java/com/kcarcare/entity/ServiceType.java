package com.kcarcare.entity;

public enum ServiceType {
    WASH,
    POLISH,
    RUBBING,
    WAX,
    CLEAN;

    public static ServiceType fromString(String value) {
        if (value == null) {
            return null;
        }
        try {
            return ServiceType.valueOf(value.trim().replaceAll("\\s+", "_").toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
