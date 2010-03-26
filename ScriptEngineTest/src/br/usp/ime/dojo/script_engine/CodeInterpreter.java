package br.usp.ime.dojo.script_engine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;



public class CodeInterpreter {

	private StringWriter consoleOutput;
	private Hashtable languagesTable;

	public CodeInterpreter() {
		super();
		consoleOutput = new StringWriter();
		languagesTable = new Hashtable();
		languagesTable.put("ruby", "jruby");
	}
	
	public StringWriter getConsoleOutput() {
		return consoleOutput;
	}
	
	public ArrayList<String> getAllowedLanguages() {
		ArrayList<String> list = new ArrayList<String>();
		Enumeration<String> e = languagesTable.keys();
		while (e.hasMoreElements()) {
			list.add(e.nextElement().toString());
		}
		return list;
	}

	public String interpret(String language, String code) {
		if (languagesTable.get(language) == null){
			return "Language not supported.";
		}
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine scriptEngine = mgr.getEngineByName((String) languagesTable.get(language));
		StringWriter output = new StringWriter();
		if (scriptEngine == null) {
			return language + " not found";
		}
		scriptEngine.getContext().setWriter(output);
		try {
			scriptEngine.eval(code);
			String result = output.toString();
			consoleOutput.append(result);
			return "" + result;
		} catch (ScriptException e) {
			return e.getCause().toString();
		}  
	}
}