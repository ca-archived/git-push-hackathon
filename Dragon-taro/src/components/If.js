export function If(props) {
  if (props.condition) {
    return props.children;
  } else {
    return false;
  }
}
