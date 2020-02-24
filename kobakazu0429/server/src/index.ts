import "reflect-metadata";
import { createServer } from "http";
import { createConnection } from "typeorm";

import { PORT } from "./env";
import { app } from "./app";

(async () => {
  await createConnection();

  createServer(app).listen(PORT, async () => {
    console.info(`You can see here: http://localhost:${PORT}`);
  });
})();
