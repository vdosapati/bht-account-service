package com.bht.account.ms.service;

import com.bht.account.ms.vo.AccountOpenRequest;
import com.bht.account.ms.vo.AccountOpenResponse;
import com.bht.vo.FGRequestContext;

public interface IAccountService {

	AccountOpenResponse openAccount(FGRequestContext reqContext,AccountOpenRequest accountOpenRequest);
	AccountOpenResponse getDetails();

}