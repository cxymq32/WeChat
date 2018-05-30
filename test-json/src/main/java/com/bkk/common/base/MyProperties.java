package com.bkk.common.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * 
 * @author bkk
 * @version 2016年8月17日下午5:12:09
 */
public class MyProperties {

	private static String param;

	/**
	 * 读取配置文件
	 * 
	 * <pre>
	 * 例：String imgUrl = PropertiesUtil.getProperties("config.properties", "picUrl");
	 * </pre>
	 * 
	 * @param propertiesName
	 *            配置文件名称(项目/WEB-INF/classes/目录下的)
	 * @param paramName
	 *            参数名称
	 * @return 参数值
	 */
	public static String getProperties(String propertiesName, String paramName) {
		MyProperties temp = new MyProperties();
		Properties prop = new Properties();
		String propertiesDir = temp.getClass().getClassLoader().getResource("").getPath() + propertiesName;
		try {
			File f = new File(propertiesDir);
			InputStream in = new FileInputStream(f);
			prop.load(in);
			param = prop.getProperty(paramName).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return param;

	}

}
