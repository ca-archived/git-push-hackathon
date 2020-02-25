# Dancing Player

## How to dev

### Setup

```bash
# ... Setup node, yarn and clone this PR.
$ cd kobakazu0429/client
$ cp.env.sample .env
# 環境に合わせて .env を書き換えてください.
# 環境変数は GOOGLE_OAUTH_CLIENT_ID のみ書き換えれば大丈夫だと思います.

$ yarn install

# http://localhost:5000 を開くと確認できるはずです.
$ yarn dev
```

### Commands

```bash
# written in package.json
$ yarn dev   # Start webpack-dev-server
$ yarn build # Build to dist/
$ yarn lint  # Check type and syntax
```
