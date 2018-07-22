package com.bht.account.ms.util;

import org.springframework.stereotype.Component;

@Component
public class AccountUtil {

	public  String createRandomNumber(long len) {
		if (len > 18)
			throw new IllegalStateException("To many digits");
		long tLen = (long) Math.pow(10, len - 1) * 9;
		long number = (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;
		String tVal = number + "";
		if (tVal.length() != len) {
			throw new IllegalStateException("The random number '" + tVal + "' is not '" + len + "' digits");
		}
		return tVal;
	}
}
