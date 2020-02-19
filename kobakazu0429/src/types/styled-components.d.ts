import { Theme } from "@/theme/theme";

declare module "styled-components" {
  interface DefaultTheme extends Theme {}
}
