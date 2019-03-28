/**
 *
 */
package com.sunyard.sunfintech.dao.test;

import org.mybatis.generator.api.ShellRunner;


/**
 * @author heroy
 *
 */
public class MybatisGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.setProperty("user.name", "Terry");
		String config = com.sunyard.sunfintech.dao.MybatisGenerator.class.getClassLoader().getResource("mybatis-generator/generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config, "-overwrite" };
		ShellRunner.main(arg);
	}
}
