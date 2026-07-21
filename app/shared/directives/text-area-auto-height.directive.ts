import {Directive, ElementRef, HostListener, Input, OnInit} from '@angular/core';

@Directive({
  selector: '[appTextAreaAutoHeight]'
})
export class TextAreaAutoHeightDirective implements OnInit {

  @Input() customHeight = '30';

  constructor(private elementRef: ElementRef) {
  }

  @HostListener(':input')
  onInput() {
    this.resize();
  }

  @HostListener(':click')
  onChange() {
    this.resize();
  }

  ngOnInit() {
    setTimeout(() => this.resize());
  }

  resize() {

    if (!this.elementRef.nativeElement.scrollHeight) {
      this.elementRef.nativeElement.style.height = (this.elementRef.nativeElement.scrollHeight + parseInt(this.customHeight)) + 'px';
    } else {
      this.elementRef.nativeElement.style.height = (this.elementRef.nativeElement.scrollHeight) + 'px';
    }
    this.elementRef.nativeElement.style['overflow-y'] = 'hidden';
  }
}
