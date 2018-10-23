@extends('common.base')
@section('title')
    gists送信フォーム
@endsection
@section('body')
    <form action="/gists" method="post">
        {{ csrf_field() }}
        <input type="text" name="description" class="form-control form-control" placeholder="Gistの説明">
        <br>
        <input type="text" name="file_name" class="form-control form-control-lg" placeholder="拡張子を含むファイル名">
        <div class="form-group">
            <textarea class="form-control" name="text_area" id="FormControlTextArea1" rows="10" placeholder="内容(必須)"></textarea>
            @if($errors->has('text_area'))<p class="error" style="color: #ac2925">{{ $errors->first('text_area') }}</p> @endif
        </div>
        <div class="form-group">
            <label for="Select1">公開設定</label>
            <select class="form-control" name="public" id="Select">
                <option value="1">公開</option>
                <option value="0">秘密</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary float-right" >送信</button>
    </form>
@endsection