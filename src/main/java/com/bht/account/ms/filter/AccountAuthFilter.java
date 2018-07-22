package com.bht.account.ms.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.bht.account.ms.exception.AccountException;
import com.bht.account.ms.vo.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class AccountAuthFilter extends GenericFilterBean {

	@Autowired
	private Environment env;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		CustomHttpServletRequest customHttpsevReq = new CustomHttpServletRequest((HttpServletRequest)req);
		if(!isPblicUri(customHttpsevReq)){
		String authToken = customHttpsevReq.getheader("authToken");
		String token = authToken.substring(6);
		if(StringUtils.isNotBlank(authToken)){
			 
			Claims claims = Jwts.parser().setSigningKey(env.getProperty("jwt.secret.key")).parseClaimsJws(token)
					.getBody();
			
			String authtoken = (String) claims.get("token");
		}else{
			throw new AccountException("Invalid authHeader");
		}
		}
		chain.doFilter(customHttpsevReq, res);
	}

	private boolean isPblicUri(CustomHttpServletRequest customHttpsevReq) {
		String publicurls = env.getProperty("public.urls");
		String[] skipurls = publicurls.split(",");
		if(StringUtils.endsWithAny(customHttpsevReq.getRequestURI().substring(customHttpsevReq.getRequestURI().lastIndexOf("/")), skipurls)){
			return true;
		}
		return false;
	}

}
