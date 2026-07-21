import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.scss']
})
export class TabComponent implements OnInit {

  @Input('tabTitle') public title: string;
  @Input() public active = false;

  @Input('showIcon') public showIcon: boolean = false;
  @Input('iconName') public iconName: string = '';
  @Input('isPrimary') public isPrimary: boolean = false;
  @Input('warningTitle') public warningTitle: boolean = false;

  @Input('showBadge') public showBadge: boolean = false;
  @Input('badgeContent') public badgeContent: string = '';
  @Input('badgeColor') public badgeColor: string = 'red';

  constructor() {
  }

  ngOnInit() {
  }

}
