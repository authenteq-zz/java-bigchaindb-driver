package com.authenteq.model;

import java.util.ArrayList;
import java.util.List;



/**
 * The Class Votes.
 */
public class Votes {

	/** The votes. */
	private List<Vote> votes = new ArrayList<Vote>();

	/**
	 * Gets the votes.
	 *
	 * @return the votes
	 */
	public List<Vote> getVotes() {
		return votes;
	}

	/**
	 * Sets the votes.
	 *
	 * @param votes the new votes
	 */
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	/**
	 * Adds the vote.
	 *
	 * @param vote the vote
	 */
	public void addVote(Vote vote) {
		this.votes.add(vote);
	}
}
