package com.company.example.util;

import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

@Repository
public class RequestIPUtil {

	// 内网nginx 设置只能内网访问
	public static boolean getWhere(HttpServletRequest request) {
		String where = request.getHeader("where");
		if (where == null) {
			return false;
		} else if ("kafdoafda#f658adf*2s37^w(ew%43sdf#afdaf2ksijuewehjhk&jihwe9".equals(where)) {
			return true;
		} else {
			return false;
		}

	}

}
