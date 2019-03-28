package com.sunyard.sunfintech.core.exception;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.rpc.RpcException;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author heroy
 * @version 2017/4/11
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    private final Logger logger = LogManager.getLogger(this.getClass());

//    @Value(value="#{deployment.model}")
    private String deploymentModel="debug";

    /**
     * 所有异常处理
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param ex 异常
     * @return BaseResponse
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @Sign
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex){
        BaseResponse baseResponse = new BaseResponse();
        if (ex instanceof BusinessException) {
            logger.info("【异常处理 -> 捕获到业务异常】：" + ex.getMessage());
            baseResponse = ((BusinessException) ex).getBaseResponse();
        }else{
            logger.error(Constants.EXCEPTION_HEAD, ex);
            logger.info("【异常处理 -> 捕获到系统异常】：" + ex.getClass());
            if(ex instanceof RemotingException){
                logger.error("【异常处理 -> dubbo异常信息】：" + ex.getMessage());
            }else if(ex instanceof RpcException){
                logger.error("【异常处理 -> dubbo异常信息】：" + ex.getMessage());
            }
            logger.error("【异常处理 -> 系统异常信息】：" + ex.getMessage());
            baseResponse.setRecode(BusinessMsg.FAIL);
            if(deploymentModel.equals("debug")){
                baseResponse.setRemsg(ex.getMessage()==null?String.valueOf(ex.getMessage())+"|"+ex.getStackTrace()[0].toString():ex.getMessage());
            }else {
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            }
        }
        response.setContentType("application/json;charset=UTF-8");
        return baseResponse;
    }
}
