package br.usp.ime.dojo.script_engine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

class evalThread extends Thread {
	public String result;
	public boolean finished = false;
	public String language;
	public String code;
	
	evalThread(String language, String code) {
		this.language = language;
		this.code = code;
		result = "";
	}
	
	@Override
	public void run() {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine scriptEngine = mgr.getEngineByName((String) language);
		StringWriter output = new StringWriter();
		if (scriptEngine == null) {
			result = language + " not found";
		} else {
			scriptEngine.getContext().setWriter(output);
			try {
				scriptEngine.eval(code);
				result = output.toString();
				if (!result.contains("Finished in")){
					result = "Estouro de pilha";
				}
			} catch (ScriptException e) {
				result = e.getCause().toString();
			}
		}
		finished = true;
	}
}

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
		evalThread eval = new evalThread(languagesTable.get(language).toString(), code);
		eval.start();
		try {
			eval.join(10000);
			if (eval.isAlive()) {
				eval.interrupt();
				Thread.sleep(1000);
				return "Timeout exceeded\n";
			}
		} catch (InterruptedException e) {
			
		}
		return eval.result;
		  
	}
}