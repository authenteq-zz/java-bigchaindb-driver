package com.authenteq.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class Transactions.
 */
public class Transactions implements Serializable {
	
	/** The transactions. */
	private List<Transaction> transactions = new ArrayList<Transaction>();

	/**
	 * Gets the transactions.
	 *
	 * @return the transactions
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Sets the transactions.
	 *
	 * @param transactions the new transactions
	 */
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	/**
	 * Adds the transaction.
	 *
	 * @param transaction the transaction
	 */
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
	
	
}
