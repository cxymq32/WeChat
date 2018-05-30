package com.bkk.common;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class UtilsWeb {
	public static String saveFile(MultipartFile file, String path) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				File filepath = new File(path);
				if (!filepath.exists())
					filepath.mkdirs();
				// 文件保存路径
				String savePath = path + file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(savePath));
				return savePath;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
