package com.sunyard.sunfintech.core.exception;

import com.sunyard.sunfintech.core.CodeStatus;
import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * 基础业务异常
 * @author heroy
 * @version 2017/4/10
 */
public class BusinessException extends BaseException {

    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 基础返回信息
     * 包括，错误代码，错误信息，签名册，订单号
     */
    private BaseResponse baseResponse;

    public BusinessException(){
        super();
    }

    public BusinessException(Throwable throwable){
        super(throwable);
    }
    public BusinessException(String errorCode){
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = BusinessMsg.getMsg(errorCode);
        this.baseResponse = new BaseResponse();
        this.baseResponse.setRecode(errorCode);
        this.baseResponse.setRemsg(errorMsg);
    }

    public BusinessException(String errorCode, String errorMsg){
        super(errorCode + "【】" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.baseResponse = new BaseResponse();
        this.baseResponse.setRecode(errorCode);
        this.baseResponse.setRemsg(errorMsg);
    }
//    public BusinessException(CodeStatus codeStatus){
//        super(codeStatus.getCode() + "【】" + codeStatus.getMessage());
//        this.errorCode = codeStatus.getCode();
//        this.errorMsg =  codeStatus.getMessage();
//        this.baseResponse = new BaseResponse();
//        this.baseResponse.setRecode(errorCode);
//        this.baseResponse.setRemsg(errorMsg);
//    }

    public BusinessException(BaseResponse baseResponse){
        super(baseResponse.getRecode() + "【】" + baseResponse.getRemsg());
        this.baseResponse = baseResponse;
        this.errorCode = baseResponse.getRecode();
        this.errorMsg = baseResponse.getRemsg();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }
}
