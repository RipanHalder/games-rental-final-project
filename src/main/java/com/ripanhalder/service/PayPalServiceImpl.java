package com.ripanhalder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ripanhalder.paypal.PayPalConfig;

@Service
public class PayPalServiceImpl implements PayPalService {
	
	@Autowired
	private Environment environment;

	@Override
	public PayPalConfig getPayPalConfig() {
		PayPalConfig configs = new PayPalConfig();
		configs.setAuthtoken(environment.getProperty("paypal.authtoken"));
		configs.setBusiness(environment.getProperty("paypal.business"));
		configs.setPosturl(environment.getProperty("paypal.posturl"));
		configs.setReturnurl(environment.getProperty("paypal.returnurl"));
		return configs;
	}

}
