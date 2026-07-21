import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";
import { DaService } from "../../services/da.service";
import { AlertService } from "src/app/core/service/common/alert.service";

@Component({
  selector: "app-da-table-add-edit",
  templateUrl: "./da-table-add-edit.component.html",
  styleUrls: ["./da-table-add-edit.component.scss"],
})
export class DaTableAddEditComponent implements OnInit {
  modalRef: MDBModalRef;
  IDSelect: any;
  designationForm: FormGroup;
  heading: string;
  tableHeadings: any;
  content: any;
  rowData: any[] = [];
  rowid: any;
  designations: any;
  newDesignations: any;
  isNewDesignation: any;
  action: Subject<any> = new Subject<any>();
  approveReject: Subject<any> = new Subject<any>();
  addNewDesignation: Subject<any> = new Subject<any>();
  type: any;
  masterRow: any;
  tempRow: any;
  selectedDesignation: any;
  constructor(
    public mdbModalRef: MDBModalRef,
    public daService: DaService,
    private toastr: AlertService
  ) {}
  modifiyDetails: any;
  designation2: any;
  minValue: number = 0;
  maxRiskValue: number = 2147483640;
  designationError: boolean = false;
  riskValueZeroError: boolean = false;
  maxRiskValueError: boolean = false;
  value: number;
  desid: number;
  colid: number;
  riskRatingg: string;
  currentDesignation: any;
  isCommittee: any;
  ngOnInit() {
    this.designationForm = new FormGroup({
      designationtype: new FormControl(),
    });
    if (this.content.tableType == "committee") {
      this.isCommittee = "Y";
    } else {
      this.isCommittee = "N";
    }
    this.rowData = this.content.rowdata;
    this.designations = this.content.designations;
    this.tableHeadings = this.content.tableheadings;
    this.rowid = this.content.rowid;
    this.isNewDesignation = this.content.isNewDesignation;
    this.type = this.content.type;
    this.masterRow = this.content.masterRow;
    this.tempRow = this.content.tempRow;
    if (this.content.isNewDesignation) {
      this.getDesignationList();
    }
    this.modifiyDetails = this.content.modifiyDetails;
    this.currentDesignation = this.getDesignation(this.rowid);
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
    this.approveReject.unsubscribe();
  }

  preventNegative(event: any) {
    if (event.key === "-" || event.key === "e") {
      event.preventDefault();
    }
  }

  getDesignation(desid: number): string {
    const designation = this.designations.find(
      (designation) => designation.desid === desid
    );
    return designation ? designation.desName : "no designation found";
  }

  getCellValue(colid: number, riskRatingg: any, table?: any): number {
    if (this.type != "more")
      if (this.rowData.length > 0) {
        this.rowData = this.rowData.map((d: any) => ({
          ...d,
          riskRating: d.colNum == colid ? riskRatingg : d.riskRating,
          designationId: this.rowid,
        }));
      }

    if (this.type == "more") {
      if (table == "master") {
        const value = this.masterRow.find((val) => val.colNum === colid);
        if (value) {
          return value.value;
        } else {
          return 0;
        }
      } else {
        const value = this.tempRow.find((val) => val.colNum === colid);
        if (value) {
          return value.value;
        } else {
          return 0;
        }
      }
    } else {
      if (this.rowData) {
        const value = this.rowData.find((val) => val.colNum === colid);
        if (value) {
          return value.value;
        }
      }
    }
    return 0;
  }

  onColumnValueChange(value, colid, riskRatingg) {
    this.riskValueZeroError = false;
    this.value = value;
    this.colid = colid;
    this.riskRatingg = riskRatingg;
    if (value > this.maxRiskValue) {
      this.maxRiskValueError = true;
    } else {
      this.maxRiskValueError = false;
      this.addNewValue(colid, value, riskRatingg);
    }
  }

  update() {
    this.action.next(this.rowData);
    this.mdbModalRef.hide();
  }

  clear(): void {
    this.rowData = this.rowData.map((row: any) => ({
      ...row,
      value: 0,
    }));
  }

  addDesignation() {
    const totalSum = this.rowData.reduce((acc, obj) => acc + obj.value, 0);
    if (totalSum == 0) {
      this.riskValueZeroError = true;
    } else if (this.selectedDesignation) {
      this.designationError = false;
      this.riskValueZeroError = false;
      var temp = {
        designationCode: this.selectedDesignation.DESIGNATION_CODE,
        designation: this.selectedDesignation.DESIGNATION_DESCRIPTION,
        isCommittee: this.isCommittee,
      };

      let data = {
        designation: temp,
        rowData: this.rowData,
      };

      this.addNewDesignation.next(data);
      this.mdbModalRef.hide();
    } else {
      this.designationError = true;
    }
  }

  approveRejectButton(approveStatus: any) {
    this.approveReject.next(approveStatus);
    this.mdbModalRef.hide();
  }

  addNewValue(colNum: number, value: number, riskRatingg: string): void {
    if (this.rowData.length > 0) {
      this.rowData = this.rowData.map((d: any) => ({
        ...d,
        value: d.colNum == colNum ? value : d.value,
        designationId: this.rowid,
      }));
    }
  }

  addOrUpdateValue(colNum: number, value: any): void {
    let found = false;
    for (const cell of this.rowData) {
      if (cell.colNum === colNum) {
        cell.value = value;
        found = true;
        break;
      }
    }
    if (!found) {
      this.rowData.push({ colNum, value });
    }
  }

  getDesignationList() {
    if (this.isCommittee == "Y") {
      this.daService
        .getAllCommitteeDesignationsWithCode()
        .then((response: any) => {
          this.designations = response;
          this.IDSelect = response;
        })
        .catch((error: any) => {
          this.toastr.showToaster("Please contact the administraion", "ERROR");
        });
    } else {
      this.daService
        .getAllIndividualDesignationCode()
        .then((response: any) => {
          this.designations = response;
          this.IDSelect = response;
        })
        .catch((error: any) => {
          this.toastr.showToaster("Please contact the administraion", "ERROR");
        });
    }
  }

  onDesignationChange($event: any) {
    this.designationError = false;
    this.selectedDesignation = this.designations.find(
      (designation: any) =>
        designation.DESIGNATION_DESCRIPTION.replace(/\s/g, "").toLowerCase() ===
        $event.target.value.replace(/\s/g, "").toLowerCase()
    );
  }

  limitDecimalPlaces(event: any): void {
    const input = event.target;
    const value = input.value;

    // Regular expression to match numbers with up to two decimal places
    const regex = /^\d*\.?\d{0,2}$/;

    if (!regex.test(value)) {
      // If the value doesn't match the regex, remove extra decimals
      input.value = value.slice(0, -1);
    }
  }
}
