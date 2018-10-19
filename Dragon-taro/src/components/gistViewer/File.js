import React from "react";
import marked from "marked";

marked.setOptions({
  sanitize: true
});

const MarkdownViewer = ({ content }) => {
  const html = marked(content);
  return (
    <div className="markdown" dangerouslySetInnerHTML={{ __html: html }} />
  );
};

const CodeViewer = ({ content }) => {
  return (
    <pre>
      <code>{content}</code>
    </pre>
  );
};

const renderContent = ({ language, content }) => {
  return language == "Markdown" ? (
    <MarkdownViewer content={content} />
  ) : (
    <CodeViewer content={content} />
  );
};

const File = ({ file }) => {
  return (
    <li className="m-file">
      <div className="filename-zone">
        <span>{file.filename}</span>
      </div>

      <div className="content-zone">{renderContent(file)}</div>
    </li>
  );
};

export default File;
