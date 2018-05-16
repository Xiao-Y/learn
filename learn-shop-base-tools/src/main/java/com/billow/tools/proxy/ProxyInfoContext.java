package com.billow.tools.proxy;

public class ProxyInfoContext {

	private static final ThreadLocal<ProxyInfo> myThreadLocal = new ThreadLocal<ProxyInfo>();

	public ProxyInfoContext(String localAddr, int port, String proxyAddr) {
		proxyAddr = proxyAddr == null ? "null" : proxyAddr;
		ProxyInfo info = new ProxyInfo(localAddr, new Integer(port).toString(), proxyAddr);
		myThreadLocal.set(info);
	}

	/**
	 * 获取代理对象
	 * 
	 * <br>
	 * added by liuyongtao<br>
	 * 
	 * @return
	 * 
	 * @date 2017年8月17日 上午9:25:14
	 */
	public static ProxyInfo getProxyInfo() {
		return myThreadLocal.get();
	}

	/**
	 * 销毁代理对象
	 * 
	 * <br>
	 * added by liuyongtao<br>
	 * 
	 * @date 2017年8月17日 上午9:25:31
	 */
	public void destroy() {
		myThreadLocal.set(null);
	}
}
