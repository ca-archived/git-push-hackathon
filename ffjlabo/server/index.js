const passport = require('passport');
const YoutubeV3Strategy = require('passport-youtube-v3').Strategy;
require('dotenv').config();
const app = require('express')();

apiEndpoint = process.env['API_ENDPOINT'];
viewEndpoint = process.env['VIEW_ENDPOINT'];

passport.use(
  new YoutubeV3Strategy(
    {
      clientID: process.env['CLIENT_ID'],
      clientSecret: process.env['CLIENT_SECRET'],
      callbackURL: `${apiEndpoint}/auth/callback`,
      scope: ['https://www.googleapis.com/auth/youtube'],
    },
    (accessToken, refreshToken, params, profile, done) => {
      const {expires_in} = params;
      return done(null, {accessToken, expires_in});
    }
  )
);

passport.serializeUser(function(user, done) {
  done(null, user);
});
passport.deserializeUser(function(obj, done) {
  done(null, obj);
});

app.use(passport.initialize());

app.get('/auth', (req, res, next) => {
  passport.authenticate('youtube')(req, res, next);
});

app.get(
  '/auth/callback',
  passport.authenticate('youtube', {
    failureRedirect: `${viewEndpoint}`,
  }),
  (req, res) => {
    const {accessToken, expires_in} = req.user;
    res.cookie('access_token', accessToken, {
      expires: new Date(Date.now() + expires_in * 1000),
      maxAge: expires_in * 1000,
      httpOnly: false,
    });
    res.redirect(viewEndpoint);
  }
);

app.listen(8080, () => console.log('listen on 8080'));
