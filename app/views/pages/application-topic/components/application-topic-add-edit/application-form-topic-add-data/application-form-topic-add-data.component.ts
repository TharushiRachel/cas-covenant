import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subject} from "rxjs";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {MasterDataService} from "../../../../../../core/service/data/master-data.service";
import {Constants} from "../../../../../../core/setting/constants";

@Component({
  selector: 'app-application-form-topic-add-data',
  templateUrl: './application-form-topic-add-data.component.html',
  styleUrls: ['./application-form-topic-add-data.component.scss']
})
export class ApplicationFormTopicAddDataComponent implements OnInit {

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

  addTemplateData(event) {
    this.action.next(event);
    this.mdbModalRef.hide();
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
