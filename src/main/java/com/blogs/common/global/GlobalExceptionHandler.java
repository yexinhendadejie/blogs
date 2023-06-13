package com.blogs.common.global;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.blogs.common.exception.ServiceException;
import com.blogs.common.resultcode.ResultCodeEnum;
import com.blogs.utils.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


  /**
   * 业务中手动抛出的ServiceException
   *
   * @param e
   * @return
   */
  @ExceptionHandler(ServiceException.class)
  public Resp<Void> serviceException(ServiceException e) {
    log.warn(e.getMessage());
    return Resp.error(e.getResultCodeEnum()).msg(e.getMessage());
  }

  @ExceptionHandler(SaTokenException.class)
  public Resp saTokenException(SaTokenException e) {
    log.warn(e.getMessage());
    if (e instanceof NotLoginException)
      return Resp.error(ResultCodeEnum.TOKEN_ERROR_EXCEPTION).msg("用户未登录或已过期");
    if (e instanceof NotRoleException) return Resp.error(ResultCodeEnum.ROLE_EXCEPTION).msg("无此权限");

    return Resp.error(ResultCodeEnum.ERROR).msg(e.getMessage());
  }



  /***
   * 请求参数校验异常
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Resp jsonParamsException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<String> collect = fieldErrors.stream().map(o -> o.getDefaultMessage()).collect(Collectors.toList());

    log.warn("MethodArgumentNotValidException： 请求参数校验错误错误: " + collect);
    return Resp.error(ResultCodeEnum.REQUEST_FIELD_IS_NOT_VALID, collect).msg(collect.get(0));
  }

  @ExceptionHandler(BindException.class)
  public Resp bindExceptionHandler(BindException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<String> collect = fieldErrors.stream().map(o -> o.getDefaultMessage()).collect(Collectors.toList());

    log.warn("BindException 请求参数校验错误错误: " + collect);
    return Resp.error(ResultCodeEnum.REQUEST_FIELD_IS_NOT_VALID, collect).msg(collect.get(0));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public Resp constraintViolationExceptionHandler(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    List<String> collect = constraintViolations.stream().map(o -> o.getMessage()).collect(Collectors.toList());

    log.warn("ConstraintViolationException: 请求参数校验错误错误: " + collect);
    return Resp.error(ResultCodeEnum.REQUEST_FIELD_IS_NOT_VALID, collect).msg(collect.get(0));
  }


  @ExceptionHandler(RuntimeException.class)
  public Resp runtimeException(RuntimeException e) {
    log.warn(e.getMessage());
    return Resp.error(ResultCodeEnum.ERROR).msg(e.getMessage());
  }
}
