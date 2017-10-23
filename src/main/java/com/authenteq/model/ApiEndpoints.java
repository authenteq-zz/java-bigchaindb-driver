package com.authenteq.model;

//
//{
//	  "assets": "/assets/", 
//	  "docs": "https://docs.bigchaindb.com/projects/server/en/v1.1.0/http-client-server-api.html", 
//	  "outputs": "/outputs/", 
//	  "statuses": "/statuses/", 
//	  "streams": "wss://test.ipdb.io:443/api/v1/streams/valid_transactions", 
//	  "transactions": "/transactions/"
//	}

public class ApiEndpoints {

	private String assets;
	private String docs;
	private String outputs;
	private String statuses;
	private String streams;
	private String transactions;
	public String getAssets() {
		return assets;
	}
	public void setAssets(String assets) {
		this.assets = assets;
	}
	public String getDocs() {
		return docs;
	}
	public void setDocs(String docs) {
		this.docs = docs;
	}
	public String getOutputs() {
		return outputs;
	}
	public void setOutputs(String outputs) {
		this.outputs = outputs;
	}
	public String getStatuses() {
		return statuses;
	}
	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}
	public String getStreams() {
		return streams;
	}
	public void setStreams(String streams) {
		this.streams = streams;
	}
	public String getTransactions() {
		return transactions;
	}
	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}
	
	
}
