<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form method="post" action="SearchSublet">

<input type="text" name="Title" value="${title}"/>
<input type="text" name="FirstName" value="${firstName}"/>
<input type="text" name="LastName" value="${lastName}"/><br>
<input type="text" name="StreetAddress" value="${street}"/>
<input type="text" name="City" value="${city}"/>
<input type="text" name="State" value="${state}"/>
<input type="text" name="ZipCode" value="${zip}"/><br>
<input type="text" name="EmailAddress" value="${email}"/><br>
<input type="text" name="Position" value="${position}"/>
<input type="text" name="Company" value="${company}"/>
<input type="hidden" name="act" value="edit">
<input type="submit">
</form>


<!-- match names
"FirstName", "Title", "Company", "Position", "StreetAddress", 
			"ZipCode", "State", "City", "EmailAddress" 
 -->

</body>
</html>