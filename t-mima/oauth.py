# coding: utf-8
from flask import Flask, request, Response
from flask_cors import CORS
import requests

app = Flask(__name__)
CORS(app)

client_id = "7b721a39564ef42cf585"
client_secret = ""

@app.route('/login')
def login():
    code = request.args.get('code')
     
    payload = {'code': code, 'client_id': client_id, 'client_secret': client_secret}
    r = requests.post('https://github.com/login/oauth/access_token', params=payload)

    res = r.text.split('&')
    response = [ x.split('=') for x in res ]
    print(response)

    return Response(response[0][1], mimetype='text/plain')

@app.route('/revoke')
def revoke():
    access_token = request.args.get('access_token')

    url='https://api.github.com/applications/'+client_id+'/tokens/'+access_token
    r = requests.delete(url, auth=(client_id,client_secret))

    return str(r.status_code)

if __name__ == '__main__' :
    app.run()