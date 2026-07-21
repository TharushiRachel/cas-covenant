import {Component, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder} from "@angular/forms";
import {Subscription} from "rxjs/Rx";
import * as _ from 'lodash';
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {Pagination} from "../../../../../core/dto/pagination";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {MyFacilityPaperCountService} from "../../../../../core/service/facility-paper/my-facility-paper-count.service";
import {CommitteePaperService} from "../../services/committee-paper.service";
import {AppUtils} from "../../../../../shared/app.utils";
import {isEmpty} from 'lodash';
import {CacheService} from "../../../../../core/service/data/cache.service";
import {PrivilegeService} from "../../../../../core/service/authentication/privilege.service";
//import { FacilityPaperAddEditService } from '../../../facility-paper/services/facility-paper-add-edit.service';

@Component({
  selector: 'app-committee-paper-dashboard',
  templateUrl: './committee-paper-dashboard.component.html',
  styleUrls: ['./committee-paper-dashboard.component.scss']
})
export class CommitteePaperDashboardComponent implements OnInit, OnDestroy {

 @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
  selectedFacilityPaperID;


   uniquePageName = 'CommitteePaperComponent-#343rta';
   pageSize = new PageSize({pageSize: 25});
   committeePaperStatusConst = Constants.committeePaperStatusConst;
   committeePaperStatus = Constants.committeePaperStatus;
   committeePaperDashboardStatusConst = Constants.committeePaperDashboardStatusConst;
   committeePaperDashboardStatus = Constants.committeePaperDashboardStatus;

   bccPaperStatusConst = Constants.bccPaperStatusConst;
   bccPaperStatus = Constants.bccPaperStatus;
   bccWorkFlowStatus = Constants.bccWorkFlowStatus;
   bccWorkFlowStatusConst = Constants.bccWorkFlowStatusConst;
   bccPaperDashboardStatusConst = Constants.bccPaperDashboardStatusConst;
   bccPaperDashboardStatus = Constants.bccPaperDashboardStatus;
   bccCommitteeName = 'Board Credit Committee';
   committeePapers: any = [];
   committeePapersPage: any = [];
   status: String = this.committeePaperDashboardStatusConst.INBOX;
   allBankOptions: any = {};
   committeeTypeList: any[] = [];
   hasPrivilegeToViewBCCPapers = false;
   bccEntererWorkClass = "";
   bccAuthorizerWorkClass = "";
   bccInquirerWorkClass = "";
   loggedInUserWorkClass = "";
   loggedInUserRole = "";

  committeePaperCounts: any = {
         inboxCommitteePaper: 0,
         inProgressCommitteePaper: 0,
         returnedCommitteePaper: 0,
         approvedCommitteePaper: 0,
         declinedCommitteePaper: 0,
       };

   bccPaperCounts: any = {
            inboxBCCPaper: 0,
            inProgressBCCPaper: 0,
            approvedBCCPaper: 0,
            declinedBCCPaper: 0,
          };

  tableColumns: any = ['Customer Name',  'Facility Paper Reference', 'Account Number', 'Branch', 'Received On', 'Assigned User', 'Status'];

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  constructor(
    private committeePaperService: CommitteePaperService,
      private searchDataCacheService: SearchDataCacheService,
      private formBuilder: FormBuilder,
      private urlEncodeService: UrlEncodeService,
      private router: Router,
      private privilegeService: PrivilegeService,
      private myFacilityPaperCountService: MyFacilityPaperCountService,
      private cacheService: CacheService,
      private applicationService: ApplicationService

  ) {
  }

  ngOnInit() {
     this.hasPrivilegeToViewBCCPapers = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_VIEW_BCC_PAPER);
     this.committeeTypeList = this.cacheService.getData(Constants.masterDataKey.CAS_COMMITTEE_TYPE_LIST);
     this.bccEntererWorkClass = this.cacheService.getData(Constants.masterDataKey.CAS_BCC_ENTERER_WORK_CLASS);
     this.bccAuthorizerWorkClass = this.cacheService.getData(Constants.masterDataKey.CAS_BCC_AUTHORIZER_WORK_CLASS);
     this.bccInquirerWorkClass = this.cacheService.getData(Constants.masterDataKey.CAS_BCC_INQUIRER_WORK_CLASS);
     this.loggedInUserWorkClass = this.applicationService.getLoggedInUserUPMGroupCode();

      /*if (this.loggedInUserWorkClass == this.bccInquirerWorkClass){
        this.loggedInUserRole = "BCC_INQUIRER";
      }*/
      if (this.loggedInUserWorkClass == this.bccEntererWorkClass){
        this.loggedInUserRole = "BCC_ENTERER";
      }
      if (this.loggedInUserWorkClass == this.bccAuthorizerWorkClass){
         this.loggedInUserRole = "BCC_AUTHORIZER";
      }

      if(this.hasPrivilegeToViewBCCPapers) {
        this.committeePaperService.getBCCPaperCounts({
              loggedInUserId: this.applicationService.getLoggedInUserUserName() ,
              loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
              loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
              loggedInUserRole:this.loggedInUserRole,
          }).then((data: any) =>{
              this.bccPaperCounts = data;
               this.loadInitialBCCDashboardPage();
         });
      }/*else{
        if (this.loggedInUserRole == "BCC_INQUIRER"){
          this.committeePaperService.getBCCPaperCounts({
              loggedInUserId: this.applicationService.getLoggedInUserUserName() ,
              loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
              loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
              loggedInUserRole:this.loggedInUserRole,
          }).then((data: any) =>{
              this.bccPaperCounts = data;
              this.committeePaperService.getCommitteePaperCounts({
                  loggedInUserId: this.applicationService.getLoggedInUserUserName() ,
                  loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
                  loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
                  loggedInUserRole:this.loggedInUserRole,
              }).then((data: any) =>{
                  this.committeePaperCounts = data;
                  this.committeePaperCounts.inboxCommitteePaper = this.committeePaperCounts.inboxCommitteePaper +  this.bccPaperCounts.inboxBCCPaper;
                  this.loadInitiateDashboardPage();
             });
          });
        }else{
          this.committeePaperService.getCommitteePaperCounts({
              loggedInUserId: this.applicationService.getLoggedInUserUserName() ,
              loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
              loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
              loggedInUserRole:this.loggedInUserRole,
           }).then((data: any) =>{
              this.committeePaperCounts = data;
              this.loadInitiateDashboardPage();
           });
        }
       }*/

     /*if(!this.hasPrivilegeToViewBCCPaper){
        this.committeePaperService.getCommitteePaperCounts({
            loggedInUserId: this.applicationService.getLoggedInUserUserName() ,
            loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
            loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
            loggedInUserRole:this.loggedInUserRole,
        }).then((data: any) =>{
            this.committeePaperCounts = data;
            this.loadInitiateDashboardPage();
       });
     }*/

  }

    loadInitiateDashboardPage() {
     /* if (this.loggedInUserRole == "BCC_INQUIRER"){
        this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.FORWARDED,this.bccPaperCounts.inboxBCCPaper);
      }*/
      this.loadSubStatusData(this.committeePaperDashboardStatusConst.INBOX, this.committeePaperStatusConst.FORWARDED,this.committeePaperCounts.inboxCommitteePaper);
    }

    loadSubStatusData(committeePaperDashboardStatus, committeePaperDashboardSubStatus, subStatusCount){
       if (subStatusCount != 0){
          this.committeePaperService.getCommitteePaperByStatus({
            loggedInUserId : this.applicationService.getLoggedInUserUserName(),
            loggedInUserBranchCode : this.applicationService.getLoggedInUserDivCode(),
            loginUpmAccessLevel : this.applicationService.getLoggedInUserUPMGroupCode(),
            loggedInUserRole: this.loggedInUserRole,
            committeePaperDashboardStatus: committeePaperDashboardStatus,
            committeePaperDashboardSubStatus : committeePaperDashboardSubStatus
          },
          new Pagination({
              pageSize: this.pageSize.pageSize,
              pageIndex: 0
            }
          )
        ).then((data: any) =>{
             let committeePaperList;
              this.committeeTypeList.forEach(committeeType => {
                if (committeeType.status == "ACT"){
                  /* if ( this.loggedInUserRole == 'BCC_INQUIRER'){
                     if (committeePaperDashboardStatus == Constants.committeePaperDashboardStatusConst.INBOX ||
                         committeePaperDashboardStatus == Constants.committeePaperDashboardStatusConst.RETURNED){
                         if (committeeType.committeeTypeName != "BCC"){
                           committeePaperList = _.filter(data.pageData || [], committeePaper => {
                              return committeePaper.committeeTypeID === committeeType.committeeTypeId});
                           this.committeePapers[committeeType.committeeDescription] = committeePaperList;
                           this.committeePapersPage[committeeType.committeeDescription] = data;
                         }
                     }else{
                          committeePaperList = _.filter(data.pageData || [], committeePaper => {
                             return committeePaper.committeeTypeID === committeeType.committeeTypeId});
                          this.committeePapers[committeeType.committeeDescription] = committeePaperList;
                          this.committeePapersPage[committeeType.committeeDescription] = data;
                           }
                   }else{*/
                    if (committeeType.committeeTypeName != "BCC"){
                       committeePaperList = _.filter(data.pageData || [], committeePaper => {
                          return committeePaper.committeeTypeID === committeeType.committeeTypeId});
                       this.committeePapers[committeeType.committeeDescription] = committeePaperList;
                       this.committeePapersPage[committeeType.committeeDescription] = data;
                     }
                   }
               // }
             });
         });

      }else{
        this.committeeTypeList.forEach(committeeType => {
            if (committeeType.status == "ACT"){
              /* if ( this.loggedInUserRole == 'BCC_INQUIRER'){
                 if (committeePaperDashboardStatus == Constants.committeePaperDashboardStatusConst.INBOX ||
                     committeePaperDashboardStatus == Constants.committeePaperDashboardStatusConst.RETURNED){
                     if (committeeType.committeeTypeName != "BCC"){
                       this.committeePapers[committeeType.committeeDescription] =[];
                       this.committeePapersPage[committeeType.committeeDescription] = [];
                     }
                 }else{
                   this.committeePapers[committeeType.committeeDescription] =[];
                   this.committeePapersPage[committeeType.committeeDescription] = [];
                 }
               }else{*/
                 if (committeeType.committeeTypeName != "BCC"){
                  this.committeePapers[committeeType.committeeDescription] =[];
                  this.committeePapersPage[committeeType.committeeDescription] = [];
                 }
               //}
            }
        });
      }

    }

  loadPageData(committeePaperDashboardStatus) {
     this.committeePapers = [];
     this.committeePapersPage = [];

    // if (this.loggedInUserRole == "BCC_INQUIRER"){
        switch (committeePaperDashboardStatus) {
           case this.committeePaperDashboardStatusConst.INBOX: {
              this.loadSubStatusData(this.committeePaperDashboardStatusConst.INBOX, this.committeePaperStatusConst.FORWARDED,this.committeePaperCounts.inboxCommitteePaper);
             /*  if (this.loggedInUserRole == "BCC_INQUIRER"){
                 this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.FORWARDED,this.bccPaperCounts.inboxBCCPaper);
               }*/
              break;
           }
           case this.committeePaperDashboardStatusConst.IN_PROGRESS: {
             this.loadSubStatusData(this.committeePaperDashboardStatusConst.IN_PROGRESS, this.committeePaperStatusConst.RECOMMENDED,this.committeePaperCounts.inProgressCommitteePaper);
             break;
           }
           case this.committeePaperDashboardStatusConst.RETURNED: {
             this.loadSubStatusData(this.committeePaperDashboardStatusConst.RETURNED, this.committeePaperStatusConst.RETURNED,this.committeePaperCounts.returnedCommitteePaper);
             break;
           }
           case this.committeePaperDashboardStatusConst.COMMITTEE_APPROVED: {
              this.loadSubStatusData(this.committeePaperDashboardStatusConst.COMMITTEE_APPROVED, this.committeePaperStatusConst.COMMITTEE_APPROVED,this.committeePaperCounts.approvedCommitteePaper);
              break;
           }
           case this.committeePaperDashboardStatusConst.DECLINED: {
             this.loadSubStatusData(this.committeePaperDashboardStatusConst.DECLINED, this.committeePaperStatusConst.DECLINED,this.committeePaperCounts.declinedCommitteePaper);
             break;
          }
        }
     this.status = committeePaperDashboardStatus;
   }

   onPageEvent(event, committeePaperDashboardSubStatus) {
       this.pageSize.pageSize = event.pageSize;
       this.committeePaperService.getCommitteePaperByStatus({
          loggedInUserId : this.applicationService.getLoggedInUserUserName(),
          loggedInUserBranchCode : this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel : this.applicationService.getLoggedInUserUPMGroupCode(),
          loggedInUserRole: this.loggedInUserRole,
          committeePaperDashboardStatus: this.status,
          committeePaperDashboardSubStatus: committeePaperDashboardSubStatus}, new Pagination(event)).then((data: any) =>{
              let committeePaperList;
               this.committeeTypeList.forEach(committeeType => {
                   if (committeeType.status == "ACT"){
                        committeePaperList = _.filter(data.pageData || [], committeePaper => {
                                        return committeePaper.committeeTypeID === committeeType.committeeTypeID});
                        this.committeePapers[committeeType.committeeDescription] = committeePaperList;
                        this.committeePapersPage[committeeType.committeeDescription] = data;
                   }
               });
           });
    }

    onBCCPageEvent(event, bccPaperDashboardSubStatus) {
       this.pageSize.pageSize = event.pageSize;
       this.committeePaperService.getBCCPaperByStatus({
          loggedInUserId : this.applicationService.getLoggedInUserUserName(),
          loggedInUserBranchCode : this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel : this.applicationService.getLoggedInUserUPMGroupCode(),
          loggedInUserRole: this.loggedInUserRole,
          committeePaperDashboardStatus: this.status,
          committeePaperDashboardSubStatus: bccPaperDashboardSubStatus}, new Pagination(event)).then((data: any) =>{
                let committeePaperList = data.pageData
                this.committeePapers[bccPaperDashboardSubStatus] = committeePaperList;
                this.committeePapersPage[bccPaperDashboardSubStatus] = data;
           });
    }

    loadInitialBCCDashboardPage() {
      if (this.loggedInUserRole == 'BCC_ENTERER'){
        this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.DRAFT,this.bccPaperCounts.inboxBCCPaper);
        this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.FORWARDED,this.bccPaperCounts.inboxBCCPaper);
        this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.REJECTED,this.bccPaperCounts.inboxBCCPaper);
      }

      if (this.loggedInUserRole == 'BCC_AUTHORIZER'){
         this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.PENDING,this.bccPaperCounts.inboxBCCPaper);
         this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.AUTH_PENDING_DOCS,this.bccPaperCounts.inboxBCCPaper);
      }

    /*  if (this.loggedInUserRole == 'BCC_INQUIRER'){
          this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.FORWARDED,this.bccPaperCounts.inboxBCCPaper);
      }*/

    }

    loadBCCPageData(bccPaperDashboardStatus) {
       this.committeePapers = [];
       this.committeePapersPage = [];

    /*  if (this.loggedInUserRole == 'BCC_INQUIRER'){
         switch (bccPaperDashboardStatus) {
             case this.bccPaperDashboardStatusConst.INBOX: {
               this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.FORWARDED,this.bccPaperCounts.inboxBCCPaper);
               break;
             }
             case this.bccPaperDashboardStatusConst.IN_PROGRESS: {
                this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.IN_PROGRESS, this.bccWorkFlowStatusConst.PENDING,this.bccPaperCounts.inProgressBCCPaper);
                this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.IN_PROGRESS, this.bccWorkFlowStatusConst.AUTH_PENDING_DOCS,this.bccPaperCounts.inProgressBCCPaper);
                break;
             }
             case this.bccPaperDashboardStatusConst.APPROVED: {
               //check bcc status is APPROVED
               this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.APPROVED, this.bccWorkFlowStatusConst.APPROVED,this.bccPaperCounts.approvedBCCPaper);
               break;
             }
             case this.bccPaperDashboardStatusConst.DECLINED: {
               //check bcc status is DECLINED
               this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.DECLINED, this.bccPaperDashboardStatusConst.DECLINED,this.bccPaperCounts.declinedBCCPaper);
               break;
             }
         }
      }*/

      if (this.loggedInUserRole == 'BCC_ENTERER'){
         switch (bccPaperDashboardStatus) {
             case this.bccPaperDashboardStatusConst.INBOX: {
                this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.DRAFT,this.bccPaperCounts.inboxBCCPaper);
                this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.FORWARDED,this.bccPaperCounts.inboxBCCPaper);
                this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.REJECTED,this.bccPaperCounts.inboxBCCPaper);
                break;
             }
             case this.bccPaperDashboardStatusConst.IN_PROGRESS: {
                this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.IN_PROGRESS, this.bccWorkFlowStatusConst.PENDING,this.bccPaperCounts.inProgressBCCPaper);
                this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.IN_PROGRESS, this.bccWorkFlowStatusConst.AUTH_PENDING_DOCS,this.bccPaperCounts.inProgressBCCPaper);
                break;
             }
             case this.bccPaperDashboardStatusConst.APPROVED: {
               //check bcc status is APPROVED
               this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.APPROVED, this.bccWorkFlowStatusConst.APPROVED,this.bccPaperCounts.approvedBCCPaper);
               break;
             }
             case this.bccPaperDashboardStatusConst.DECLINED: {
               //check bcc status is DECLINED
               this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.DECLINED, this.bccPaperDashboardStatusConst.DECLINED,this.bccPaperCounts.declinedBCCPaper);
               break;
             }
         }
       }

      if (this.loggedInUserRole == 'BCC_AUTHORIZER'){
           switch (bccPaperDashboardStatus) {
               case this.bccPaperDashboardStatusConst.INBOX: {
                  this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.PENDING,this.bccPaperCounts.inboxBCCPaper);
                  this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.INBOX, this.bccWorkFlowStatusConst.AUTH_PENDING_DOCS,this.bccPaperCounts.inboxBCCPaper);
                  break;
               }
               case this.bccPaperDashboardStatusConst.IN_PROGRESS: {
                 this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.IN_PROGRESS, this.bccWorkFlowStatusConst.REJECTED,this.bccPaperCounts.inProgressBCCPaper);
                 break;
               }
               case this.bccPaperDashboardStatusConst.APPROVED: {
                 //check bcc status is APPROVED
                 this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.APPROVED, this.bccWorkFlowStatusConst.APPROVED,this.bccPaperCounts.approvedBCCPaper);
                 break;
               }
               case this.bccPaperDashboardStatusConst.DECLINED: {
                 //check bcc status is DECLINED
                 this.loadBCCSubStatusData(this.bccPaperDashboardStatusConst.DECLINED, this.bccPaperDashboardStatusConst.DECLINED,this.bccPaperCounts.declinedBCCPaper);
                 break;
               }
           }
       }
       this.status = bccPaperDashboardStatus;
     }

    loadBCCSubStatusData(bccPaperDashboardStatus, bccPaperDashboardSubStatus, subStatusCount){
       if (subStatusCount != 0){
          this.committeePaperService.getBCCPaperByStatus({
            loggedInUserId : this.applicationService.getLoggedInUserUserName(),
            loggedInUserBranchCode : this.applicationService.getLoggedInUserDivCode(),
            loginUpmAccessLevel : this.applicationService.getLoggedInUserUPMGroupCode(),
            loggedInUserRole: this.loggedInUserRole,
            committeePaperDashboardStatus: bccPaperDashboardStatus,
            committeePaperDashboardSubStatus : bccPaperDashboardSubStatus
          },
          new Pagination({
              pageSize: this.pageSize.pageSize,
              pageIndex: 0
            }
          )
        ).then((data: any) =>{
            let committeePaperList = data.pageData
            this.committeePapers[bccPaperDashboardSubStatus] = committeePaperList;
            this.committeePapersPage[bccPaperDashboardSubStatus] = data;
         });

      }else{
          this.committeePapers[bccPaperDashboardSubStatus] =[];
          this.committeePapersPage[bccPaperDashboardSubStatus] = [];
      }

    }

    getBranchName(branchCode) {
      this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
      let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

      if (!isEmpty(branch)) {
        return branch.branchName + ' - ' + branch.branchCode;
      }
      return branchCode;
    }

    loadFacilityPaper(facilityPaperID) {
        this.selectedFacilityPaperID = this.urlEncodeService.encode(facilityPaperID);
        this.router.navigate(['/facility-paper/edit']);
    }

    ngOnDestroy(): void {
    }



}

