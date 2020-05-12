<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <title>Insert title here</title>
</head>
<body>
  ${fn:substring("abcdefg",2 ,5 )}<br>
${fn:substringBefore("abcdefg","d" )}
${fn:substringAfter("abcdefg","cd" )}
</body>
</html>
