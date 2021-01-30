package com.dauXanh.utils;

public class StringChecker {

	public static boolean isEmptyOrNull(String s) {
		
		return s == null || s.trim().isEmpty();
	}
}
