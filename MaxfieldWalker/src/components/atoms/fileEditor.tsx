import * as React from "react";
import MonacoEditor from "react-monaco-editor";
import * as monaco from "monaco-editor";
import { CustomStyle, FillStyle } from "../../theme/styles";

const options: monaco.editor.IEditorConstructionOptions = {
  minimap: {
    enabled: true,
    renderCharacters: false
  },
  wordBasedSuggestions: false,
  automaticLayout: true,
  scrollBeyondLastLine: false
};

interface Props extends CustomStyle {
  content?: string;
  language: string;
  onContentChange?: (
    value: string,
    event: monaco.editor.IModelContentChangedEvent
  ) => void;
}

export class FileEditor extends React.Component<Props> {
  shouldComponentUpdate(props: Props) {
    return this.props.content !== props.content;
  }

  onChange(value: string, event: monaco.editor.IModelContentChangedEvent) {
    const { onContentChange } = this.props;
    if (onContentChange) {
      onContentChange(value, event);
    }
  }

  render() {
    const { content, language } = this.props;

    const lang = convertLanguage(language);
    console.log(lang);

    return (
      <div style={{ ...FillStyle, ...this.props.style }}>
        <MonacoEditor
          language={lang} // TODO: 言語に応じて変更する
          value={content}
          options={options}
          onChange={this.onChange.bind(this)}
        />
      </div>
    );
  }
}

function convertLanguage(language: string): string {
  switch (language) {
    case "C":
      return "cpp";
    case "TypeScript":
      return "typescript";
    case "JavaScript":
      return "javascript";
    case "HTML":
      return "html";
    case "CSS":
      return "css";
    default:
      return language;
  }
}
