//package com.sunyard.sunfintech.web.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.sunyard.sunfintech.core.Constants;
//import com.sunyard.sunfintech.core.annotation.Log;
//import com.sunyard.sunfintech.core.annotation.Sign;
//import com.sunyard.sunfintech.core.base.BaseController;
//import com.sunyard.sunfintech.core.dic.CardType;
//import com.sunyard.sunfintech.core.dic.CusType;
//import com.sunyard.sunfintech.core.dic.IdType;
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.core.exception.BusinessMsg;
//import com.sunyard.sunfintech.core.util.CacheUtil;
//import com.sunyard.sunfintech.core.util.MD5;
//import com.sunyard.sunfintech.core.util.StringUtils;
//import com.sunyard.sunfintech.user.model.bo.*;
//import com.sunyard.sunfintech.web.business.UserBusiness;
////import ocxkeyboard.Util;
//import ocx.AESWithJCE;
//import ocx.GetRandom;
//import ocxkeyboard.Util;
//import org.apache.commons.codec.DecoderException;
//import org.apache.commons.lang.CharSetUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 账户系统返回页面
// *
// * @author KouKi
// * @version 20180115
// */
//@Controller
//@RequestMapping("/account")
//public class AccountPageController extends BaseController {
//
//    @Resource(name = "userBusiness")
//    private UserBusiness userBusiness;
//
//    @Value("${nginx.url}")
//    private String url;
//
//    /**
//     * 四要素开户页面
//     *
//     * @param registerRequest4
//     * @return
//     */
//    @RequestMapping("/four_elements_register")
//    @Sign
//    @Log(method = "fourElementsRegister")
//    public ModelAndView fourElementsRegister(HttpServletRequest httpServletRequest, RegisterRequest4 registerRequest4) {
//        //===========根据页面设置token===============
//        String token_origin_str = Constants.TOKEN_STATIC_STR + registerRequest4.getOrder_no();
//        String token_str = MD5.MD5Encode(token_origin_str, Constants.CHARACTERENCODING);
//        CacheUtil.getCache().set(token_origin_str, token_str, 1800);
//        //==========================================
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            validate(registerRequest4);
//            if (registerRequest4.getCard_type() == null) {
//                registerRequest4.setCard_type(CardType.DEBIT.getCode());
//            }
//            logger.info("【四要素开户】检查传入的参数是否是借记卡");
//            if (!CardType.DEBIT.getCode().equals(registerRequest4.getCard_type())) {
//                logger.info("【四要素开户】传入的值不是借记卡");
//                context.put("error_msg", "请使用借记卡开户");
//                forword("error", context);
//            }
//            String trans_serial = userBusiness.fourElementsRegister(registerRequest4);
//            //隐藏个人信息
//            registerRequest4.setCard_no(StringUtils.hideCardNo(registerRequest4.getCard_no()));
//            registerRequest4.setId_code(StringUtils.hideIdCode(registerRequest4.getId_code()));
//            registerRequest4.setPre_mobile(StringUtils.hideMobile(registerRequest4.getPre_mobile()));
//            //设置角色
//            registerRequest4.setRole(StringUtils.formatRole(registerRequest4.getRole()));
//            if (IdType.IDENTITY_CARD.getCode().equals(registerRequest4.getId_type())) {
//                registerRequest4.setId_type("身份证");
//            } else {
//                registerRequest4.setId_type("其他证件");
//            }
//            context.put("data", registerRequest4);
//            context.put("trans_serial", trans_serial);
//            context.put("token", token_str);
//        } catch (BusinessException e) {
//            logger.info("【四要素开户页面】返回错误页面", e);
//            context.put("error_msg", e.getBaseResponse().getRemsg());
//            return forword("error_page", context);
//        }
//        logger.info("【四要素开户页面】存入redis中的请求信息：" + registerRequest4.toString());
//        return forword("four_elements", context);
//    }
//
//    /**
//     * 三要素开户页面
//     *
//     * @param registerRequest3
//     * @return
//     */
//    @RequestMapping("/three_elements_register")
//    @Sign
//    @Log(method = "threeElementsRegister")
//    public ModelAndView threeElementsRegister(HttpServletRequest httpServletRequest, RegisterRequest3 registerRequest3) {
//        //===========根据页面设置token===============
//        String token_origin_str = Constants.TOKEN_STATIC_STR + registerRequest3.getOrder_no();
//        String token_str = MD5.MD5Encode(token_origin_str, Constants.CHARACTERENCODING);
//        CacheUtil.getCache().set(token_origin_str, token_str, 1800);
//        //==========================================
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            validate(registerRequest3);
//            if (registerRequest3.getCard_type() == null) {
//                registerRequest3.setCard_type(CardType.CREDIT.getCode());
//            }
//            logger.info("【三要素开户】检查传入的参数是否是信用卡");
//            if (!CardType.CREDIT.getCode().equals(registerRequest3.getCard_type())) {
//                logger.info("【三要素开户】传入的值不是信用卡");
//                context.put("error_msg", "请使用信用卡开户");
//                forword("error", context);
//            }
//            String trans_serial = userBusiness.threeElementsRegister(registerRequest3);
//            //隐藏个人信息
//            registerRequest3.setCard_no(StringUtils.hideCardNo(registerRequest3.getCard_no()));
//            registerRequest3.setId_code(StringUtils.hideIdCode(registerRequest3.getId_code()));
//            //设置角色
//            registerRequest3.setRole(StringUtils.formatRole(registerRequest3.getRole()));
//            if (IdType.IDENTITY_CARD.getCode().equals(registerRequest3.getId_type())) {
//                registerRequest3.setId_type("身份证");
//            } else {
//                registerRequest3.setId_type("其他证件");
//            }
//            logger.info("【三要素开户页面】存入redis中的请求信息：" + registerRequest3.toString());
//            context.put("data", registerRequest3);
//            context.put("trans_serial", trans_serial);
//            context.put("token", token_str);
//        } catch (BusinessException e) {
//            logger.info("【三要素开户页面】返回错误页面", e);
//            context.put("error_msg", e.getBaseResponse().getRemsg());
//            return forword("error_page", context);
//        }
//        return forword("three_elements", context);
//    }
//
//    /**
//     * 企业开户页面
//     *
//     * @param companyRegisterRequest
//     * @return
//     */
//    @RequestMapping("/company_register")
//    @Sign
//    @Log(method = "companyRegister")
//    public ModelAndView companyRegister(HttpServletRequest httpServletRequest, CompanyRegisterRequest companyRegisterRequest) {
//        //===========根据页面设置token===============
//        String token_origin_str = Constants.TOKEN_STATIC_STR + companyRegisterRequest.getOrder_no();
//        String token_str = MD5.MD5Encode(token_origin_str, Constants.CHARACTERENCODING);
//        CacheUtil.getCache().set(token_origin_str, token_str, 1800);
//        //==========================================
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            validate(companyRegisterRequest);
//            if (companyRegisterRequest.getCard_type() == null) {
//                companyRegisterRequest.setCard_type(CardType.DEBIT.getCode());
//            }
//            logger.info("【企业开户】检查传入的参数是否是借记卡");
//            if (!CardType.DEBIT.getCode().equals(companyRegisterRequest.getCard_type())) {
//                logger.info("【企业开户】传入的值不是借记卡");
//                context.put("error_msg", "请使用借记卡开户");
//                return forword("error_page", context);
//            }
//            String trans_serial = userBusiness.companyRegister(companyRegisterRequest);
//            //隐藏个人信息
//            companyRegisterRequest.setCard_no(StringUtils.hideCardNo(companyRegisterRequest.getCard_no()));
//            companyRegisterRequest.setMobile(companyRegisterRequest.getMobile());
//            //设置角色
//            companyRegisterRequest.setRole(StringUtils.formatRole(companyRegisterRequest.getRole()));
//            logger.info("【企业开户页面】存入redis中的请求信息：" + companyRegisterRequest.toString());
//            context.put("data", companyRegisterRequest);
//            context.put("trans_serial", trans_serial);
//            context.put("token", token_str);
//        } catch (BusinessException e) {
//            logger.info("【企业开户页面】返回错误页面", e);
//            context.put("error_msg", e.getBaseResponse().getRemsg());
//            return forword("error_page", context);
//        }
//        return forword("company_register", context);
//    }
//
//    /**
//     * 设置密码
//     *
//     * @param httpServletRequest
//     * @param setPwdRequest
//     * @return
//     */
//    @RequestMapping("/set_password")
//    @Sign
//    @Log(method = "setPassword")
//    public ModelAndView setPassword(HttpServletRequest httpServletRequest, SetPwdRequest setPwdRequest) {
//        //===========根据页面设置token===============
//        String token_origin_str = Constants.TOKEN_STATIC_STR + setPwdRequest.getOrder_no();
//        String token_str = MD5.MD5Encode(token_origin_str, Constants.CHARACTERENCODING);
//        CacheUtil.getCache().set(token_origin_str, token_str, 1800);
//        //==========================================
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            validate(setPwdRequest);
//            setPwdRequest = userBusiness.setPassword(setPwdRequest);
//
//            //隐藏个人信息
//            setPwdRequest.setId_code(StringUtils.hideIdCode(setPwdRequest.getId_code()));
//            //设置角色
//            setPwdRequest.setRole(StringUtils.formatRole(setPwdRequest.getRole()));
//            logger.info("【设置密码页面】存入redis中的请求信息：" + setPwdRequest.toString());
//            context.put("data", setPwdRequest);
//            context.put("trans_serial", setPwdRequest.getTrans_serial());
//            context.put("token", token_str);
//        } catch (BusinessException e) {
//            logger.info("【设置密码页面】返回错误页面", e);
//            context.put("error_msg", e.getBaseResponse().getRemsg());
//            return forword("error_page", context);
//        }
//        return forword("set_password", context);
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param httpServletRequest
//     * @param modifyPwdRequest
//     * @return
//     */
//    @RequestMapping("/modify_password")
//    @Sign
//    @Log(method = "modifyPassword")
//    public ModelAndView modifyPassword(HttpServletRequest httpServletRequest, ModifyPwdRequest modifyPwdRequest) {
//        //===========根据页面设置token===============
//        String token_origin_str = Constants.TOKEN_STATIC_STR + modifyPwdRequest.getOrder_no();
//        String token_str = MD5.MD5Encode(token_origin_str, Constants.CHARACTERENCODING);
//        CacheUtil.getCache().set(token_origin_str, token_str, 1800);
//        //==========================================
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            validate(modifyPwdRequest);
//            modifyPwdRequest = userBusiness.modifyPassword(modifyPwdRequest);
//            //隐藏个人信息
//            modifyPwdRequest.setId_code(StringUtils.hideIdCode(modifyPwdRequest.getId_code()));
//            logger.info("【修改密码页面】存入redis中的请求信息：" + modifyPwdRequest.toString());
//            context.put("data", modifyPwdRequest);
//            context.put("token", token_str);
//            if(CusType.PERSONAL.getCode().equals(modifyPwdRequest.getType())){
//                context.put("mobile_type", "预留手机号");
//                context.put("code_type", "身份证号");
//            }else {
//                context.put("mobile_type", "手机号");
//                context.put("code_type", "证件号");
//            }
//        } catch (Exception e) {
//            logger.info("【修改密码页面】返回错误页面", e);
//            if(e instanceof BusinessException) {
//                context.put("error_msg", ((BusinessException) e).getBaseResponse().getRemsg());
//            }else {
//                context.put("error_msg", e.getMessage());
//            }
//            return forword("error_page", context);
//        }
//        return forword("modify_password", context);
//    }
//
//    /**
//     * 设置/修改密码成功页面
//     *
//     * @param httpServletRequest
//     * @param setPwdRequest
//     * @return
//     */
//    @RequestMapping("/update_password_success")
//    @Log(method = "updatePasswordSuccess")
//    public ModelAndView updatePasswordSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SetPwdRequest setPwdRequest) {
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            Object obj = CacheUtil.getCache().get(Constants.SET_PWD_PARAMS_KEY + setPwdRequest.getTrans_serial());
//            if (obj == null) {
//                //原订单不存在
//                logger.info("【设置/修改密码成功页面】原单号不存在，订单号：" + setPwdRequest.getOrder_no());
//                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT, BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
//            }
//            String type;
//            if (setPwdRequest.getRole() == null) {
//                type = "修改";
//            } else {
//                type = "设置";
//            }
//            setPwdRequest = JSON.parseObject((String) obj, SetPwdRequest.class);
//            setPwdRequest.setTypeStr(type);
//            context.put("data", setPwdRequest);
//        } catch (BusinessException e) {
//            logger.info("【设置/修改密码成功页面】返回错误页面", e);
//            context.put("error_msg", e.getBaseResponse().getRemsg());
//            return forword("error_page", context);
//        }
//        return forword("xg_password", context);
//    }
//
//    /**
//     * mapping
//     *
//     * @return
//     */
//    @RequestMapping("/send_mapping")
//    @ResponseBody
//    @Log(method = "sendMapping")
//    public String sendMapping(HttpServletRequest httpServletRequest) {
//        String[] mArr = Util.getMappingString();
//        String mString = Util.getBase64(Arrays.toString(mArr)).trim();
//        return mString;
//    }
////    /**
////     * mapping
////     *
////     * @return
////     */
////    @RequestMapping("/send_mapping")
////    @Log(method = "sendMapping")
////    public ModelAndView sendMapping(HttpServletRequest httpServletRequest) {
////        Map<String, Object> context = getRootMap(httpServletRequest);
////        return forword("send_mapping", context);
////    }
//
//    /**
//     * mapping
//     *
//     * @return
//     */
//    @RequestMapping("/send_randkey")
//    @ResponseBody
//    @Log(method = "sendRandkey")
//    public String sendRandkey(HttpServletRequest httpServletRequest) {
//        String mcrypt_key = Util.getRandomKey(32).trim();
//        return mcrypt_key;
//    }
////    /**
////     * mapping
////     *
////     * @return
////     */
////    @RequestMapping("/send_randkey")
////    @Log(method = "sendRandkey")
////    public ModelAndView sendRandkey(HttpServletRequest httpServletRequest) {
////        Map<String, Object> context = getRootMap(httpServletRequest);
////        return forword("send_randkey", context);
////    }
//
//    /**
//     * mapping
//     *
//     * @return
//     */
//    @RequestMapping("/skey_enstr")
//    @ResponseBody
//    @Log(method = "skeyEnstr")
//    public String skeyEnstr(HttpServletRequest httpServletRequest) {
//        String sKey = GetRandom.generateString(32);
//        String enStr = AESWithJCE.getCipher(sKey, sKey);
//        return sKey + "," + enStr.trim();
//    }
//
////    /**
////     * mapping
////     *
////     * @return
////     */
////    @RequestMapping("/skey_enstr")
////    @Log(method = "skeyEnstr")
////    public ModelAndView skeyEnstr(HttpServletRequest httpServletRequest) {
////        Map<String, Object> context = getRootMap(httpServletRequest);
////        return forword("skey_enstr", context);
////    }
//
//    /**
//     * mapping
//     *
//     * @return
//     */
//    @RequestMapping("/srand_num")
//    @ResponseBody
//    @Log(method = "srandNum")
//    public String srandNum(HttpServletRequest httpServletRequest) {
//        String mcrypt_key = GetRandom.generateString(32);
//        return mcrypt_key;
//    }
////    /**
////     * mapping
////     *
////     * @return
////     */
////    @RequestMapping("/srand_num")
////    @Log(method = "srandNum")
////    public ModelAndView srandNum(HttpServletRequest httpServletRequest) {
////        Map<String, Object> context = getRootMap(httpServletRequest);
////        return forword("skey_enstr", context);
////    }
//
//    /**
//     * 个人开户成功页面
//     *
//     * @param httpServletRequest
//     * @param registerRequest4
//     * @return
//     */
//    @RequestMapping("/register_success")
//    @Log(method = "registerSuccess")
//    public ModelAndView registerSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, RegisterRequest4 registerRequest4) {
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        Object openAccountParamsObj = CacheUtil.getCache().get(Constants.OPEN_ACCOUNT_PARAMS_KEY + registerRequest4.getTrans_serial());
//        if (openAccountParamsObj == null) {
//            //原订单不存在
//            logger.info("【个人开户成功页面】原单号不存在，订单号：" + registerRequest4.getOrder_no());
//            throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT, BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
//        }
//        String platcust = registerRequest4.getPlatcust();
//        registerRequest4 = JSON.parseObject((String) openAccountParamsObj, RegisterRequest4.class);
//        registerRequest4.setPlatcust(platcust);
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            context.put("data", registerRequest4);
//        } catch (BusinessException e) {
//            logger.info("【个人开户成功页面】返回错误页面", e);
//            context.put("error_msg", e.getErrorMsg());
//            return forword("error_page", context);
//        }
//        return forword("person_suc", context);
//    }
//
//    /**
//     * 企业开户成功页面
//     *
//     * @param httpServletRequest
//     * @param companyRegisterRequst
//     * @return
//     */
//    @RequestMapping("/com_register_success")
//    @Log(method = "comRegisterSuccess")
//    public ModelAndView comRegisterSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CompanyRegisterRequest companyRegisterRequst) {
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        Object openAccountParamsObj = CacheUtil.getCache().get(Constants.OPEN_ACCOUNT_PARAMS_KEY + companyRegisterRequst.getTrans_serial());
//        if (openAccountParamsObj == null) {
//            //原订单不存在
//            logger.info("【企业开户成功页面】原单号不存在，订单号：" + companyRegisterRequst.getOrder_no());
//            throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT, BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
//        }
//        companyRegisterRequst = JSON.parseObject((String) openAccountParamsObj, CompanyRegisterRequest.class);
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        try {
//            context.put("data", companyRegisterRequst);
//        } catch (BusinessException e) {
//            logger.info("【企业开户成功页面】返回错误页面", e);
//            context.put("error_msg", e.getErrorMsg());
//            return forword("error_page", context);
//        }
//        return forword("com_success", context);
//    }
//
//    /**
//     * 错误页面
//     *
//     * @param httpServletRequest
//     * @param errorData
//     * @return
//     */
//    @RequestMapping("/error_page")
//    @Log(method = "errorPage")
//    public ModelAndView errorPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ErrorData errorData) {
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        Map<String, Object> context = getRootMap(httpServletRequest);
//        context.put("error_msg", errorData.getError_info());
//        return forword("error_page", context);
//    }
//
//    /**
//     * 所有ActionMap 统一从这里获取
//     *
//     * @return
//     */
//    private Map<String, Object> getRootMap(HttpServletRequest request) {
//        Map<String, Object> rootMap = new HashMap<>();
//        rootMap.put("url",url);
//        return rootMap;
//    }
//}
