import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-single-node-html',
  templateUrl: './single-node-html.component.html',
  styleUrls: ['./single-node-html.component.scss']
})
export class SingleNodeHtmlComponent implements OnInit {

  @Input('htmlStr') htmlStr: any = '';

  constructor() {
  }

  ngOnInit() {
  }

}
