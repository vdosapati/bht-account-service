package com.bht.account.ms.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bht.account.ms.service.IAccountService;
import com.bht.account.ms.vo.AccountDetailRequest;
import com.bht.account.ms.vo.AccountOpenRequest;
import com.bht.account.ms.vo.AccountOpenResponse;
import com.bht.controller.IRestController;
import com.bht.vo.ErrorInfo;
import com.bht.vo.MSRequestScope;
import com.bht.vo.ServiceResponse;

@Controller
public class AccountController implements IRestController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private MSRequestScope requestScope;

	@Autowired
	private IAccountService acctServce;

	/**
	 * Service exposed to open secondary account
	 * 
	 * @param servletRequest
	 * @param accountOpenRequest
	 * @return
	 */
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ServiceResponse<AccountOpenResponse>> openAccount(
			HttpServletRequest servletRequest, @RequestBody @Valid AccountOpenRequest accountOpenRequest) {
		return invokeServiceResponse(servletRequest, accountOpenRequest, acctServce::openAccount);

	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ServiceResponse<AccountOpenResponse>> getAccountDetails(
			HttpServletRequest servletRequest) {
		DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm:ss");

		ErrorInfo errorInfo = ErrorInfo.builder().timeStamp(LocalDateTime.now().format(formatter)).correlationId("")
				.errorCode("00").errorMessage("Sucsess").externalErrors(new ArrayList<>())
				.jn(System.getProperty("server.id")).build();
		ServiceResponse serviceResponse = new ServiceResponse<>();
		addExternalErrors(errorInfo);
		serviceResponse.setData(acctServce.getDetails()	);
		serviceResponse.setErrorInfo(errorInfo);
		return new ResponseEntity<ServiceResponse<AccountOpenResponse>>(serviceResponse, HttpStatus.OK);
	}

	@Override
	public MSRequestScope getExceptionCollections() {
		return requestScope;
	}
}
