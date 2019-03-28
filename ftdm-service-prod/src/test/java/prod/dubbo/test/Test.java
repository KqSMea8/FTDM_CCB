package prod.dubbo.test;

import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.prod.provider.IProductOptionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/test-spring-bean.xml");
		context.start();
		IProductOptionService ipo = context.getBean(IProductOptionService.class);
		ProdProductinfo p= new ProdProductinfo();
		//	List<FinancingInfoList> l =(List<FinancingInfoList>) new FinancingInfoList();
		p.setCreate_time(DateUtils.today());
		p.setCharge_off_auto("1");
		p.setPlat_no("dadfa");
		p.setCreate_by("ddd");
		p.setCreate_type("dede");
		p.setCycle(12);
		p.setCycle_unit("dad");
		p.setExpire_date("3322");
		p.setEnabled("!23");
		p.setParti_num(2323);
		p.setProd_id("11");
		p.setPlat_no("22");
		p.setProd_name("hahahah");
		p.setProd_type("dsads");
		p.setTotal_limit(new BigDecimal(1000.98));
		p.setRemain_limit(new BigDecimal(2323));
		p.setSaled_limit(new BigDecimal(32323));
		p.setValue_type("adasdasd");
		p.setCreate_type("ada");
		p.setPayout_type("ADA");
		p.setSell_date("dad");
		p.setValue_date("ADA");
		p.setExpire_date("DAD");
		p.setCycle(222);
		p.setCycle_unit("dad");
		p.setParti_num(11);
		p.setIst_year(new BigDecimal(3233));
		p.setProd_state("ddd");
		p.setRepay_type("dada");
		p.setProd_account("dd");
		p.setEnabled("dd");
		p.setRemark("dad");
		p.setCreate_by("dada");
		p.setUpdate_by("dd");
		p.setUpdate_time(DateUtils.today());
		p.setExt1("dada");
		p.setExt2("da");


		//ipo.publish(p,null,null );

	}

	@org.junit.Test
	public void testComp(){
		String a="20180716";
		String b="20180717";
		String c="20180715";
		String d="20180716";

		System.out.println(a.compareTo(b));
		System.out.println(a.compareTo(c));
		System.out.println(a.compareTo(d));
	}
}
