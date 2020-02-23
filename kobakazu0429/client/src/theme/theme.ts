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
    warning: string;
  };
  utils: {
    unit: (amount: number) => number;
    unitPx: (amount: number) => string;
  };
}

const UNIT = 8; // px
const unit = (amount: number) => UNIT * amount;

const theme: Theme = {
  color: {
    brand: "#0F4C81",
    text: {
      primary: "#212121",
      secondary: "#fefefe"
    },
    accent: "#7fffd4",
    divider: "#e6ecf0",
    warning: "#e31c3d"
  },
  utils: {
    unit,
    unitPx: (amount: number) => `${unit(amount)}px`
  }
};

export const themes: Themes = {
  default: theme
};
