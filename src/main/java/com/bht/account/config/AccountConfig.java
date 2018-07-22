package com.bht.account.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bht.account.ms.util.AccountUtil;
import com.bht.account.ms.vo.Account;
import com.bht.async.MsThreadPoolExecutor;

@Configuration
public class AccountConfig {
	
	@Autowired
	private AccountUtil acctUtil;
	
	@Bean(name="inmemoryMap")
	Map<String, List<Account>> createImememoryMap() {
		Map<String, List<Account>> userAccounts = new ConcurrentHashMap<>();
		Account account = Account.builder()
				.accountNumber("4532167894")
				.accountType("Primary")
				.email("test@gmail.com")
				.mobile("1234567891")
				.firstName("A")
				.lastName("Test")
				.build();
		List<Account> accounts = new ArrayList<>();
		accounts.add(account);
		userAccounts.put("1234567", accounts);
		return userAccounts;

	}
	
	@Bean
	public MsThreadPoolExecutor custompoolExecutor() {
		MsThreadPoolExecutor MsThreadPoolExecutor = new MsThreadPoolExecutor(10, 25, 10, TimeUnit.SECONDS,
				"Customthread", new LinkedBlockingDeque<>(10));
		return MsThreadPoolExecutor;

	}
	

}
