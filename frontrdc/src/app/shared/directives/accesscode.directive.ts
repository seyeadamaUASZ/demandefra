import { Directive, OnInit, TemplateRef, ViewContainerRef, Input } from '@angular/core';

@Directive({ selector: '[accesscode]'})
export class AccesscodeDirective implements OnInit {
    actions :any;
    constructor(
        private templateRef: TemplateRef<any>,
        private viewContainer: ViewContainerRef
    ) {
        this.actions = JSON.parse(sessionStorage.getItem('actions'));
     }

    accesscodevar: string;

    @Input() 
    set accesscode(code: string) {    
         //alert("--------------------Access code----------------------"+code);   
        this.accesscodevar = code;
    }

    ngOnInit() {
        let hasAccess = false;            
        //alert("--------------------Has access----------------------"+actions[this.accesscodevar]);
        hasAccess = this.actions[this.accesscodevar];
        if (hasAccess) {
            this.viewContainer.createEmbeddedView(this.templateRef);
        } else {
            this.viewContainer.clear();
        }
    }
}