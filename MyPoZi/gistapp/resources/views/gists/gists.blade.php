@extends('common.base')
@section('title')
    gists
@endsection
@section('body')
    <div class="border alert alert-secondary">

        @for($i = 0; $i < count($name); $i++)
            <div class="card border-secondary mb-3 ">

                <div class="card-header"><a href="https://github.com/{{$name[$i]}}"><img class="rounded-circle"
                                                                                style="width: 70px; height: 70px"
                                                                                src="{{$img[$i]}}" alt="avatar">
                        {{$name[$i]}}</a>
                    <div class="float-right">{{$updated_at[$i]}}</div>
                </div>
                <a href="{{$html_url[$i]}}" style="text-decoration: none" class="hover">
                    <div class="card-body text-primary">
                        <h5 class="card-title"><p>{{key($files[$i])}}</p></h5>
                        <p class="card-text">Gistの説明：{{$description[$i]}}</p>
                    </div>
                </a>
            </div>
        @endfor

    </div>
@endsection
