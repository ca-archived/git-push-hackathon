<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
こんにちは！！！！
@if(Auth::check())
    {{ \Auth::user()->name }}さん<br />
    <a href="/auth/logout">ログアウト</a>
@else
    ゲストさん<br/>
    <a href="/auth/login">ログイン</a>
    <a href="/auth/login/github/" class="btn btn-github">Sign in using Github</a>
    <a href="/auth/register">会員登録</a>
@endif
</body>
</html>