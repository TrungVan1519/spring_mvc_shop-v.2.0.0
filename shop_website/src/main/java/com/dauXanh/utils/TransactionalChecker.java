package com.dauXanh.utils;

import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class TransactionalChecker {
	public static void checkTransactionalWorking() {
		
		TransactionStatus status = null;
		try {
			status = TransactionAspectSupport.currentTransactionStatus();
		} catch (NoTransactionException e) {
			System.err.println(e);
		}
		
		System.out.println(
				status != null ? "Transactional Annotation is working" : "Transactional Annotation is not working");
	}
}
