import {Component, OnInit, Input, ViewChild, ComponentFactoryResolver} from '@angular/core';

import{ GistDirective } from './gist.directive';
import{ GistItem } from './gist-item';
import{ GistComponent } from './gist.component';



@Component({
  selector: 'app-gist-single',
  template: `
<div class="gist_area">
<ng-template gist-host></ng-template>
</div>`,
  styleUrls: ['./gist-single.component.css']
})

export class GistSingleComponent implements OnInit {
  @Input() gist : GistItem;

  @ViewChild(GistDirective) gistHost : GistDirective;


  constructor(private componentFactoryResolver : ComponentFactoryResolver) { }

  ngOnInit() {
    this.loadGistComponent();
  }
  loadGistComponent () {
    let gistItem = this.gist;
    let componentFactory = this.componentFactoryResolver.resolveComponentFactory(gistItem.component);

    let viewContainerRef = this.gistHost.viewContainerRef;

    let componetnRef = viewContainerRef.createComponent(componentFactory);
    (<GistComponent>componetnRef.instance).data = gistItem.data;
  }
}
