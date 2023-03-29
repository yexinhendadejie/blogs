package com.blogs.common.validator.anno;


import com.blogs.common.validator.LoginTypeEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = LoginTypeEnumValidator.class)
@Target({
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.TYPE,
        ElementType.TYPE_PARAMETER,
        ElementType.TYPE_USE,
        ElementType.CONSTRUCTOR
    })
@Retention(RUNTIME)
@Documented
public @interface LoginTypeEnum {
    //提示信息,可以写死,可以填写国际化的key
    String message() default "数据类型错误或者校验失败";

    String[] strValues() default {};

    int[] intValues() default {};

    //下面这两个属性必须添加
    Class<?>[] groups() default {};

    Class<?> enumValue() default Class.class;

    Class<? extends Payload>[] payload() default {};

    @Target({
            ElementType.FIELD,
            ElementType.ANNOTATION_TYPE,
            ElementType.TYPE,
            ElementType.TYPE_PARAMETER,
            ElementType.TYPE_USE,
            ElementType.CONSTRUCTOR
    })
    @Retention(RUNTIME)
    @Documented
    @interface List{
        LoginTypeEnum[] value();
    }
}