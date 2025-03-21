package com.codigofacilito.common.props.util;

import java.util.Properties;

public class PropsUtils {

    public static final String PROPS_SEPARATOR = ".";

    public static <T extends Enum<T>> T readEnumProperty(Properties properties, String propertyName, T defaultValue,
                                                         Class<T> enumType) {
        String value = readStringProperty(properties, propertyName, defaultValue.toString());
        try {
            return Enum.valueOf(enumType, value);
        } catch (Exception e) {
            System.err.println("Error while trying to cast enum, defaulting to " + defaultValue + ". Exception: " +
                    e.getMessage());
            return defaultValue;
        }
    }

    public static String readStringProperty(Properties properties, String propertyName, String defaultValue) {
        if (properties == null || propertyName == null) {
            return defaultValue;
        }
        return properties.getProperty(propertyName, defaultValue);
    }

    public static boolean readBooleanProperty(Properties properties, String propertyName, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(readStringProperty(properties, propertyName, String.valueOf(defaultValue)));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int readIntegerProperty(Properties properties, String propertyName, int defaultValue) {
        try {
            return Integer.parseInt(readStringProperty(properties, propertyName, String.valueOf(defaultValue)));
        } catch (Exception e) {
            return defaultValue;
        }
    }



}
