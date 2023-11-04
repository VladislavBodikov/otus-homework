package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * "Разбирает" объект на составные части
 */
public interface EntityClassMetaData<T> {
    String getName();

    Constructor<T> getConstructor();

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    Field getIdField();

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();

    /** key - fieldName, value - setter of the field
     */
    Map<String,Method> getSettersOfAllFields(List<String> fieldNames);

    /** key - fieldName, value - getter of the field
     */
    Map<String,Method>  getGettersOfAllFields(List<String> fieldNames);
}
