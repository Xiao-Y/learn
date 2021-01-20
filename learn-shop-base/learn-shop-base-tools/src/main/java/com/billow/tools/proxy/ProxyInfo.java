package com.billow.tools.proxy;

/**
 * 访问信息类
 * 
 * @author billow
 * 
 */
public class ProxyInfo {

	// --服务器的真实IP地址-- //
	private String localAddr;

	// --服务器的真实I端口号-- //
	private String port;

	// --代理服务器的IP的地址-- //
	private String proxyAddr = "null";

	public ProxyInfo(String localAddr, String port, String proxyAddr) {
		this.localAddr = localAddr;
		this.port = port;
		this.proxyAddr = proxyAddr;
	}

	public String getLocalAddr() {
		return localAddr;
	}

	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProxyAddr() {
		return proxyAddr;
	}

	public void setProxyAddr(String proxyAddr) {
		this.proxyAddr = proxyAddr;
	}

}
