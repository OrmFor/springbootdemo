package com.jlkj.web.shopnew.util;

public enum LmSignatureAlgorithm {

	SHA1WithRSA("SHA1WithRSA"),
	
	MD5WithRSA("MD5WithRSA");
	
	private String signAlgorithm;

	private LmSignatureAlgorithm(String signAlgorithm) {
		this.signAlgorithm = signAlgorithm;
	}

	public String getSignAlgorithm() {
		return signAlgorithm;
	}
}
