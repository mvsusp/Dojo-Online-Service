package br.usp.ime.dojo.web_service;

// This class is used to model the input data
// received from the web.
// An input entry is consisted of two fields:
// - a source code (text)
// - the programming language of the source (also given as a text) 
public class Input {
	private String language;
	private String sourceCode;
	private String testCode;
	
	// empty constructor
	public Input() {
	}
	
	// language - accessor
	public String getLanguage() {
		return language;
	}
	
	// language - mutator
	public void setLanguage(String language) {
		this.language = language;
	}
	
	// source code - accessor
	public String getSourceCode() {
		return sourceCode;
	}
	
	// source code - mutator
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	// test code - accessor
	public String getTestCode() {
		return testCode;
	}
	
	// test code - mutator
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

}
