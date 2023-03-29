package com.blogs.common.exception;

import com.blogs.common.resultcode.ResultCodeEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ServiceException extends RuntimeException {
  private ResultCodeEnum resultCodeEnum;
  private String message;
  private Object data = null;


  public ServiceException(ResultCodeEnum resultCodeEnum, String message) {
    super(message);
    this.resultCodeEnum = resultCodeEnum;
    this.message = message;
  }

  public ServiceException(String message) {
    this(ResultCodeEnum.UNKNOWN_REASON, message);
  }

  public ServiceException(ResultCodeEnum resultCodeEnum) {
    this(resultCodeEnum, resultCodeEnum.getMessage());
  }
}
