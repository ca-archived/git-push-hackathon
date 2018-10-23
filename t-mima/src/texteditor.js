import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import marked, { Renderer } from 'marked';
import highlightjs from 'highlight.js';
import { Btn } from './btn';

const renderer = new Renderer();
renderer.code = (code, language) => {
  if (language && highlightjs.getLanguage(language)) {
    return `<pre><code class="hljs ${language}">${highlightjs.highlight(language, code).value}</code></pre>`;
  }
  return `<pre><code class="hljs ${language}">${code}</code></pre>`;
};
marked.setOptions({ renderer });

export class Texteditor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      html: '<h3 style="text-align:center;border: dotted 2px #aaa;padding: 1em 0;color: #aaa;">Markdown output here</h3>',
    };

    this.delete = this.delete.bind(this);
    this.saveAsNew = this.saveAsNew.bind(this);
    this.saveAsLocal = this.saveAsLocal.bind(this);
    this.saveAsUpdate = this.saveAsUpdate.bind(this);
    this.updateMarkdown = this.updateMarkdown.bind(this);
  }

  updateMarkdown(event) {
    this.setState({ html: marked(event.target.value) });
  }

  saveAsNew() {
    const ACCESS_TOKEN = localStorage.getItem('ACCESS_TOKEN') || null;
    const CONTENT = document.getElementById('editing').value;
    const newname = window.prompt('new filename?', this.props.filename);
    if (newname) {
      fetch(`https://api.github.com/gists/${this.props.gist_id}`, {
        method: 'PATCH',
        headers: {
          Authorization: `token ${ACCESS_TOKEN}`,
          accept: 'application/json',
        },
        body: JSON.stringify({
          files: {
            [newname]: {
              content: CONTENT,
            },
          },
        }),
      }).then((response) => {
        return response.text();
      }).then((text) => {
        alert('done!!');
        ReactDOM.render(<Dashboard />, document.getElementById('area'));
      }).catch((error) => {
        alert(error)
      });
    } else {
      alert('canceled');
    }
  }

  saveAsUpdate() {
    const ACCESS_TOKEN = localStorage.getItem('ACCESS_TOKEN') || null;
    const CONTENT = document.getElementById('editing').value;
    fetch(`https://api.github.com/gists/${this.props.gist_id}`, {
      method: 'PATCH',
      headers: {
        Authorization: `token ${ACCESS_TOKEN}`,
        accept: 'application/json',
      },
      body: JSON.stringify({
        files: {
          [this.props.filename]: {
            content: CONTENT,
          },
        },
      }),
    }).then((response) => {
      return response.text();
    }).then((text) => {
      alert('done!!');
      ReactDOM.render(<Dashboard />, document.getElementById('area'));
    }).catch((error) => {
      alert(error)
    });
  }

  delete() {
    if (window.confirm('delete this file from your gist?')) {
      const ACCESS_TOKEN = localStorage.getItem('ACCESS_TOKEN') || null;
      fetch(`https://api.github.com/gists/${this.props.gist_id}`, {
        method: 'PATCH',
        headers: {
          Authorization: `token ${ACCESS_TOKEN}`,
          accept: 'application/json',
        },
        body: JSON.stringify({
          files: {
            [this.props.filename]: null,
          },
        }),
      }).then((response) => {
        return response.text();
      }).then((text) => {
        alert('done!!');
        ReactDOM.render(<Dashboard />, document.getElementById('area'));
      }).catch((error) => {
        alert(error)
      });
    }
  }

  saveAsLocal() {
    const content = document.getElementById('editing').value;
    const filename = this.props.filename;
    const blob = new Blob([content], { type: 'text/plain' });
    if (window.navigator.msSaveBlob) {
      window.navigator.msSaveBlob(blob, filename);
      window.navigator.msSaveOrOpenBlob(blob, filename);
    } else {
      const download = document.createElement('a');
      download.download = filename;
      download.href = URL.createObjectURL(blob);
      download.click();
    }
  }

  render() {
    const html = this.state.html;

    return (
      <div className="container-inner" id="container-inner">
        <div className="item" id="center">
          <div className="sheet">
            <textarea onChange={this.updateMarkdown} id="editing">{this.props.text}</textarea>
          </div>
        </div>
        <div className="item" id="right">
          <div className="sheet">
            <div dangerouslySetInnerHTML={{
              __html: html,
            }}
            />
            <hr />
            <h3>save as</h3>
            <div className="container-btn">
              <div className="item-btn pointer" onClick={this.saveAsNew}>new file</div>
              <div className="item-btn pointer" onClick={this.saveAsUpdate}>update</div>
              <div className="item-btn pointer" onClick={this.saveAsLocal}>local</div>
            </div>
            <p onClick={this.delete} className="lightletter pointer">delete</p>
          </div>
        </div>
      </div>);
  }
}

export class Gistcreator extends Component {
  constructor(props) {
    super(props);

    this.creategist = this.creategist.bind(this);
  }

  creategist() {
    const ACCESS_TOKEN = localStorage.getItem('ACCESS_TOKEN') || null;
    const description = document.getElementById('description').value;
    const secret = document.getElementById('secret').checked;
    const content = document.getElementById('editing').value;
    const filename = document.getElementById('filename').value;
    if (ACCESS_TOKEN && description && content && filename) {
      fetch('https://api.github.com/gists', {
        method: 'POST',
        headers: {
          Authorization: `token ${ACCESS_TOKEN}`,
          accept: 'application/json',
        },
        body: JSON.stringify({
          description,
          public: secret,
          files: {
            [filename]: {
              content,
            },
          },
        }),
      }).then((response) => {
        return response.text();
      }).then((text) => {
        alert('done!!');
        ReactDOM.render(<Dashboard />, document.getElementById('area'));
      }).catch((error) => {
        alert(error)
      });
    } else {
      alert('fill all blanks');
    }
  }

  render() {
    return (
      <div className="container-inner" id="container-inner">
        <div className="item" id="center">
          <div className="sheet">
            <textarea id="editing" />
          </div>
        </div>
        <div className="item" id="right">
          <div className="sheet">
            <p>description</p>
            <input type="text" id="description" />
            <p>filename</p>
            <input type="text" id="filename" />
            <label>
              <p>public　<input type="checkbox" id="secret" value="true" /></p>
            </label>
            <div className="container-btn">
              <div className="item-btn pointer" onClick={this.creategist}>create</div>
            </div>
          </div>
        </div>
      </div>);
  }
}

export class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      to: '',
      url: '',
      html: '',
      gist_id: '',
      filename: '',
      download: '',
      selected: 'mygists',
      json: this.props.json || null,
    };

    this.previewMarkdown = this.previewMarkdown.bind(this);
    this.getgists = this.getgists.bind(this);
    this.download = this.download.bind(this);
  }

  previewMarkdown(url, id, to, filename) {
    fetch(url).then((response) => {
      return response.text();
    }).then((text) => {
      this.setState({
        html: marked(text),
        to,
        text,
        gist_id: id,
        filename,
        url,
        download: 'download',
      });
    }).catch((error) => {
      alert(error)
    });
  }

  getgists(username) {
    const ACCESS_TOKEN = localStorage.getItem('ACCESS_TOKEN') || null;
    fetch(`https://api.github.com/users/${username}/gists`, {
      method: 'GET',
      cache: 'no-cache',
      headers: {
        Authorization: `token ${ACCESS_TOKEN}`,
        accept: 'application/json',
      },
    }).then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error('Network response was not ok.');
    }).then((json) => {
      json.sort((a, b) => {
        return (a.updated_at < b.updated_at ? 1 : -1);
      });
      this.setState({ selected: 'mygists', json });
    }).catch((error) => {
      alert(error)
    });
  }

  getstarredgists() {
    const ACCESS_TOKEN = localStorage.getItem('ACCESS_TOKEN') || null;
    fetch('https://api.github.com/gists/starred', {
      method: 'GET',
      headers: {
        Authorization: `token ${ACCESS_TOKEN}`,
        accept: 'application/json',
      },
    }).then((response) => {
      return response.json();
    }).then((json) => {
      json.sort((a, b) => {
        return (a.updated_at < b.updated_at ? 1 : -1);
      });
      this.setState({ selected: 'star', json });
    }).catch((error) => {
      alert(error)
    });
  }

  componentDidMount() {
    if (localStorage.getItem('ACCESS_TOKEN') && !this.props.json) {
      this.getgists(localStorage.getItem('LOGIN'));
    } else {
      this.setState({
        selected: '',
      });
    }
  }

  download() {
    fetch(this.state.url).then((response) => {
      return response.text();
    }).then((text) => {
      const filename = this.state.filename;
      const blob = new Blob([text], { type: 'text/plain' });
      if (window.navigator.msSaveBlob) {
        window.navigator.msSaveBlob(blob, filename);
        window.navigator.msSaveOrOpenBlob(blob, filename);
      } else {
        const download = document.createElement('a');
        download.download = filename;
        download.href = URL.createObjectURL(blob);
        download.click();
      }
    }).catch((error) => {
      alert(error)
    });
  }

  render() {
    const html = this.state.html;
    const login = localStorage.getItem('LOGIN') || false;
    return (
      <div className="container-inner" id="container-inner">
        <div className="item" id="center">
          <div className="sheet">
            {(() => {
              if (login) {
                return (
                  <div className="container-btn">
                    <div className={this.state.selected === 'mygists' ? 'item-btn item-btn-selected' : 'item-btn pointer'} onClick={() => { this.getgists(localStorage.getItem('LOGIN')); }}>my gists</div>
                    <div className={this.state.selected === 'star' ? 'item-btn item-btn-selected' : 'item-btn pointer'} onClick={() => { this.getstarredgists(); }}>star</div>
                  </div>
                );
              }
            })()}
            <div id="list">
              {(() => {
                if (this.state.json) {
                  console.log(this.state.json);
                  const list = [];
                  const user = localStorage.getItem('LOGIN');
                  this.state.json.forEach((e) => {
                    list.push(<h3>{e.description}</h3>);
                    Object.keys(e.files).forEach((f) => {
                      const to = (e.owner.login === user) ? 'edit' : 'Fork';
                      list.push(<li className="pointer" onClick={() => { this.previewMarkdown(e.files[f].raw_url, e.id, to, f); }}>{f}</li>);
                    });
                    list.push(<hr />);
                  });
                  return list;
                } else {
                  return <h2 className='lightletter'>please login<br />or<br />make teams and choose a member</h2>
                }
              })()}
            </div>
          </div>
        </div>
        <div className="item" id="right">
          <div className="sheet">
            <h1 className='lightletter'>preview</h1>
            <div dangerouslySetInnerHTML={{
              __html: html,
            }}
            />
            <br />
            <Btn
              to={this.state.to}
              text={this.state.text}
              gist_id={this.state.gist_id}
              filename={this.state.filename}
            />
            <div className="btn pointer" onClick={this.download}>{this.state.download}</div>
            <br />
          </div>
        </div>
      </div>);
  }
}

export class Freeeditor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      html: '<h3 style="text-align:center;border: dotted 2px #aaa;padding: 1em 0;color: #aaa;">Markdown output here</h3>',
    };

    this.updateMarkdown = this.updateMarkdown.bind(this);
    this.download = this.download.bind(this);
  }

  updateMarkdown(event) {
    this.setState({
      html: marked(event.target.value),
    });
  }

  download() {
    const content = document.getElementById('editing').value;
    const filename = 'Geditor.md';
    const blob = new Blob([content], { type: 'text/plain' });
    if (window.navigator.msSaveBlob) {
      window.navigator.msSaveBlob(blob, filename);
      window.navigator.msSaveOrOpenBlob(blob, filename);
    } else {
      const download = document.createElement('a');
      download.download = filename;
      download.href = URL.createObjectURL(blob);
      download.click();
    }
  }

  render() {
    const html = this.state.html;

    return (
      <div className="container-inner" id="container-inner">
        <div className="item" id="center">
          <div className="sheet">
            <textarea onChange={this.updateMarkdown} id="editing">{this.props.text}</textarea>
          </div>
        </div>
        <div className="item" id="right">
          <div className="sheet">
            <div dangerouslySetInnerHTML={{
              __html: html,
            }}
            />
            <div className="container-btn">
              <div className="item-btn pointer" onClick={this.download}>download.md</div>
            </div>
          </div>
        </div>
      </div>);
  }
}
