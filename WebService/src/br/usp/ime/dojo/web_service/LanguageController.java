package br.usp.ime.dojo.web_service;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.ArrayList;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.dojo.script_engine.CodeInterpreter;

@Resource
public class LanguageController {

	private final Result result;
	private CodeInterpreter interpreter;
	
	// constructor
	public LanguageController(Result result) {
		this.result = result;
		this.interpreter = new CodeInterpreter();
	}
	
	// gets the list of available languages.
	public void listLanguages() {
		try{
			ArrayList<String> list = interpreter.getAllowedLanguages();
			result.use(json()).from(list).serialize();
		}
		catch(Exception e){
			String error = e.getMessage();
			result.use(json()).from(error).serialize();
		}
	}
}

