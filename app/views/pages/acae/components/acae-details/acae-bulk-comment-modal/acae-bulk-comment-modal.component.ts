import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { IMyOptions, MDBModalRef } from 'ng-uikit-pro-standard';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { ACAEService } from '../../../services/acae-base.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-acae-bulk-comment-modal',
  templateUrl: './acae-bulk-comment-modal.component.html',
  styleUrls: ['./acae-bulk-comment-modal.component.scss']
})
export class AcaeBulkCommentModalComponent implements OnInit {
  bulkCommentForm: FormGroup;
  remark: String = new String('');
  content: any;
  isCommentSave: Boolean = false;
  isCommentEdited: Boolean = false;
  anticipatedDateStr  ="";
  constructor(
    private formBuilder: FormBuilder,
    public acaeDetailsEditmodalRef: MDBModalRef,
    private applicationService: ApplicationService,
    private alertService: AlertService,
    private acaeBaseService: ACAEService,
  ) { }

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy-mm-dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo'
  };
  refreshGridAction : Subject<any> = new Subject<any>();

  availableTagsManager = [
    "Since Regularized",
    "Paid against leeway of depositsof Rs..................../- ( Leeway - Rs.............../-)",
    "Paid against float Balance of Rs................./-",
    "Paid against Post dated cheques ( Date of realization of PDs.....................)",
    "Rs....................../- deposited to the account during .............................",
    "Excess reduced to Rs...................../-"
  ];

  availableTagsRMOnly = [
    "Approved. Please regularize the account and report",
    "Approved. Please arrange a regular limit",
    "Approved",
    "Recommended"
  ];

  availableTagsRM = [
    "Approved. Please regularize the account and report",
    "Please Regularize the account & report",
    "Casual excesses will not entertain until settlement of loan arrears",
    "Please recover loan arrears before ...........",
    "Please arrange a regular limit",
    "Let us have the settlement arragements of loan arrears",
    "Late submission - Please submit on stipulated time frame",
    "Approved",
    "Recommended"
  ];

  suggestCommentArr: string[];

  ngOnInit() {
    this.bulkCommentForm = this.formBuilder.group({
      remark: "",
      anticipatedDateStr: null
    });
    this.loadSuggestComment();
  }

  loadSuggestComment() {
    if (this.applicationService.getLoggedInUserUPMGroupCode() == Constants.applicationSecurityWorkClass.RM) {
      this.suggestCommentArr = this.availableTagsRMOnly;
    } else if (this.applicationService.getLoggedInUserUPMGroupCode() > Constants.applicationSecurityWorkClass.MANAGER) {
      this.suggestCommentArr = this.availableTagsRM;
    } else {
      this.suggestCommentArr = this.availableTagsManager;
    }
  }

  getRefreshComment($event: String) {
    this.remark = $event
    this.isCommentSave = false;
    this.isCommentEdited = true;
    if ($event.length === 1) {
      this.autocomplete(document.getElementById('myInput'), this.suggestCommentArr, "firstSug");
    } else if ($event.length > 1 && $event.length <= 4) {
      this.autocomplete(document.getElementById('myInput'), this.suggestCommentArr, "moreSug");
    } else {
      this.clearAllSuggestion();
   }
  }

  clearAllSuggestion(){
    document.getElementById('myInputautocomplete-list')?document.getElementById('myInputautocomplete-list').remove():"";
    var x = document.getElementsByClassName('autocomplete-items');
    for (var i = 0; i < x.length; i++) {
        x[i].removeChild(x[i]);
    }
  }

  closeAllLists(elmnt?: any) {
    var x = document.getElementsByClassName("autocomplete-items");
    for (var i = 0; i < x.length; i++) {
      if (elmnt != x[i] && elmnt != document.getElementById('myInput')) {
        x[i].parentNode.removeChild(x[i]);
      }
    }
  }

  autocomplete(inp: any, arr: any, condition: string) {
    var currentFocus: number = 0;

    inp.addEventListener("input", (e: any) => {
      var a, b, i, val = e.data;
      this.closeAllLists();
      if (!val) {
        return false;
      }
      a = document.createElement("DIV");

      a.style.cursor = "pointer";
      a.style.position = "absolute";

      a.style.boxShadow = "0px 8px 16px 0px rgba(0, 0, 0, 0.2)";
      a.style.width = "100%";
      a.style.backdrop = 'static'
      a.style.ignoreBackdropClick = true;
      a.style.zIndex = 2;

      a.setAttribute("id", inp.id + "autocomplete-list");
      a.setAttribute("class", "autocomplete-items");

      let parent = (<HTMLElement>(<HTMLElement>e.target).parentNode);
      parent.appendChild(a);

      if (condition == "firstSug") {
        for (i = 0; i < arr.length; i++) {

          b = document.createElement("DIV");

          b.style.border = "1px solid #d4d4d4"
          b.style.borderRadius = "0 0 2px 2px";
          b.style.backgroundColor = "white"
          b.style.backdrop = 'static'
          b.style.ignoreBackdropClick = true;
          b.style.zIndex = 2;

          b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
          b.innerHTML += arr[i].substr(val.length);
          b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";

          b.addEventListener("click", (e: any) => {

            let elementTarget = (<HTMLElement>(<HTMLElement>e.target));
            inp.value = elementTarget.getElementsByTagName("input")[0].value;
            this.remark = elementTarget.getElementsByTagName("input")[0].value;
            this.closeAllLists();
            currentFocus = 0;

          });
          a.appendChild(b);
        }
      } else {
        for (i = 0; i < arr.length; i++) {

          if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {

            b = document.createElement("DIV");

            b.style.border = "1px solid #d4d4d4"
            b.style.borderRadius = "0 0 2px 2px";
            b.style.backgroundColor = "white"
            b.style.backdrop = 'static'
            b.style.ignoreBackdropClick = true;
            b.style.zIndex = 2;

            b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
            b.innerHTML += arr[i].substr(val.length);
            b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";

            b.addEventListener("click", (e) => {

              let elementTarget = (<HTMLElement>(<HTMLElement>e.target));
              inp.value = elementTarget.getElementsByTagName("input")[0].value;
              this.remark = elementTarget.getElementsByTagName("input")[0].value;
              this.closeAllLists();
              currentFocus = 0;

            });
            a.appendChild(b);
          }
        }
      }
    });

    inp.addEventListener("keydown", (e: any) => {
      var x: any = document.getElementById(inp.id + "autocomplete-list");

      if (x) x = x.getElementsByTagName("div");

      if (e.keyCode == 40) {
        currentFocus++;
        this.addActive(x, currentFocus);
      } else if (e.keyCode == 38) {
        currentFocus--;
        this.addActive(x, currentFocus);
      } else if (e.keyCode == 13) {
        e.preventDefault();
        if (currentFocus > -1) {
          if (x) {
            inp.value = arr[currentFocus];
            this.remark = arr[currentFocus];
            this.closeAllLists();
            currentFocus = 0;
          }
        }
      }
    });
  }

  addActive(x: any, currentFocus: any) {
    if (!x) return false;
    this.removeActive(x);
    if (currentFocus >= x.length) currentFocus = 0;
    if (currentFocus < 0) currentFocus = (x.length - 1);

    x[currentFocus].classList.add("autocomplete-active");
    x[currentFocus].style.backgroundColor = "DodgerBlue"
  }

  removeActive(x: any) {
    for (var i = 0; i < x.length; i++) {
      x[i].classList.remove("autocomplete-active");
      x[i].style.backgroundColor = "white"
    }
  }

  addComment() {
    let { anticipatedDateStr } = this.bulkCommentForm.getRawValue();
    this.anticipatedDateStr = anticipatedDateStr ? anticipatedDateStr :"";
    if (this.remark == "") {
      this.alertService.showToaster("Please add a comment!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    } if (this.anticipatedDateStr == "") {
      this.alertService.showToaster("Please specify the date!", SETTINGS.TOASTER_MESSAGES.error)
      return;
    } 
    else {
      let dataRQ = {}
      dataRQ = {
        recordSet: this.content.initialState.recordSet,
        comment: this.remark,
        negDate: anticipatedDateStr,
        previousNegDate: anticipatedDateStr,
      }
      this.acaeBaseService.saveBulkComments(dataRQ).subscribe((response: any) => {
        if (response) {
          this.alertService.showToaster("Batch Comment added successfully", SETTINGS.TOASTER_MESSAGES.success)
          this.isCommentSave = true;
          Promise.resolve(this.refreshGridAction.next(true)).then((value) => this.onCloseModel())
        }
      }, (error) => {
        this.alertService.showToaster("Comment Added Failed!", SETTINGS.TOASTER_MESSAGES.error)
      });
    }
  }

  clearComment() {
    this.bulkCommentForm.reset({
      anticipatedDateStr: null,
      remark: [""],
    });
  }

  onCloseModel() {
    this.acaeDetailsEditmodalRef.hide();
  }

}
