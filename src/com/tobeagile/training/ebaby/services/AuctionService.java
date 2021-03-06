package com.tobeagile.training.ebaby.services;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.tobeagile.training.ebaby.domain.Auction;
import com.tobeagile.training.ebaby.domain.Auction.AuctionCategory;
import com.tobeagile.training.ebaby.domain.Auction.AuctionState;
import com.tobeagile.training.ebaby.domain.AuctionNotifier;
import com.tobeagile.training.ebaby.domain.FeeDecorator;
import com.tobeagile.training.ebaby.domain.FeeFactory;
import com.tobeagile.training.ebaby.domain.LoggerDecorator;
import com.tobeagile.training.ebaby.domain.LoggerFactory;
import com.tobeagile.training.ebaby.domain.User;


public class AuctionService {
	private Set<Auction> auctions = null;
	
	public AuctionService()
	{
		auctions = new HashSet<Auction>();
	}

	public Auction createAuction(User u, String description, Double price,
			LocalDateTime auctionStartDateTime, LocalDateTime auctionEndDateTime) {
		if(u.isLoggedIn() && u.isSeller() && validationAuctionTimes(auctionStartDateTime, auctionEndDateTime))
		{
			Auction auction = new Auction(u,description,price,auctionStartDateTime,auctionEndDateTime);
			auctions.add(auction);
			return auction;
		}
		return null;
	}
	
	private boolean validationAuctionTimes(LocalDateTime auctionStartTime, LocalDateTime auctionEndTime)
	{
		if(auctionEndTime.isAfter(auctionStartTime) && auctionStartTime.isAfter(LocalDateTime.now()))
			return true;
		return false;
	}
	
	public void changeAuctionState(Auction auction) {
		
		if(auction.getAuctionState().equals(AuctionState.NOT_STARTED))
		{
			auction.setAuctionState(AuctionState.OPEN);
		}
		else if(auction.getAuctionState().equals(AuctionState.OPEN))
		{
			auction.setAuctionState(AuctionState.CLOSED);
		}		
	}

	public void onClose(Auction auction) {
		Set<FeeDecorator> fees = FeeFactory.getFees(auction);
		Double buyerFees = 0.00;
		for(FeeDecorator fee: fees)
		{
			fee.process();
			buyerFees += fee.getBuyerFee();
		}
		Set<LoggerDecorator> logs = LoggerFactory.getLogs(auction);
		for(LoggerDecorator log: logs)
		{
			if(auction.isClosedOffHours())
			{
				log.process(auction, "offHoursLog.log");

			} else {
				log.process(auction, "auctionLog.log");
			}
		}
		sendNotifications(auction);
	}

	public void placeBid(Double bidAmount, Auction auction, User bidder) {
		if(auction.getAuctionState() == (Auction.AuctionState.OPEN))
		{
			if (!bidder.equals(auction.getSeller()))
			{
				if(bidAmount > auction.getPrice() )
				{
					auction.setPrice(bidAmount);
					auction.setHighBidder(bidder);
				}
			}
		}
	}

	public void sendNotifications(Auction auction) {
		AuctionNotifier notifier = AuctionNotifier.getInstance(auction);
		notifier.sendMessage(auction);
	}

	public void setAuctionCategory(Auction auction, AuctionCategory auctionCategory) {
		auction.setAuctionCategory(auctionCategory);
	}
}