package com.shileiyu.baseapp.common.bean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;

/**
 * @author shilei.yu
 * @since on 2018/3/17.
 */

public class DateConverter implements PropertyConverter<Date, Long> {

    @Override
    public Date convertToEntityProperty(Long databaseValue) {
        if (databaseValue != null) {
            return new Date(databaseValue);
        }
        return null;
    }

    @Override
    public Long convertToDatabaseValue(Date entityProperty) {
        return entityProperty.getTime();
    }
}
