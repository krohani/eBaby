package com.tobeagile.training.ebaby.services;

import com.tobeagile.training.ebaby.domain.Auction;

public class CarLogger extends LoggerDecorator {
		
	@Override
	public void process() {	
		AuctionLogger logger = AuctionLogger.getInstance();
		logger.log("c:\\auctionLog.log", "car sale");
	}
}