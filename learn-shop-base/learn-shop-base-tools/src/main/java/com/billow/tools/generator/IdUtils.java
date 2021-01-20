package com.billow.tools.generator;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class IdUtils {

	/**
	 * 
	 * 
	 * <br>
	 * added by liuyongtao<br>
	 * 
	 * @return
	 * 
	 * @date 2017年4月20日 下午5:54:26
	 */
	public static String createId(int leng) {
		String id = UUID.randomUUID().toString();

		id = DEKHash(id) + "";

		int diff = leng - id.length();
		String randStr = RandomStringUtils.randomAlphabetic(leng);
		for (int i = 0; i < diff; i++) {
			int randIndex = (int) (Math.random() * randStr.length());
			int index = (int) (Math.random() * id.length());
			id = id.substring(0, index) + randStr.charAt(randIndex) + id.substring(index, id.length());
		}
		return id;
	}

	private static int DEKHash(String str) {
		int hash = str.length();

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
		}

		return (hash & 0x7FFFFFFF);
	}

//	public static void main(String[] args) {
//		Set<String> set = new HashSet<>();
//		for (int i = 0; i < 10000000; i++) {
//			set.add(createId(12));
//			System.out.println(i);
//		}
//		System.out.println(set.size());
//	}
}
