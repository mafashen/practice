package com.mafashen.framework.service.impl;

import com.mafashen.framework.service.CalculateService;

public class CalculateServiceImpl implements CalculateService {

	@Override
	public int add(Integer a, Integer b) {
		return a + b;
	}

	@Override
	public int subtract(Integer a, Integer b) {
		return a - b;
	}

	@Override
	public int multiply(Integer a, Integer b) {
		return a * b;
	}

	@Override
	public int divide(Integer a, Integer b) {
		return a / b;
	}
}
