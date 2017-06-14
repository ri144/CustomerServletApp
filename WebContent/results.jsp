<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Results</title>
</head>
<body>
<p>${message}</p>
<form action="SearchSublet" method = "post">
<input type="text" name="fullName" id = "fName" value="enter full name to edit"/>
<input type = "hidden" name = "act" value="fSearch">
<input type = "submit" value = "enter">
</form>

<form action = "SearchSublet" method = "post">
<input type = "text" style= "width:250px;" name="lName" id = "lName" value="search for another last name">
<input type = "submit" value = "enter">
<input type = "hidden" name = "act" value="lSearch">
</form>

</html>