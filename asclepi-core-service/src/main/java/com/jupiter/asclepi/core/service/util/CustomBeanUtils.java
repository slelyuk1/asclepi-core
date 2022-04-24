package com.jupiter.asclepi.core.service.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.Objects;

public final class CustomBeanUtils {

    public static <T> String[] getNullPropertyNames(T source, boolean checkIsReadable) {
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        return Arrays.stream(wrapper.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> checkIsReadable && wrapper.isReadableProperty(propertyName))
                .filter(propertyName -> Objects.isNull(wrapper.getPropertyValue(propertyName)))
                .toArray(String[]::new);
    }

    public static <T, G> void copyPropertiesWithoutNull(T toCopyFrom, G toCopyTo) {
        BeanUtils.copyProperties(toCopyFrom, toCopyTo, getNullPropertyNames(toCopyFrom, true));
    }

    private CustomBeanUtils() {
        throw new IllegalStateException("CustomBeanUtils class mustn't be instantiated");
    }
}
