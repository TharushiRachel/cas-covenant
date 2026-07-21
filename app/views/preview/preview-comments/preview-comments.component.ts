import {Component, Input, OnInit} from '@angular/core';
import {Constants} from "../../../core/setting/constants";
import {CacheService} from "../../../core/service/data/cache.service";

@Component({
  selector: 'app-preview-comments',
  templateUrl: './preview-comments.component.html',
  styleUrls: ['./preview-comments.component.scss']
})
export class PreviewCommentsComponent implements OnInit {

  @Input("resizedFPCommentList") resizedFPCommentList: any[] = [];
  @Input("tabView") tabView = false;

  tableColumns = ['User', 'Date', 'Action', 'Comment'];
  allBanksDetails: any = [];

  constructor(private cacheService: CacheService,) {
  }

  ngOnInit() {    
    this.allBanksDetails = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
   // console.log("this.allBanksDetails", this.allBanksDetails);
  }

}
