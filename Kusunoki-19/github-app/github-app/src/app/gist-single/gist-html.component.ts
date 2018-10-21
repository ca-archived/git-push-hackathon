import {Component, Input} from '@angular/core';
import {GistComponent} from './gist.component';

@Component ({
    template: `
<a href="https://gist.github.com/{{data.id}}">{{data.file_name}}</a>
<gh-gist src="https://gist.github.com/{{data.owner_name}}/{{data.id}}.js"></gh-gist>
`,
})
export class GistHtmlComponent implements GistComponent{
    @Input() data ; any;
}