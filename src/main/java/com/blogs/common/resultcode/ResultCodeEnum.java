package com.blogs.common.resultcode;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResultCodeEnum {

  SUCCESS(true, 20000, "成功"),
  OK_EMPTY_DATA(true, 20001, "空数据"),
  SEND_OK(true, 20003, "发送成功"),
  ERROR(false, 40000, "发生错误"),
  TOKEN_ERROR_EXCEPTION(false, 40001, "TOKEN过期或已失效"),
  ROLE_EXCEPTION(false, 40003, "无此权限"),
  REQUEST_FIELD_IS_NOT_VALID(false, 40012, "请求字段不合规定"),
  DATA_ALREADY_EXISTS(false, 40013, "数据已经存在"),
  DATA_IS_NOT_EXISTS(false, 40014, "数据不存在"),
  SMS_ERROR(true, 40015, "短信验证码发送异常"),
  DATA_FORMAT_ERROR(false, 40016, "数据格式校验出错"),
  SEND_FAIL(false, 40017, "发送失败"),
  FILE_UPLOAD_ERROR(false, 40018, "文件上传错误"),
  DATA_IS_NOT_SUPPORT(false, 40019, "数据不支持"),
  UNKNOWN_REASON(false, 49999, "未知错误"),

  SERVICE_SAME(false,300001,"两次数据相同"),

  SERVICE_ID_EMPTY(false,300002,"没有这条数据信息"),

  WEBSOCKET_SUCCESS(true, 200000, "成功"),
  WEBSOCKET_OPEN_SUCCESS(true, 200001, "websocket连接成功"),
  WEBSOCKET_MESSAGE_SUCCESS(true, 200002, "websocket消息接收成功"),
  WEBSOCKET_CLOSE_SUCCESS(true, 200003, "websocket关闭成功"),
  WEBSOCKET_ERROR_OF_CLIENT(false, 400000, "客户端WebSocket出错"),
  WEBSOCKET_ERROR_OF_SERVER(false, 500000, "服务端WebSocket出错");



  private final Boolean success;

  private final Integer code;

  private final String message;

  ResultCodeEnum(boolean success, int code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
  }
}
