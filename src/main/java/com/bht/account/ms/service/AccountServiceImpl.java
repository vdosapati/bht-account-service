package com.bht.account.ms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bht.account.ms.exception.AccountException;
import com.bht.account.ms.util.AccountUtil;
import com.bht.account.ms.vo.Account;
import com.bht.account.ms.vo.AccountDetailRequest;
import com.bht.account.ms.vo.AccountDetailResponse;
import com.bht.account.ms.vo.AccountOpenRequest;
import com.bht.account.ms.vo.AccountOpenResponse;
import com.bht.vo.FGRequestContext;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired @Qualifier("inmemoryMap")
	private Map<String, List<Account>> accounts;
	
	@Autowired
	private AccountUtil acctUtil;
	
	
	@Override
	public AccountOpenResponse openAccount(FGRequestContext reqContext, AccountOpenRequest accountOpenRequest) {
		AccountOpenResponse accountOpenResponse = new AccountOpenResponse();
		
		accountOpenResponse.setCustomerId(accountOpenRequest.getCustomerId());
			if(accounts.containsKey(accountOpenRequest.getCustomerId())){
				List<Account> mapAccounts = accounts.get(accountOpenRequest.getCustomerId());
				if(mapAccounts.size()==2){
					throw new AccountException("511", "Customer already has secondary account");
				}
				Account secondaryaccount = Account.builder()
						.accountNumber(acctUtil.createRandomNumber(10))
						.accountType("secondary")
						.address(mapAccounts.get(0).getAddress())
						.email(mapAccounts.get(0).getEmail())
						.firstName(mapAccounts.get(0).getFirstName())
						.lastName(mapAccounts.get(0).getLastName())
						.mobile(mapAccounts.get(0).getMobile())
						.build();
				mapAccounts.add(secondaryaccount);
				AccountDetailResponse accountDetailResponse = new AccountDetailResponse();
				accountDetailResponse.setAccountDetails(mapAccounts);
				accountOpenResponse.setAccountDetailResponse(accountDetailResponse);
				accounts.put(accountOpenRequest.getCustomerId(), mapAccounts);
			}else{
				throw new AccountException("510", "CustId not found");
			}
		
		return accountOpenResponse;
	}


	@Override
	public AccountOpenResponse getDetails() {
		AccountOpenResponse accountOpenResponse = new AccountOpenResponse();
		
		AccountDetailResponse accountDetailResponse = new AccountDetailResponse();
		accountDetailResponse.setAccounts(accounts);
		accountOpenResponse.setAccountDetailResponse(accountDetailResponse);
		return accountOpenResponse;
	}



}
