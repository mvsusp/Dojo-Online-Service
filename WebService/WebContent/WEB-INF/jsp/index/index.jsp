<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Interpreter Webservice</title>
<script type="text/javascript" src="jquery.js"></script>
<script src="codepress/codepress.js" type="text/javascript"></script>  
<script type="text/javascript">
$(document).ready(function() {
	result.toggleReadOnly()
})
function postCode() {
	$.post("input/callInterpreter", {"input.language" : $("#language").val(), "input.sourceCode" : sourceCode.getCode(), "input.testCode" : testCode.getCode()},
	
			function (data) {
				var txt_result = data.output.result
				result.edit(txt_result)
			}, "json")		
}
</script>
</head>
<body>
<form action="#">
linguagem: <input type="text" id="language" value="ruby" /><br />
source code: <textarea id="sourceCode" class="codepress ruby linenumbers-on" style="width: 100%; height: 200px"></textarea><br />
test code: <textarea id="testCode" class="codepress ruby linenumbers-on" style="width: 100%; height: 100px"></textarea><br />
<input type="button" onclick="postCode()" value="Run"  />
</form>
<hr>
result: <textarea id="result" class="codepress text linenumbers-on" style="width: 100%; height: 100px; overflow:auto;">
</textarea>
</body>
</html>