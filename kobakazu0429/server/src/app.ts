import { Express } from "express";
import bodyParser from "body-parser";
import errorhandler from "strong-error-handler";
import { createExpressServer } from "routing-controllers";

import { NODE_ENV } from "./env";

export const app = createExpressServer({
  controllers: [__dirname + "/controller/**/*.ts"]
}) as Express;

// middleware for parsing application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }));

// middleware for json body parsing
app.use(bodyParser.json({ limit: "5mb" }));

// enable corse for all origins
app.use((_req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Expose-Headers", "x-total-count");
  res.header("Access-Control-Allow-Methods", "GET, PATCH, POST, DELETE");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept"
  );

  next();
});

app.options("*", (_req, res) => {
  res.sendStatus(200);
});

app.use(
  errorhandler({
    debug: NODE_ENV === "development",
    log: true
  })
);
