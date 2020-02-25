import { config } from "dotenv";

config();

type T_NODE_ENV = "production" | "development";

export const NODE_ENV = (process.env.NODE_ENV || "production") as T_NODE_ENV;
export const PORT = process.env.PORT || 3000;
