package com.blogs.common.validator;

import com.blogs.common.validator.anno.ImageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFormatValidator implements ConstraintValidator<ImageFormat, String> {
  private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

  @Override
  public void initialize(ImageFormat constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Allow null values
    }

    for (String ext : ALLOWED_EXTENSIONS) {
      if (value.toLowerCase().endsWith(ext)) {
        return true;
      }
    }

    return false;
  }
}
