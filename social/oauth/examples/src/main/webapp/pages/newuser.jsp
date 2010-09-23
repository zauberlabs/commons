<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Twitter OAuth Login Example</title>
</head>
<body>
<h1>Welcome!</h1>
<p>You don't have a user here yet, please enter a username:</p>
<form action="/api/welcome/" method="post">
	<p>
	   <input type="text" name="username" value="${twitterUsername}" />
	</p>
	<p>
	   <input type="submit" value="Join" />
	</p>
</form>
</body>
</html>
