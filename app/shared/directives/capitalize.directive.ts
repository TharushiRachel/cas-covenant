import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appCapitalize]'
})
export class CapitalizeDirective {

  constructor(private el: ElementRef) {
  }

  @HostListener('keyup', ['$event'])
  onChange(data) {
    this.el.nativeElement.value = this.el.nativeElement.value.toUpperCase();
  }

}
