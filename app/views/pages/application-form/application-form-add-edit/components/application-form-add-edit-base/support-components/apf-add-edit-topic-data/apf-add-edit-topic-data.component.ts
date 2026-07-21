import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subject} from "rxjs";
import {MasterDataService} from "../../../../../../../../core/service/data/master-data.service";
import {Constants} from "../../../../../../../../core/setting/constants";

@Component({
  selector: 'app-apf-add-edit-topic-data',
  templateUrl: './apf-add-edit-topic-data.component.html',
  styleUrls: ['./apf-add-edit-topic-data.component.scss']
})
export class ApfAddEditTopicDataComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  action: Subject<any> = new Subject<any>();
  dataUpdateDTO: Data = new Data({});
  content: any;
  givenData: any;
  header: any = '';

  isTinyMCEEnabled: boolean = false;

  constructor(
    public  mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private masterDataService: MasterDataService
  ) {
  }

  ngOnInit() {

    this.givenData = this.content.dataToEdit ? this.content.dataToEdit : '';
    this.header = this.content.header ? this.content.header : '';
    this.isTinyMCEEnabled = this.masterDataService.getSystemParameter(Constants.systemParamKey.TINYMCE_ENABLED);
  }

  saveAndCloseTemplateData(event) {
    this.action.next(event);
    this.mdbModalRef.hide();
  }

  saveTemplateData(event) {
    this.action.next(event);
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }
}

export class Data {
  data;

  constructor(dto) {
    dto = dto || {};
    this.data = dto.data || ''
  }
}
