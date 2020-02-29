import Cookies from 'js-cookie';
const accessToken = Cookies.get('access_token');
const axios = require('axios');

export const client = axios.create({
  baseURL: 'https://www.googleapis.com/youtube/v3',
  timeout: 1000,
  headers: {Authorization: `Bearer ${accessToken}`},
});
