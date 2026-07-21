import {AfterViewInit, Directive, ElementRef, Input} from '@angular/core';

@Directive({
  selector: '[appFocusInput]'
})
export class FocusInputDirective implements AfterViewInit {

  @Input('appFocusInput') pageType: string;

  constructor(private el: ElementRef) {
  }

  ngAfterViewInit(): void {
    if (this.pageType == 'new' || this.pageType == 'true') {
      setTimeout(() => {
        this.el.nativeElement.focus();
      });
    }
  }
}
