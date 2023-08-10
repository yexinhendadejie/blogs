package com.blogs.common.validator.anno;

import com.blogs.common.validator.ImageFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageFormatValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFormat {
  String message() default "不允许的图片格式";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
