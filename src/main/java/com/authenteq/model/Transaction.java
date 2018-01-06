package com.authenteq.model;

import com.authenteq.annotations.Exclude;
import com.authenteq.json.strategy.TransactionIdExclusionStrategy;
import com.authenteq.util.JsonUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Transaction.
 */
public class Transaction implements Serializable {

	/** The asset. */
	@SerializedName("asset")
	private Asset asset = new Asset();

	/** The id. */
	@SerializedName("id")
	private String id;

	/** The inputs. */
	@SerializedName("inputs")
	private List<Input> inputs = new ArrayList<>();

	/** The meta data. */
	@SerializedName("metadata")
	private Object metaData = null;

	/** The operation. */
	@SerializedName("operation")
	private String operation;

	/** The outputs. */
	@SerializedName("outputs")
	private List<Output> outputs = new ArrayList<>();

	/** The version. */
	@SerializedName("version")
	private String version;

	/** The signed. */
	@Exclude
	private Boolean signed;

	/**
	 * Gets the signed.
	 *
	 * @return the signed
	 */
	public Boolean getSigned() {
		return signed;
	}

	/**
	 * Sets the signed.
	 *
	 * @param signed the new signed
	 */
	public void setSigned(Boolean signed) {
		this.signed = signed;
	}

	/**
	 * Gets the asset.
	 *
	 * @return the asset
	 */
	public Asset getAsset() {
		return asset;
	}

	/**
	 * Sets the asset.
	 *
	 * @param asset the new asset
	 */
	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the inputs.
	 *
	 * @return the inputs
	 */
	public List<Input> getInputs() {
		return inputs;
	}

	/**
	 * Sets the inputs.
	 *
	 * @param inputs the new inputs
	 */
	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	/**
	 * Gets the meta data.
	 *
	 * @return the meta data
	 */
	public Object getMetaData() {
		return metaData;
	}

	/**
	 * Set the metaData object
	 *
	 * @param obj the metadata object
	 */
	public void setMetaData( Object obj )
	{
		this.metaData = obj;
	}

	/**
	 * Gets the operation.
	 *
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Sets the operation.
	 *
	 * @param operation the new operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Gets the outputs.
	 *
	 * @return the outputs
	 */
	public List<Output> getOutputs() {
		return outputs;
	}

	/**
	 * Sets the outputs.
	 *
	 * @param outputs the new outputs
	 */
	public void setOutputs(List<Output> outputs) {
		this.outputs = outputs;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Adds the input.
	 *
	 * @param input the input
	 */
	public void addInput(Input input) {
		this.inputs.add(input);
	}

	/**
	 * Adds the output.
	 *
	 * @param output the output
	 */
	public void addOutput(Output output) {
		this.outputs.add(output);
	}

	 /* (non-Javadoc)
 	 * @see java.lang.Object#toString()
 	 */
 	@Override
	 public String toString() {
		 return JsonUtils.toJson(this);
	 }

	/**
	 * Return the transaction suitable for hashing.
	 *
	 * @return the transaction as a json string
	 */
	public String toHashInput()
	 {
	 	return JsonUtils.toJson( this, new TransactionIdExclusionStrategy() );
	 }
}
