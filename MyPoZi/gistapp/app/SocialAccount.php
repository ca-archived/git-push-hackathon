<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class SocialAccount extends Model
{
    protected $table = 'social_accounts';

    protected $primaryKey = 'id';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'user_id',
        'provider_user_id',
        'provider_access_token',
        'provider',
    ];

    public function user()
    {
        return $this->belongsTo(User::class);
    }
}
