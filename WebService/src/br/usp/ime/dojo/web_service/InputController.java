package br.usp.ime.dojo.web_service;

import static br.com.caelum.vraptor.view.Results.json;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.dojo.script_engine.CodeInterpreter;

@Resource
public class InputController {

	private final Result result;
	
	// constructor
	public InputController(Result result) {
		this.result = result;
	}
	
	// calls the interpreter
	@Post
	public void callInterpreter(Input input) {
		try{
			// calls the interpreter
			CodeInterpreter interpreter = new CodeInterpreter();
			if(!interpreter.getAllowedLanguages().contains(input.getLanguage()))
				throw new Exception(input.getLanguage() + ": language not available.");
			String code = input.getSourceCode() + "\n" + input.getTestCode();
			String r = interpreter.interpret(input.getLanguage(), code);
			
			// creates the output and redirects to the output controller 
			Output out = new Output(r);
			result.include("output", out);
			result.use(json()).from(out).serialize();
		}
		catch(Exception e){
			Output out = new Output(e.getMessage());
			result.include("output", out);
			result.use(json()).from(out).serialize();
		}
	}
}
