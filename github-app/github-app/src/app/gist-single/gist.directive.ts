import { Directive, ViewContainerRef } from '@angular/core';
@Directive ({
    selector : '[gist-host]',
})
export class GistDirective {
    constructor(public viewContainerRef : ViewContainerRef){
    }
}