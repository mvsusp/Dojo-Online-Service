<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Interpreter Webservice</title>
</head>
<body>
Get outta here!!!

<form method="post" action="input/callInterpreter">
 linguagem: <input type="text" name="input.language" /><br />
 source code: <textarea name="input.sourceCode"></textarea><br />
 <input type="submit" value="envia" />
</form>
</body>
</html>