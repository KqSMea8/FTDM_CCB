
package com.sunyard.sunfintech.dao;

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
		
		System.setProperty("user.name", "djh");
		String config = MybatisGenerator.class.getClassLoader().getResource("mybatis-generator/generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config, "-overwrite" };
		ShellRunner.main(arg);
	}
}
