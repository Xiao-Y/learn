package com.billow.job.exception;

/**
 * 获取Bean异常
 * 
 * @author liuyongtao
 * 
 * @date 2017年4月18日 下午4:23:20
 */
public class NullBeanException extends NullPointerException {

	private static final long serialVersionUID = 6699554902890756807L;

	public NullBeanException() {
		super();
	}

	public NullBeanException(String name) {
		super(name + "：没有获取到Bean");
	}

	public <T> NullBeanException(Class<T> clazz) {
		super(clazz.getClass().getName() + "：没有获取到Bean");
	}
}
