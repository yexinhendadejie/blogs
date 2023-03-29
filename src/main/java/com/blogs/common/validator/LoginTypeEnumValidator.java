package com.blogs.common.validator;

import com.blogs.common.validator.anno.LoginTypeEnum;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class LoginTypeEnumValidator implements ConstraintValidator<LoginTypeEnum, Object> {

    private String[] strValues;
    private int[] intValues;

    private Class<?> cls;

    @Override
    public void initialize(LoginTypeEnum constraintAnnotation) {
        strValues = constraintAnnotation.strValues();
        intValues = constraintAnnotation.intValues();
        cls = constraintAnnotation.enumValue();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;

        if (cls.isEnum()) {
            Object[] objects = cls.getEnumConstants();
            for (Object obj : objects) {
                Method getNameMethod = cls.getDeclaredMethod("getName");
                String name = String.valueOf(getNameMethod.invoke(obj));
                if(name.equals(String.valueOf(value))) return true;
            }

            if(value instanceof String){
                for (String str : strValues){
                    if(str.equals(String.valueOf(value)))return true;
                }
            }

            if(value instanceof Integer){
                for(Integer integer : intValues){
                    if(integer.equals(String.valueOf(value)));
                }
            }
        }

        return false;
    }
}
