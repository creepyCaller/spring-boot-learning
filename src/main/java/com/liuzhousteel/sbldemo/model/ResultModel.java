package com.liuzhousteel.sbldemo.model;

import com.liuzhousteel.sbldemo.util.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResultModel {
    public static String SUCCESS = "success";
    public static String ERROR = "error";
    public static String FAILURE = "failure";

    private ResultModel(HttpStatus code, int status, String message, Object content) {
        this.code = code;
        this.timestamp = TimeUtil.getTime();
        this.status = status;
        this.message = message;
        this.content = content;
    }

    private ResultModel(HttpStatus code, int status, String message) {
        this.code = code;
        this.timestamp = TimeUtil.getTime();
        this.status = status;
        this.message = message;
    }

    /**
     * 动作成功结果集，需要设置状态
     * @return code = 200, message = success
     */
    public static ResultModel ok(int status) {
        return new ResultModel(HttpStatus.OK, status, ResultModel.SUCCESS);
    }

    /**
     * 动作成功结果集，需要设置状态、消息
     * @return code = 200
     */
    public static ResultModel ok(int status, String message) {
        return new ResultModel(HttpStatus.OK, status, message);
    }

    /**
     * 动作成功结果集，需要设置状态、内容
     * @return code = 200, message = success
     */
    public static ResultModel ok(int status , Object content) {
        return new ResultModel(HttpStatus.OK, status, ResultModel.SUCCESS, content);
    }

    /**
     * 动作成功结果集，需要设置状态、消息、内容
     * @return code = 200
     */
    public static ResultModel ok(int status,String message , Object content) {
        return new ResultModel(HttpStatus.OK, status, message, content);
    }
    //

    /**
     * 动作错误结果集，要设置状态
     * @return code = 404, message = error
     */
    public static ResultModel error(int status) {
        return new ResultModel(HttpStatus.BAD_REQUEST, status, ResultModel.ERROR);
    }

    /**
     * 动作错误结果集，需要设置状态、消息
     * @return code = 404
     */
    public static ResultModel error(int status, String message) {
        return new ResultModel(HttpStatus.BAD_REQUEST, status, message);
    }

    /**
     * 动作错误结果集，需要设置状态、内容
     * @return code = 404, message = error
     */
    public static ResultModel error(int status , Object content) {
        return new ResultModel(HttpStatus.BAD_REQUEST, status, ResultModel.ERROR, content);
    }

    /**
     * 动作错误结果集，需要设置状态、消息、内容
     * @return code = 404
     */
    public static ResultModel error(int status,String message , Object content) {
        return new ResultModel(HttpStatus.BAD_REQUEST, status, message, content);
    }
    
    private HttpStatus code;

    private long timestamp;

    private int status;

    private String message;

    private Object content;

}
