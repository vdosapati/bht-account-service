package com.bht.account.ms.vo;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AccountOpenResponse extends Response{
	private String customerId;
	private AccountDetailResponse accountDetailResponse;

}
