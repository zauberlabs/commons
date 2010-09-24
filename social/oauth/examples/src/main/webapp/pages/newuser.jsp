<%--

    Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
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
