<?php

namespace App\Http\Controllers;

use GuzzleHttp\Client;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class GistsController extends Controller
{

    public function showPublicList()
    {
        $show_public_list = $this->getGistsDate('https://api.github.com/gists/public');
        return $show_public_list;
    }

    public function postGists(Request $request)
    {
        $this->postGistsData('https://api.github.com/gists', $request);
        $show_my_gists_list = $this->showMyGistsList();
        return $show_my_gists_list;
    }

    public function showMyGistsList()
    {
        $show_my_gists_list = $this->getGistsDate('https://api.github.com/gists');
        return $show_my_gists_list;
    }


    public function postGistsData(string $url, Request $request)
    {
        $all = $request->all();
        $access_token = $this->getAccessToken();

        if (!$all['description']) {
            $all['description'] = "";
        }
//        $files = array();
//        if (count($all['file_name']) != 1) {
//            foreach (array_map($all['file_name'], $all['text_area']) as [$file_name, $text_area]) {
//                array_merge($files, array($file_name => array(
//                        "content" => $text_area,
//                    ))
//                );
//            }
//        }
//        var_dump($files);

        $array = array(
            "description" => $all['description'],
            "public" => boolval($all['public']),
            "files" => array(
                $all['file_name'] => array(
                    "content" => $all['text_area'],
                ),
            ),
        );
        $array = json_encode($array);
        $client = new Client();
        $res = $client->request('POST', $url,
            [
                'query' => ['access_token' => $access_token],
                'body' => $array
            ]);
        return $res;
    }

    public function getGistsDate(string $url)
    {
        $access_token = $this->getAccessToken();
        $github_ID = $this->getGithubClientID();
        $client = new Client();
        $res = $client->request('GET', $url, [
            'query' => ['client_id' => $github_ID['github']['client_id'],
                'client_secret' => $github_ID['github']['client_secret'],
                'access_token' => $access_token
            ]
        ]);

        $data = json_decode($res->getBody(), true);
        $name = array();
        $html_url = array();
        $files = array();
        $img = array();
        $owner_html_url = array();
        $updated_at = array();
        $description = array();
        foreach ($data as $record) {
            array_push($name, $record['owner']['login']);
            array_push($html_url, $record['html_url']);
            array_push($files, $record['files']);
            array_push($img, $record['owner']['avatar_url']);
            array_push($owner_html_url, $record['owner']['html_url']);
            array_push($updated_at, date('Y/m/d H:i:s', strtotime($record['updated_at']) + 9 * 60 * 60));
            array_push($description, $record['description']);
        }
        return view('gists/gists', compact('name', 'html_url', 'files',
            'img', 'updated_at', 'description'));
    }

    protected function getAccessToken()
    {
        $user_id = Auth::user()->id;
        $access_token = DB::table('social_accounts')->select('provider_access_token')->where('user_id', '=', $user_id)->get();
        return $access_token[0]->provider_access_token;
    }

    protected function getGithubClientID()
    {
        return ['github' => [
            'client_id' => env('GITHUB_CLIENT_ID'),
            'client_secret' => env('GITHUB_CLIENT_SECRET'),
        ]];
    }

}
