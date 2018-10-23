export enum Color {
  black = "#000",
  gray = "gray",
  lightgray = "#eee",
  darkgray = "#222",
  silver = "#ccc",
  white = "#fff",
  orange = "#ffa500",
  red = "#ce372f"
}

export namespace ColorResources {
  export const tree_view_sub_foreground = Color.gray;
  export const tree_view_high_background = Color.lightgray;
  export const foreground = Color.black;
  export const foreground_lv1 = "#333";
  export const foreground_lv2 = "#666";
  export const danger = Color.red;
  export const sub_foreground = Color.gray;
  export const editable_text_hover_background = Color.lightgray;
  export const public_label = "#3082E3";
  export const private_label = "#E33091";

  export const app_bar_background = Color.darkgray;
}

export namespace TransitionResources {
  export const tree_view_background_color = "all 0.1s ease";
  export const editable_text = "all 0.3s ease";
}

export namespace NumberResources {
  export const radius_mid = 6;
}

export namespace FontSize {
  export const tiny = 10;
  export const small = 12;
  export const button = 14;
  export const mid = 16;
  export const large = 28;
}
