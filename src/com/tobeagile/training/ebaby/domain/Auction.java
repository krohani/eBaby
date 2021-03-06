package com.tobeagile.training.ebaby.domain;

import java.time.LocalDateTime;

import com.tobeagile.training.ebaby.services.OffHours;

public class Auction 
{
	private String auctionId = null;
	private User seller = null;
	private User highBidder = null;
	private String description = null;
	private Double price = null;
	private LocalDateTime auctionStartDateTime = null;
	private LocalDateTime auctionEndDateTime = null;
	private AuctionState auctionState = AuctionState.NOT_STARTED;
	private AuctionCategory auctionCategory = AuctionCategory.GENERAL;
	private Double sellerIncome = 0.00;
	private Double buyerCost = 0.00;
	private boolean closedOffHours = false;
	
	public AuctionState getAuctionState() {
		return auctionState;
	}

	public void setAuctionState(AuctionState auctionState) {
		this.auctionState = auctionState;
	}

	public Auction(User u, String description, Double price,LocalDateTime auctionStartDateTime, LocalDateTime auctionEndDateTime) {
		setAuctionId(u.getUserName() + auctionStartDateTime);
		seller = u;
		this.description = description;
		this.price = price;
		this.auctionStartDateTime = auctionStartDateTime;
		this.auctionEndDateTime = auctionEndDateTime;
	}

	public User getSeller() {
		return this.seller;
	}

	public String getDescription() {
		return this.description;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDateTime getAuctionStartDateTime() {
		return this.auctionStartDateTime;
	}
	
	public LocalDateTime getAuctionEndDateTime() {
		return this.auctionEndDateTime;
	}

	public String getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}
	
	public enum AuctionCategory
	{
		GENERAL,
		CAR,
		DOWNLOADABLE_SOFTWARE;
	}

	public enum AuctionState
	{
		NOT_STARTED,
		OPEN,
		CLOSED;
	}

	public User getHighBidder() {
		return highBidder;
	}

	public void setHighBidder(User highBidder) {
		this.highBidder = highBidder;
	}

	public AuctionCategory getAuctionCategory() {
		return auctionCategory;
	}

	public void setAuctionCategory(AuctionCategory auctionCategory) {
		this.auctionCategory = auctionCategory;
	}

	public Double getSellerIncome() {
		return sellerIncome;
	}

	public void setSellerIncome(Double fee) {
		this.sellerIncome = fee;
	}

	public Double getBuyerCost() {
		return buyerCost;
	}

	public void setBuyerCost(Double buyerCost) {
		this.buyerCost = buyerCost;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAuctionStartDateTime(LocalDateTime auctionStartDateTime) {
		this.auctionStartDateTime = auctionStartDateTime;
	}

	public void setAuctionEndDateTime(LocalDateTime auctionEndDateTime) {
		this.auctionEndDateTime = auctionEndDateTime;
	}

	public boolean isClosedOffHours() {
		return closedOffHours;
	}

	public void setClosedOffHours(boolean closedOffHours) {
		OffHours offHours = OffHours.getInstance();
		this.closedOffHours = offHours.isOffHours();
	}
}
