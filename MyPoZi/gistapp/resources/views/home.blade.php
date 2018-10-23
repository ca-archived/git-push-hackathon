@extends('common.base')

@section('title')
    ホーム
@endsection

@section('body')
    <h1>ホーム</h1>

    @if(Auth::check())
    @else
        <a href="/auth/login/github/">
            <button type="button" class="btn"><i class="fab fa-github-square"></i>Sign in using Github</button>
        </a>
    @endif
@endsection