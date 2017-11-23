package com.authenteq.model;

import java.util.ArrayList;
import java.util.List;



/**
 * The Class Outputs.
 */
public class Outputs {
	
	/** The output. */
	private List<Output> output = new ArrayList<Output>();

	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public List<Output> getOutput() {
		return output;
	}

	/**
	 * Sets the output.
	 *
	 * @param output the new output
	 */
	public void setOutput(List<Output> output) {
		this.output = output;
	}
	
	/**
	 * Adds the output.
	 *
	 * @param output the output
	 */
	public void addOutput(Output output) {
		this.output.add(output);
	}
	
	
}
