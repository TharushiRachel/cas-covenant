import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { DaService } from 'src/app/views/pages/delegated-credit-authority/services/da.service';
import { TABLE_HEADINGS,TABLE_HEADINGS_COMMITEE } from '../../../core/setting/table.heading';
import { ChangeOrderComponent } from '../change-order/change-order.component';
import * as _ from 'lodash';
import { DaTableChangeOrderComponent } from './da-table-change-order/da-table-change-order.component';


@Component({
  selector: 'app-da-table-structure',
  templateUrl: './da-table-structure.component.html',
  styleUrls: ['./da-table-structure.component.scss']
})
export class DaTableStructureComponent implements OnInit {

  @Input() mode: any = 'edit';
  @Input() tableType: any = ' ';
  @Output() addNewDesignation = new EventEmitter<string>();
  @Output() editRiskValues = new EventEmitter<any>();
  @Output() moreOptions = new EventEmitter<any>();

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
  columnIds: number[]
  designationOnView: any
  loggedUserDesignation: string
  approvedAndPendingData: any
  masterRow: any
  tempRow: any
  userDAs: any = [];
  tableHeadings = TABLE_HEADINGS;
  
  inActivated: any = false;

  constructor(
    private daService: DaService,
    private toastr: AlertService,
    private appService: ApplicationService,
    private mdbModalService: MDBModalService,
    private applicationService: ApplicationService) { }

  ngOnInit() {
    this.getLoggedUserDA();
    this.columnIds = this.getColumnIds(this.tableHeadings.tableHeadings);
    this.getDesignations()
    this.loggedUserName = this.appService.getLoggedInUserUserName()
    this.getUserUPMDetailsList()
   
    if (this.tableType == 'committee'){
      
      this.tableHeadings = TABLE_HEADINGS_COMMITEE;
      
    }
    else if (this.tableType == 'individual'){
    
      this.tableHeadings = TABLE_HEADINGS;
    }
   
  }

  getUserUPMDetailsList() {
    this.applicationService.getUserUPMDetailsList({
      "userID": this.applicationService.getLoggedInUserUserID(),
      "appCode": 'CAS'
    }).subscribe((response: any) => {
      if (response) {
        this.loggedUserDesignation = response.designationDescription;
        let isDAUser = this.userDAs.find((user: any) => user.userName === response.adUserID && user.status == "ACT" && user.approveStatus == "APPROVED")
        this.loggedUserDesignation = isDAUser ? response.designationDescription : null
      }
    }, (error) => {
      this.toastr.showToaster("Please contact the administraion", "ERROR")
    });
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

  isSumGreaterThanZero = (designationId) => {
    const sum = this.daTableDTO
      .filter(obj => obj.designationId === designationId)
      .reduce((acc, obj) => acc + obj.daColumnsDTO.columnValue, 0);

    return sum > 0;
  };

  getApprovedPendingData() {
    this.daService.getApprovedAndTempData().then((res) => {
    
      let responseDAData;
      if(this.tableType == 'committee'){
       
        responseDAData = res.committee
      }
      else{
    
        responseDAData = res.individual
      }
      this.approvedAndPendingData = responseDAData
    
      this.tempdaTableDTO = this.approvedAndPendingData.pendingDALimits
      this.daTableDTO = this.approvedAndPendingData.approvedDALimits
     
      if (this.mode == 'view' && this.daTableDTO) {
        const result = this.daTableDTO.filter(obj => this.isSumGreaterThanZero(obj.designationId));
        this.daTableDTO = result
        const uniqueDesignationIds = [...new Set(this.daTableDTO.map(item => item.designationId))];
        const designationsWithApprovedValues = this.approvedDesignationList.filter(item => uniqueDesignationIds.includes(item.desid));
        this.approvedDesignationList = designationsWithApprovedValues
      }
    }).catch((error) => {
      this.toastr.showToaster("Please contact the administraion", "ERROR")
    })
  }

  getApprovedDAData() {
    this.getApprovedPendingData()
    this.daTableDTO = this.approvedAndPendingData.approvedDALimits

    if (this.mode == 'view' && this.daTableDTO) {
      const result = this.daTableDTO.filter(obj => this.isSumGreaterThanZero(obj.designationId));
      this.daTableDTO = result
      const uniqueDesignationIds = [...new Set(this.daTableDTO.map(item => item.designationId))];
      const designationsWithApprovedValues = this.approvedDesignationList.filter(item => uniqueDesignationIds.includes(item.desid));
      this.approvedDesignationList = designationsWithApprovedValues
    }
  }

  getPendingDAData() {
    this.getApprovedPendingData()
    this.tempdaTableDTO = this.approvedAndPendingData.pendingDALimits
  }

  getDesignations() {
    this.daService.getApprovedDesignations().then((res) => {
      let responseDesignationsList;
      let responseDAData;
      if(this.tableType == 'committee'){
        responseDesignationsList = res.daDesignationMasterData.committeeDesignations
        responseDAData = res.fullDATableRS.committee
      }
      else{
        responseDesignationsList = res.daDesignationMasterData.individualDesignations
        responseDAData = res.fullDATableRS.individual
      }
      
      this.designationsList = responseDesignationsList.map((des: any) => ({
        desid: des.id,
        desName: des.designation,
        createdBy: des.createdBy,
        modifiedBy: des.modifiedBy,
        displayOrder: des.displayOrder
      }));
      
      this.approvedDesignationList = this.designationsList
      this.approvedAndPendingData = responseDAData
      this.tempdaTableDTO = this.approvedAndPendingData.pendingDALimits
      this.daTableDTO = this.approvedAndPendingData.approvedDALimits
     
      this.getApprovedPendingData()
    }).catch((err) => {
      this.toastr.showToaster("Please contact the administraion", "ERROR")
    });
  }

  getLoggedUserDA() {
    this.daService.getAllDAUsers().then((res) => {
      this.userDAs = res;
    }).catch((err) => {
      this.toastr.showToaster("Please contact the administraion", "ERROR")
    });
  }

  addOrUpdateValue(tableData: any, rowNum: number, colNum: number, value: any, createdBy: string = null, modifiedBy: string = null): any {
    if (tableData.hasOwnProperty(rowNum)) {
      const columnIndex = tableData[rowNum].findIndex(cell => cell.colNum === colNum);
      if (columnIndex !== -1) {
        tableData[rowNum][columnIndex].value = value;
      } else {
        tableData[rowNum].push({ colNum, value });
      }
    } else {
      tableData[rowNum] = [{ colNum, value }];
    }
    return tableData
  }

  getRowData(tableData: any, rowNum: number): any[] | undefined {
    return tableData[rowNum];
  }



  getApprovedCellValues(designationId: number, columnId: number): number {
    const dataEntry = this.daTableDTO.find(entry => entry.designationId === designationId && entry.daColumnsDTO.columnId === columnId);

    if (dataEntry) {
      this.approvedTableData = this.addOrUpdateValue(this.approvedTableData, designationId, columnId, dataEntry.daColumnsDTO.columnValue)
      return (dataEntry.daColumnsDTO.columnValue)
    }
    this.approvedTableData = this.addOrUpdateValue(this.approvedTableData, designationId, columnId, 0)
    return (0);
  }

  getPendingCellValues(designationId: number, columnId: number): number {
    const dataEntry = this.tempdaTableDTO.find(entry => entry.designationId === designationId && entry.daColumnsDTO.columnId === columnId);

    if (dataEntry) {
      this.tempTableData = this.addOrUpdateValue(this.tempTableData, designationId, columnId, dataEntry.daColumnsDTO.columnValue, dataEntry.createdBy, dataEntry.modifiedBy)
      this.getApprovedCellValues(designationId, columnId)
      return (dataEntry.daColumnsDTO.columnValue)
    }
    this.tempTableData = this.addOrUpdateValue(this.tempTableData, designationId, columnId, 0)
    this.getApprovedCellValues(designationId, columnId)
    return (0);
  }

  getDesignation(desid: number): string {
   
    let designation: any = this.approvedDesignationList.find((designation: any) => designation.desid === desid);
    return designation ? designation.desName : 'no designation found';
  }

  isAuthorizeToApproveReject(desid): boolean {
    const dataEntry = this.tempdaTableDTO.find(entry => entry.designationId === desid);

    if (dataEntry) {
      if (dataEntry.modifiedBy && dataEntry.modifiedBy != this.loggedUserName) {
        return true
      }
      if (dataEntry.createdBy && !dataEntry.modifiedBy && dataEntry.createdBy != this.loggedUserName) {
        return true
      }
      return false
    }
    return false
  }

  isAuthorizeToEdit(desid): boolean {
   
    const dataEntry = this.tempdaTableDTO.find(entry => entry.designationId === desid);
    if (dataEntry) {
      return false
    }
    return true
  }

  onColumnValueChange(value, desid, colid) {
    this.value = value
    this.desid = desid
    this.colid = colid
  }

  isDesignationIdExists(array, id) {
    return array.some(item => item.id === id);
  }

  addNewDesignationbtn() {
   
    this.addNewDesignation.emit(this.tableType);
  }

  editRiskValuesbtn(data) {
    
    const dataWithTableType = {...data, tableType: this.tableType}
   
    this.editRiskValues.emit(dataWithTableType)
  }

  moreOptionbtn(data) {
    
    const dataWithTableType = {...data, tableType: this.tableType}
   
    this.moreOptions.emit(dataWithTableType)
  }

  openModalUpdateDesignationOrder() {
    this.modalRef = this.mdbModalService.show(DaTableChangeOrderComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p modal-dialog-scrollable',
      containerClass: '',
      animated: true,
      data: {
        heading: "Change Designation Order",
        actionName: "Ok",
        dataList: this.approvedDesignationList,
        keyData: {
          keyOne: 'desName',
          keyTwo: 'desCode'
        }
      }
    });
    this.modalRef.content.action.subscribe((result: any) => {
      if (!_.isEmpty(result)) {
        this.inActivated = true;
        // this.approvedDesignationList = result
        this.getDesignations();
      }
    });
  }

  // saveUpdate() {
  //   let saveData = Object.assign({},
  //     {
  //       daDisplayOrderDTOList : this.approvedDesignationList.map((data: any, index) => {
  //         data.displayOrder = index;
  //         return data;
  //       })
  //     },
  //   );
  //   console.log("saveData", saveData)
  //   this.daService.changeDAOrder(saveData).then((res) => {
  //     this.designationsList = res.daDesignationMasterData.map((des: any) => ({
  //       desid: des.id,
  //       desName: des.designation,
  //       createdBy: des.createdBy,
  //       modifiedBy: des.modifiedBy,
  //       displayOrder: des.displayOrder
  //     }));
  //     this.approvedDesignationList = this.designationsList
  //     this.approvedAndPendingData = res.fullDATableRS
  //     this.tempdaTableDTO = this.approvedAndPendingData.pendingDALimits
  //     this.daTableDTO = this.approvedAndPendingData.approvedDALimits
  //     this.getApprovedPendingData()
  //   }).catch((err) => {
  //     this.toastr.showToaster("Please contact the administraion", "ERROR")
  //   });
  //   //this.creditFacilityTemplateAddEditService.saveUpdateCreditFacilityTemplate(AppUtils.trim(saveData));
  // }


}


