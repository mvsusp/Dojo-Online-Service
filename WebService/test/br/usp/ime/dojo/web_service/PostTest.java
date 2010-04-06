package br.usp.ime.dojo.web_service;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

public class PostTest {
	
	@Test
	public void aRubyCodeShouldReturnSeven(){
		String result = submit("ruby", "puts 2 + 5");
		assertEquals(buildJSON("7\\u000a"), result);
	}


	@Test
	public void aRubyScriptWithFunctionCallShouldSayWorks() {
		String result = submit("ruby", readScript("ruby/SimpleScriptWithFunctionCall.rb"));
		assertEquals(buildJSON("works!\\u000a"), result);
	}

	@Test
	public void aRubyFunctionCallWithParametersShouldReturn3() {
		String result = submit("ruby", readScript("ruby/FunctionCallWithParameters.rb"));
		assertEquals(buildJSON("3\\u000a"), result);
	}

	@Test
	public void aRubyCallMethodFromClassShouldReturnSuccess() { 
		String result = submit("ruby", readScript("ruby/SimpleClassWithMethod.rb"));
		assertEquals(buildJSON("success\\u000a"), result);
	}

	@Test
	public void aRubyFunctionCallWithoutConsoleOutputResultShouldBeEmpty() {
		String result = submit("ruby", readScript("ruby/FunctionCallWithoutReturn.rb"));
		assertEquals(buildJSON(""), result);
	}

	@Test
	public void aRubyEmptyScriptResultShouldbeEmpty() {
		String result = submit("ruby","");
		assertEquals(buildJSON(""), result);
	}

	@Test
	public void aRubyScriptWithInvalidCodeShouldReturnErrorMessage() {
		String result = submit("ruby", "bla");
		assertEquals(buildJSON("org.jruby.exceptions.RaiseException: undefined local variable or method `bla' for main:Object"), result);
	}

	@Test
	public void aRubyScriptWithSlowCodeShouldTimeout() {
		String result = submit("ruby", readScript("ruby/SlowScript.rb"));
		assertEquals(buildJSON("Timeout exceeded\\u000a"), result);
	}
	
	@Test
	public void assemblyShouldNotBeAvailable(){
		String result = submit("assembly", "MOV EAX, [EBX]");
		assertEquals(buildJSON("assembly: language not available."), result);
	}
	
	
	
	// private helpers
	
	private String path_to_test_scripts = "test/br/usp/ime/dojo/script_engine/";
	
	private String buildJSON(String result){
		return "{\"output\": {\n  \"result\": \"" + result + "\"\n}}\n";
	}
	
	private String readScript(String filePath) {
		FileInputStream fileInput;
		try {
			fileInput = new FileInputStream(path_to_test_scripts + filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
		String code = "";
		Scanner scanner = new Scanner(fileInput);
		while (scanner.hasNextLine()) {
			code += scanner.nextLine() + "\n";
		}
		scanner.close();
		return code;
	}
	
	private String headFilter(String result){
		int pos = result.indexOf("\n\n");
		return result.substring(pos+2);
	}
	
	private String submit(String language, String sourceCode){
		HashMap<String,String> parameters = new HashMap<String,String>();
		parameters.put("input.sourceCode", sourceCode);
		parameters.put("input.language", language);		
		String result = PostRequest.send("localhost", 8080, "/WebService/input/callInterpreter", parameters);
		return headFilter(result);
	}
	
}
