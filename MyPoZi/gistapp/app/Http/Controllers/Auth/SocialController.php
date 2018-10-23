<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Laravel\Socialite\Facades\Socialite;

use App\User;
use App\SocialAccount;

class SocialController extends Controller
{

    protected $redirectTo = '/gists/public';

    public function getGithubAuth()
    {
        return Socialite::driver('github')->with(['scope' => 'gist'])->redirect();
    }

    public function getGithubAuthCallback()
    {
        $githubUser = Socialite::driver('github')->user();

        $user = $this->createOrGetUser($githubUser);
        Auth::login($user, true);

        return redirect($this->redirectTo);
    }


    public function createOrGetUser($providerUser)
    {
        $account = SocialAccount::firstOrCreate([
            'provider_user_id' => $providerUser->getId(),
        ]);

        if (empty($account->user)) {
            $user = User::create([
                'name' => $providerUser->getNickname(),
            ]);
            $account->user()->associate($user);
        }

        $account->provider_access_token = $providerUser->token;
        $account->save();

        return $account->user;
    }
}