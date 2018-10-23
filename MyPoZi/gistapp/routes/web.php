<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});
Route::get('/home', function(){
    return view('home');
});

// login, logout
Route::get('auth/logout', 'Auth\LoginController@logout');

// github
Route::get('auth/login/github', 'Auth\SocialController@getGithubAuth');
Route::get('auth/login/github/callback', 'Auth\SocialController@getGithubAuthCallback');

// gists
Route::get('gists/public', 'GistsController@showPublicList')->middleware('auth');
Route::get('gists/my-gists', 'GistsController@showMyGistsList');
Route::get('gists/send', function (){
    return view('gists/gists_post');
});
Route::post('gists', 'GistsController@postGists')->middleware('auth');