<html>
    <head>
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <div class="block">
            <h2>Message</h2>
            <%= request.getAttribute("msg") %>
            <br><br>
            <a href="/app"><< Go Home</a>
        </div>
    </body>
</html>