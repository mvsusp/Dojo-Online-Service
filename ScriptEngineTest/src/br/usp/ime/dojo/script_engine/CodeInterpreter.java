package br.usp.ime.dojo.script_engine;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CodeInterpreter {

	public String interpretFile(String filename) {
		try {
			FileInputStream file = new FileInputStream(filename);
			String code = "";
			DataInputStream data = new DataInputStream(file);
			while (data.available() != 0) {
				String readLine = data.readLine() + "\n";
				code += readLine;
			}
			return interpret(code);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "File not Found";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public String interpret(String code) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine rbEngine = mgr.getEngineByName("jruby");
		if (rbEngine == null)
			System.out.println("jruby not found");

		Object parsedCode = null;
		try {
			parsedCode = rbEngine.eval(code);
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
		if (parsedCode != null) {
			return "" + parsedCode.toString();
		}
		return "";
	}

	public static void main(String[] args) throws NoSuchMethodException {
		CodeInterpreter interpreter = new CodeInterpreter();
		System.out.println(interpreter.interpret(""));
	}
}