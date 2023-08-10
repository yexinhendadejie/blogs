package com.blogs.utils;

import com.blogs.common.resultcode.ResultCodeEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Resp<T> {
  private Boolean success;
  private Integer code;
  private String message;
  private T data;

  private Resp(ResultCodeEnum resultCode, T data) {
    this.success = resultCode.getSuccess();
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
    this.data = data;
  }

  public Resp<T> put(String key, Object value) {
    if (data == null) {
      data = (T) new HashMap<String, Object>();
    }
    if (data instanceof Map) {
      ((Map<String, Object>) data).put(key, value);
    }
    return this;
  }

  /**
   * 业务成功返回业务代码和描述信息
   */
  public static Resp<Void> ok() {
    return new Resp<>(ResultCodeEnum.SUCCESS, null);
  }

  /**
   * 业务成功返回业务代码,描述和返回的参数
   */
  public static <T> Resp<T> ok(T data) {

    // 校验空数据
    if (data == null) {
      return new Resp<>(ResultCodeEnum.OK_EMPTY_DATA, data);
    }
    if (data instanceof List) {
      if (((List) data).isEmpty()) return new Resp<>(ResultCodeEnum.OK_EMPTY_DATA, data);
    }

    return new Resp<>(ResultCodeEnum.SUCCESS, data);
  }

  public static <T> Resp<T> ok(ResultCodeEnum resultCodeEnum) {
    return ok(resultCodeEnum, null);
  }

  public Resp msg(String message) {
    this.message = message;
    return (Resp<T>) this;
  }

  /**
   * 业务成功返回业务代码,描述和返回的参数
   */
  public static <T> Resp<T> ok(ResultCodeEnum resultCode, T data) {
    if (resultCode == null) {
      return ok(data);
    }

    return new Resp<>(resultCode, data);
  }

  /**
   * 业务异常返回业务代码,描述和返回的参数
   */
  public static <T> Resp<T> error(ResultCodeEnum resultCode) {
    return error(resultCode, null);
  }

  public static <T> Resp<T> error() {
    return error(ResultCodeEnum.ERROR, null);
  }

  /**
   * 业务异常返回业务代码,描述和返回的参数
   */
  public static <T> Resp<T> error(ResultCodeEnum resultCode, T data) {
    if (resultCode == null) {
      return new Resp<>(ResultCodeEnum.UNKNOWN_REASON, null);
    }
    return new Resp<>(resultCode, data);
  }

}
