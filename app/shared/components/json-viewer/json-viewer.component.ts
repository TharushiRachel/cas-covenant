import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, Renderer2, ViewChild} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";

declare var JsonHuman: any;

@Component({
  selector: 'app-json-viewer',
  templateUrl: './json-viewer.component.html',
  styleUrls: ['./json-viewer.component.scss']
})
export class JsonViewerComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('jsonView', {static: false}) jsonView: ElementRef;

  content: any = {};

  constructor(public  mdbModalRef: MDBModalRef,
              private renderer: Renderer2) { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    let html = JsonHuman.format(this.content.data, {showArrayIndex: true});
    this.renderer.appendChild(this.jsonView.nativeElement, html);
  }

  ngOnDestroy(): void {
  }


}
