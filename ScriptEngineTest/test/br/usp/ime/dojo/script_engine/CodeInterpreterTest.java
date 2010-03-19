package br.usp.ime.dojo.script_engine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CodeInterpreterTest {

	private CodeInterpreter interpreter;
	private String path_to_ruby_scripts = "test/br/usp/ime/dojo/script_engine/ruby";

	@Before
	public void setUp() {
		interpreter = new CodeInterpreter();
	}

	@Test
	public void aSimpleRubyCodeShouldWork() {
		String code = "1 + 1";
		String result = interpreter.interpret(code);
		assertEquals("2", result);
	}

	@Test
	public void rubyFunctionCall() {
		String result = interpreter.interpretFile(path_to_ruby_scripts + "/FunctionCall.rb");
		assertEquals("works!", result);
	}

	@Test
	public void rubyFunctionCallWithParameters() {
		String result = interpreter.interpretFile(path_to_ruby_scripts + "/FunctionCallWithParameters.rb");
		assertEquals("3", result);
	}

	@Test
	public void rubySimpleClassWithMethod() {
		String result = interpreter.interpretFile(path_to_ruby_scripts + "/SimpleClassWithMethod.rb");
		assertEquals("success", result);
	}

}
