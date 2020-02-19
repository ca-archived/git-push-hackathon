export type ThemeName = "default";
export type Themes = { [P in ThemeName]: Theme };

export interface Theme {
  color: {
    brand: string;
    text: {
      primary: string;
      secondary: string;
    };
    accent: string;
    divider: string;
  };
}

const theme: Theme = {
  color: {
    brand: "#0F4C81",
    text: {
      primary: "#212121",
      secondary: "#fefefe"
    },
    accent: "#7fffd4",
    divider: "#BDBDBD"
  }
};

export const themes: Themes = {
  default: theme
};
