package br.usp.ime.dojo.web_service;

// This class is used to model the output data
// sent to the user.
// An output entry is consisted of one field:
// - the result text received from the interpreter
public class Output {
	private String result;
	
	// empty constructor
	public Output(String result) {
		this.result = result;
	}
	
	// result - accessor
	public String getResult() {
		return result;
	}
}
