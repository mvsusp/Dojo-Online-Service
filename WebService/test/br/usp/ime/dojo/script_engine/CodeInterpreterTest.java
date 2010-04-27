package br.usp.ime.dojo.script_engine;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

public class CodeInterpreterTest {

	private CodeInterpreter interpreter;
	private String path_to_ruby_scripts = "test/br/usp/ime/dojo/script_engine/ruby/";

	@Before
	public void setUp() {
		interpreter = new CodeInterpreter();
	}

	private String interpretFile(String filename) {
		FileInputStream fileInput;
		try {
			fileInput = new FileInputStream(path_to_ruby_scripts + filename);
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
		return interpreter.interpret("ruby",code);
	}

	@Test
	public void aSimpleRubyCodeShouldWork() {
		String code = "puts 1 + 1";
		String result = interpreter.interpret("ruby",code);
		assertEquals("2\n", result);
	}

	@Test
	public void aRubyScriptWithFunctionCallShouldSayWorks() {
		String result = interpretFile("SimpleScriptWithFunctionCall.rb");
		assertEquals("works!\n", result);
	}

	@Test
	public void aRubyFunctionCallWithParametersShouldReturn3() {
		String result = interpretFile( "FunctionCallWithParameters.rb");
		assertEquals("3\n", result);
	}

	@Test
	public void aRubyCallMethodFromClassShouldReturnSuccess() { 
		String result = interpretFile("SimpleClassWithMethod.rb");
		assertEquals("success\n", result);
	}

	@Test
	public void aRubyFunctionCallWithoutConsoleOutputResultShouldBeEmpty() {
		String result = interpretFile("FunctionCallWithoutReturn.rb");
		assertEquals("", result);
	}

	@Test
	public void aRubyEmptyScriptResultShouldbeEmpty() {
		String result = interpreter.interpret("ruby","");
		assertEquals("", result);
	}

	@Test
	public void aRubyScriptWithInvalidCodeShouldReturnErrorMessage() {
		String result;
		result = interpreter.interpret("ruby","bla");
		assertEquals("org.jruby.exceptions.RaiseException: undefined local variable or method `bla' for main:Object", result);
	}
	
	@Test
	public void listAllowedLanguagesShouldReturnRuby() {
		ArrayList<String> list = interpreter.getAllowedLanguages();
		assertEquals("The list should have more than one element", true, list.size()>=1);
		
		assertEquals("The list should contain 'ruby'", true, list.contains("ruby"));
	}

	@Test
	public void aRubyScriptWithSlowCodeShouldTimeout() {
		String result;
		result = interpretFile("SlowScript.rb");
		assertEquals("Timeout exceeded\n", result);
	}
	
	
	@Test
	public void aRubyScriptWithUnitTest() {
		String result;
		result = interpretFile("ScriptWithUnitTest.rb");
		assertEquals(true, result.contains("2 tests, 3 assertions, 1 failures"));
	}
	
}