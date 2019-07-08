package com.datazord;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class HttpUtils {

	private static final String[] IP_HEADER_CANDIDATES = {
			"X-FORWARDED-FOR",
			"Proxy-Client-IP",
			"WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR",
			"HTTP_X_FORWARDED",
			"HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR",
			"HTTP_FORWARDED",
			"HTTP_VIA",
			"REMOTE_ADDR" };

	public static String getIpAddressFromRequest(HttpServletRequest request) {

		String remoteAddr = null;

		if (request == null)
			return null;

		for (String header : IP_HEADER_CANDIDATES) {
			String ip = request.getHeader(header);

			if (StringUtils.isBlank(ip) || ip.equalsIgnoreCase("unknown"))
				continue;

			remoteAddr = ip;
			break;
		}

		if(remoteAddr != null && remoteAddr.contains("."))
			remoteAddr = remoteAddr.split("\\s*,\\s*")[0];

		return remoteAddr;
	}
}
