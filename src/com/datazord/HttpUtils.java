package com.datazord;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

	public static String getIpAddressFromRequest(HttpServletRequest request) {

		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");

			if (remoteAddr == null || "".equals(remoteAddr))
				remoteAddr = request.getRemoteAddr();
		}

		return remoteAddr;
	}
}
