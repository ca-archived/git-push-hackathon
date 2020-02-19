# react-webpack-boilerplate

[![CircleCI](https://circleci.com/gh/kobakazu0429/react-webpack-boilerplate/tree/master.svg?style=svg)](https://circleci.com/gh/kobakazu0429/react-webpack-boilerplate/tree/master)

My React + Webpack boilerplate 201905

- yarn
- webpack
- typescript
- prettier
- tslint

This code does **not** include framework, ci, and other (production) tools.

## Bootstrap

```bash
# ... Setup node and yarn
$ git clone git@github.com:kobakazu0429/react-webpack-boilerplate.git --depth 1 <your project name>
$ cd <your project name>
$ git fetch origin --unshallow
$ git remote rm origin
$ yarn install
$ yarn dev    # Start webpack-dev-server
$ yarn build  # Build to dist/
$ yarn lint   # Check type and syntax
```

---

# {app_name}

## How to dev

### Setup

```bash
# ... Setup node and yarn
$ git clone <git repository url>
$ cd <your project name>
$ yarn install
```

### Commands

```bash
# written in package.json
$ yarn dev   # Start webpack-dev-server
$ yarn build # Build to dist/
$ yarn lint  # Check type and syntax
```
