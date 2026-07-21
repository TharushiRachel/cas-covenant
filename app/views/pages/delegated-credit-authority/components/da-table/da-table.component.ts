import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { DaTableAddEditComponent } from '../da-table-add-edit/da-table-add-edit.component';
import { DaService } from '../../services/da.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { Constants } from 'src/app/core/setting/constants';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { DaTableStructureComponent } from 'src/app/shared/components/da-table-structure/da-table-structure.component';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Component({
  selector: 'app-da-table',
  templateUrl: './da-table.component.html',
  styleUrls: ['./da-table.component.scss']
})
export class DaTableComponent implements OnInit {

  @Input() mode: any = 'edit';
  @ViewChildren(DaTableStructureComponent) childComponents!: QueryList<DaTableStructureComponent>;

  type = 'edit'
  modalRef: MDBModalRef;
  designationsList: any
  value: number;
  desid: number;
  colid: number;
  rowdata: any;
  designations: any[];
  daTableDTO: any[] = [];
  tempdaTableDTO: any[] = []
  approvedTableData = {};
  tempTableData = []
  tempDesignationList: any[]
  approvedDesignationList: any[]
  loggedUserName: any;
  columnIds: number[];
  designationOnView: any
  approvedAndPendingData: any
  customConfigs = { timeOut: 1000 }
  tableHeadings
  masterRow: any
  tempRow: any
  committeeTable='committee'
  individualTable = 'individual'
  childArray:any
  childComponent:any;
  constructor(private mdbModalService: MDBModalService, private daService: DaService, private toastr: AlertService, private appService: ApplicationService) { }

  ngOnInit() {
 
  }

  getColumnIds(headings: any[]): number[] {
    let subIds: number[] = [];

    function traverse(headings: any[]): void {
      for (let heading of headings) {
        if (heading.subId !== undefined) {
          subIds.push(heading.subId);
        }
        if (heading.subHeadings) {
          traverse(heading.subHeadings);
        }
      }
    }

    traverse(headings);
    return subIds;
  }

  getRowData(tableData: any, rowNum: number): any[] | undefined {
    return tableData[rowNum];
  }

  addNewDesignation(data:any) {
    this.openModalDATableEdit({ rowid: 0, isNewDesignation1: true, tableType: data })
  }

  editRiskValues(data) {
    this.openModalDATableEdit(data)
  }

  moreOptions(data) {
    this.openModalDATableEdit(data)
  }

  openModalDATableEdit(options: { rowid: any, tableName?: any, isNewDesignation1?: any, type?: any , tableType:any}) {
    var { rowid, tableName, isNewDesignation1, type , tableType} = options;

    
     this.childArray = this.childComponents.toArray(); 
   
     this.childComponent = this.childArray.find(child => child.tableType === tableType);
   
    this.tableHeadings = this.childComponent.tableHeadings;
   
    this.columnIds = this.getColumnIds(this.tableHeadings.tableHeadings);
    
    
    var heading = isNewDesignation1 ? "Add New Designation" : "Edit Delegated Credit Authority Limits"

    var modifiyDetails: any

    this.masterRow = this.getRowData(this.childComponent.approvedTableData, rowid)

    this.tempRow = this.getRowData(this.childComponent.tempTableData, rowid)

    if (type == "more") {

      const dataEntry = this.childComponent.tempdaTableDTO.find(entry => entry.designationId === rowid);
      
      modifiyDetails = {
        modifiedBy: dataEntry.modifiedBy ? dataEntry.modifiedBy : dataEntry.createdBy ? dataEntry.createdBy : 'unknown',
        modifiedDate: dataEntry.modifiedDate ? dataEntry.modifiedDate : dataEntry.createdDate ? dataEntry.createdDate : 'unknown'
      }

    }
    else {

      if (isNewDesignation1) {
        let temprow = [];
        // for (let i = 1; i <= 13; i++) {
        //   temprow.push({ colNum: i, value: 0 });

        // }
        this.childComponent.columnIds.forEach((id) => {
          temprow.push({ colNum: id, value: 0 });

        });

        this.rowdata = temprow
      }
      else {
        if (tableName === "master") {
          this.rowdata = this.masterRow
        }
        else {
          this.rowdata = this.tempRow
        }
      }
    }


    this.modalRef = this.mdbModalService.show(DaTableAddEditComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p audit-modal-margin-center',
      containerClass: '',
      animated: true,
      data: {
        heading: heading,
        content: {
          rowdata: this.rowdata,
          rowid: rowid,
          designations: this.childComponent.approvedDesignationList,
          tableheadings: this.childComponent.tableHeadings,
          isNewDesignation: isNewDesignation1,
          masterRow: this.masterRow,
          tempRow: this.tempRow,
          type: type,
          modifiyDetails: modifiyDetails,
          tableType:tableType
        },
      }
    });

    this.modalRef.content.action.subscribe((rowData: any) => {

      var requestBody: any = {
        dasEntityDTOList: rowData.map((cell: any) => ({
          designationId: cell.designationId,
          columnId: cell.colNum,
          riskValue: cell.value,
          riskRating: cell.riskRating
        }))
      }

      this.daService.addDataDATable(requestBody).then((res) => {
        let responseDesignationsList;
        let responseDAData;
        if(tableType == 'committee'){
      responseDesignationsList = res.daDesignationMasterData.committeeDesignations
      responseDAData = res.fullDATableRS.committee
        }
      else{
      responseDesignationsList = res.daDesignationMasterData.individualDesignations
      responseDAData = res.fullDATableRS.individual
      }
      
        this.childComponent.approvedAndPendingData = responseDAData     
        this.childComponent.tempdaTableDTO = this.childComponent.approvedAndPendingData.pendingDALimits
        this.childComponent.daTableDTO = this.childComponent.approvedAndPendingData.approvedDALimits
      
        this.toastr.showToaster("Editted Successfully!", SETTINGS.TOASTER_MESSAGES.success)
      }).catch((err) => {
        this.toastr.showToaster("Editted Successfully!", SETTINGS.TOASTER_MESSAGES.success)
      });
    });


    this.modalRef.content.addNewDesignation.subscribe((data: any) => {
      var rowData = data.rowData
      var designation = data.designation

      var rowDatatoSend: any = {
        dasEntityDTOList: rowData.map((cell: any) => ({
          designationId: cell.designationId,
          columnId: cell.colNum,
          riskValue: cell.value,
          riskRating: cell.riskRating
        }))
      }

      var requestBody = {
        daDesignationDTO: designation,
        daLimitListDTO: rowDatatoSend
      }

      this.daService.addNewDesignation(requestBody).then((res) => {
        this.childComponent.getDesignations()
        this.toastr.showToaster("Data Added Successfully!", SETTINGS.TOASTER_MESSAGES.success)
      }).catch((err) => {
        this.toastr.showToaster("Designation and values not added!", SETTINGS.TOASTER_MESSAGES.error)
      });
    })

    this.modalRef.content.approveReject.subscribe((status) => {
      this.openModalApproveRejectConfirmation(rowid, status,tableType)
    })
  }

  openModalApproveRejectConfirmation(desid, status,tableType) {

    var heading = status == 'approve' ? "APPROVE CONFIRMATION" : "REJECT CONFIRMATION"

    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center',
      containerClass: '',
      animated: true,
      data: {
        heading: heading,
        approveRejectType: status,
        message: `Do you want to ${status} ?`
      }
    });

    this.modalRef.content.action.subscribe((approveStatus) => {

      var requestBody: any
      if (approveStatus) {
        if (status == 'approve') {
          requestBody = {
            "approveStatus": Constants.approveStatusConst.APPROVED,
            "designationId": desid
          }
        }
        if (status == 'reject') {

          requestBody = {
            "approveStatus": Constants.approveStatusConst.REJECTED,
            "designationId": desid
          }
        }

        this.daService.approveRejectRiskValues(requestBody).then((res) => {
          let responseDesignationsList;
          let responseDAData;
          if(tableType == 'committee'){
        responseDesignationsList = res.daDesignationMasterData.committeeDesignations
        responseDAData = res.fullDATableRS.committee
          }
        else{
        responseDesignationsList = res.daDesignationMasterData.individualDesignations
        responseDAData = res.fullDATableRS.individual
        }
      
        
        this.childComponent.approvedAndPendingData = responseDAData      
        this.childComponent.tempdaTableDTO = this.childComponent.approvedAndPendingData.pendingDALimits
        this.childComponent.daTableDTO = this.childComponent.approvedAndPendingData.approvedDALimits


          let toastMessage = status == 'approve' ? 'Approved Successfully!' : 'Rejected Successfully!';
          this.toastr.showToaster(toastMessage, SETTINGS.TOASTER_MESSAGES.success);
        }).catch((err) => {
          this.toastr.showToaster("Approve or reject did not happen", SETTINGS.TOASTER_MESSAGES.error);
        });

      }
    });
  }
}
