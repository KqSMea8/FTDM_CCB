/**
 * 
 */
package com.sunyard.sunfintech.core.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.CodeStatus;
import com.sunyard.sunfintech.core.util.DateUtils;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author Ycv
 */
public class BaseResponse extends BaseMessage{

	public BaseResponse(){}
	public BaseResponse(CodeStatus codeStatus){
		this.setRecode(codeStatus.getCode());
		this.setRemsg(codeStatus.getMessage());
	}
	public BaseResponse(String code,String msg){
		this.setRecode(code);
		this.setRemsg(msg);
	}
	/**
	 * 商户请求订单号
	 */
	@NotBlank
	private String order_no;

	@NotBlank
//	@JSONField(serialzeFeatures = {SerializerFeature.NotWriteDefaultValue})
	private String order_status;  //部分接口不返回这个字段，测试要求响应结果里不显示该字段
	/**
	 * 签名数据
	 */
	@NotBlank
	private String sign="I'm Sign";

	@NotBlank
	private String trans_date= DateUtils.getyyyyMMddDate();

	@JSONField(serialize = false)
	private String trans_serial;
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getTrans_serial() {
		return trans_serial;
	}

	public void setTrans_serial(String trans_serial) {
		this.trans_serial = trans_serial;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		BaseResponse that = (BaseResponse) o;

		if (order_no != null ? !order_no.equals(that.order_no) : that.order_no != null) return false;
		if (order_status != null ? !order_status.equals(that.order_status) : that.order_status != null) return false;
		return sign != null ? sign.equals(that.sign) : that.sign == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (order_no != null ? order_no.hashCode() : 0);
		result = 31 * result + (order_status != null ? order_status.hashCode() : 0);
		result = 31 * result + (sign != null ? sign.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"recode='" + getRecode() + '\'' +
				"remsg='" + getRemsg() + '\'' +
				"order_no='" + order_no + '\'' +
				", order_status='" + order_status + '\'' +
				", sign='" + sign + '\'' +
				", trans_date='" + trans_date + '\'' +
				", trans_serial='" + trans_serial + '\'' +
				'}';
	}

	public static void main(String[] args) {
		BaseResponse baseResponse = new BaseResponse();
		System.out.println(baseResponse.toString());
	}

}
