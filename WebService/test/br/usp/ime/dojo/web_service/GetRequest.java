package br.usp.ime.dojo.web_service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GetRequest {

	public static String send(String hostname, int port, String path, Map<String,String> parameters) {
		try {
			// resposta
			String response = "";
			
		    // obtem parametros do map
			String data = getEncodedParameters(parameters);

		    // Cria um socket
		    InetAddress addr = InetAddress.getByName(hostname);
		    Socket socket = new Socket(addr, port);

		    // Envia cabeçalho no formato:
		    // ----------------------------------
		    // GET /path/script.cgi HTTP/1.0
		    // From: frog@jmarshall.com [ <-- omitido ]
		    // User-Agent: HTTPTool/1.0 [ <-- omitido ]
		    // Content-Type: application/x-www-form-urlencoded
		    // Content-Length: 32
		    //
		    // home=Cosby&favorite+flavor=flies
		    // ----------------------------------
		    BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		    wr.write("GET "+path+" HTTP/1.0\r\n");
		    wr.write("Content-Length: "+data.length()+"\r\n");
		    wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
		    wr.write("\r\n");

		    // Escrevendo os parametros...
		    wr.write(data);
		    wr.flush();

		    // Obtendo resposta
		    BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null)
		        response += line + "\n";
		    wr.close();
		    rd.close();
		    
		    // fim!
		    return response;
		}
		catch (Exception e) {
			return "GetRequest::Send(): " + e.getMessage();
		}
	}
	
	
	// transforma um Map<String,String> numa encoded String
	private static String getEncodedParameters(Map<String,String> m) {
		try {
			String data = "";
			Set<Map.Entry<String,String> > s = m.entrySet();
			Iterator<Map.Entry<String,String> > it = s.iterator();
			
			while(it.hasNext()) {
				Map.Entry<String,String> e = (Map.Entry<String,String>)it.next();
				data += URLEncoder.encode(e.getKey(), "UTF-8");
				data += "=";
				data += URLEncoder.encode(e.getValue(), "UTF-8");
				if(it.hasNext())
					data += "&";
			}
		    
		    return data;
		}
		catch(Exception e) {
			return "";
		}
	}

	
}
