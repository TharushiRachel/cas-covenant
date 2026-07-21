import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { AddEditWalletShareComponent } from "./add-edit-wallet-share/add-edit-wallet-share.component";
import { CurrencyPipe } from "@angular/common";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Constants } from "src/app/core/setting/constants";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { AppUtils } from "src/app/shared/app.utils";
import { AlertService } from "src/app/core/service/common/alert.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import * as _ from "lodash";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";

@Component({
  selector: "app-fp-wallet-share",
  templateUrl: "./fp-wallet-share.component.html",
  styleUrls: ["./fp-wallet-share.component.scss"],
})
export class FpWalletShareComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper: any;

  dataList: any[] = [];
  prevDataList: any[] = [];
  limitTotal: any = 0;
  osTotal: any = 0;
  isDataEdited: boolean = false;
  modalRef: MDBModalRef;

  masterDataPrivilege = SETTINGS.PRIVILEGES;

  onWalletShareChangeSub = new Subscription();

  constructor(
    public currencyPipe: CurrencyPipe,
    private readonly mdbModalService: MDBModalService,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly alertService: AlertService,
    private readonly applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.getWalletShares();
  }

  ngOnDestroy(): void {
    this.onWalletShareChangeSub.unsubscribe();
  }

  getWalletShares() {
    if (this.facilityPaper && this.facilityPaper.facilityPaperID) {
      this.facilityPaperAddEditService
        .getWalletShare(this.facilityPaper.facilityPaperID)
        .then((res: any[]) => {
          if (res !== null && res.length > 0) {
            this.handleTableData(res);
          }

          this.setFPFacilities();
        });
    }
  }

  handleTableData(res: any[]) {
    this.dataList = this.prepareDataList(res);
    this.prevDataList = this.prepareDataList(res);
    this.calculateSubTotal();
    this.isDataEdited = false;
  }

  setFPFacilities() {
    let facilities: any[] = [];
    let commonSecurities: any[] = [];

    this.facilityPaper.facilityDTOList.forEach((facility: any) => {
      let fcSecurities: any[] = facility.facilitySecurityDTOList
        ? facility.facilitySecurityDTOList
        : [];

      let facCommonSecurities: any[] = fcSecurities
        .filter((cs: any) => cs.isCommonSecurity === Constants.yesNoConst.Y)
        .map((s: any) => s.securityDetail);

      if (commonSecurities.length > 0) {
        commonSecurities.concat(facCommonSecurities);
      } else {
        commonSecurities = facCommonSecurities;
      }

      let item: any = {
        facilityId: 0,
        facility: facility.creditFacilityTemplateDTO
          ? facility.creditFacilityTemplateDTO.description
          : "-",
        facilityCurrency: facility.facilityCurrency
          ? facility.facilityCurrency
          : "LKR",
        limitAmount: facility.facilityAmount
          ? AppUtils.getMillionValue(parseFloat(facility.facilityAmount))
          : 0,
        osAmount: facility.outstandingAmount
          ? AppUtils.getMillionValue(parseFloat(facility.outstandingAmount))
          : 0,
        recordStatus: Constants.recordStatusConst.NEW,
        facilitySecurities: fcSecurities
          .filter((cs: any) => cs.isCommonSecurity === Constants.yesNoConst.N)
          .map((s: any) => ({
            securityId: 0,
            facilityId: 0,
            walletShareId: 0,
            isCommonSecurity: Constants.yesNoConst.N,
            securityDetail: s.securityDetail,
            recordStatus: Constants.recordStatusConst.NEW,
          })),
      };

      facilities.push(item);
    });

    let limitTotal: any = this.facilityPaper.totalExposureNew
      ? AppUtils.getMillionValue(
          this.getFloat(this.facilityPaper.totalExposureNew)
        )
      : 0.0;
    let osTotal: any = this.facilityPaper.totalExposurePrevious
      ? AppUtils.getMillionValue(
          this.getFloat(this.facilityPaper.totalExposurePrevious)
        )
      : 0.0;

    let fpShare: any = {
      walletShareId: 0,
      bank: "Sampath Bank (with proposed facilities)",
      facilities: facilities,
      sortData: this.sortDataList(facilities),
      commonSecurities: commonSecurities.map((s: any) => ({
        securityId: 0,
        facilityId: 0,
        walletShareId: 0,
        isCommonSecurity: Constants.yesNoConst.Y,
        securityDetail: s,
        recordStatus: Constants.recordStatusConst.NEW,
      })),
      rows: facilities.length + 1,
      share: 0,
      limitTotal: limitTotal,
      osTotal: osTotal,
      isSystem: 1,
      recordStatus: Constants.recordStatusConst.NEW,
    };

    this.dataList = this.dataList.map((d: any) =>
      d.bank === fpShare.bank
        ? { ...d, recordStatus: Constants.recordStatusConst.DELETE }
        : d
    );
    this.prevDataList = this.prevDataList.map((d: any) =>
      d.bank === fpShare.bank
        ? { ...d, recordStatus: Constants.recordStatusConst.DELETE }
        : d
    );

    this.dataList.push(fpShare);
    this.prevDataList.push(fpShare);
    this.calculateSubTotal();
    this.calculateShare();

    this.compareFacilitiesWithWallet(
      facilities,
      commonSecurities,
      this.dataList
    );
  }

  compareFacilitiesWithWallet(
    facilities: any[],
    securities: any[],
    walletShares: any[]
  ) {
    let fpFacilities: any[] = facilities.map((facility: any) => ({
      facility: facility.facility,
      limitAmount: facility.limitAmount,
      osAmount: facility.osAmount,
      facilitySecurities: facility.facilitySecurities.map(
        (s: any) => s.securityDetail
      ),
    }));

    let fpShare: any = walletShares.find((ws: any) => ws.isSystem == 1);

    let walletFPFacilities: any[] =
      fpShare && fpShare.facilities !== null
        ? fpShare.facilities.map((ws: any) => ({
            facility: ws.facility,
            limitAmount: ws.limitAmount,
            osAmount: ws.osAmount,
            facilitySecurities: ws.facilitySecurities.map(
              (s: any) => s.securityDetail
            ),
          }))
        : [];

    let walletShareSecurity: any = fpShare
      ? fpShare.commonSecurities.map((s: any) => s.securityDetail)
      : [];

    let checkFacilities: boolean = _.isEqual(
      JSON.stringify(fpFacilities),
      JSON.stringify(walletFPFacilities)
    );

    let checkSecurities: boolean = _.isEqual(
      JSON.stringify(walletShareSecurity),
      JSON.stringify(securities)
    );

    if (
      (!checkFacilities || !checkSecurities) &&
      this.dataList.filter(
        (d: any) =>
          d.isSystem === 0 &&
          d.recordStatus !== Constants.recordStatusConst.DELETE
      ).length > 0
    ) {
      this.submit();
    }
  }

  prepareDataList(data: any[]) {
    return data
      .map((d: any) => ({
        ...d,
        recordStatus: Constants.recordStatusConst.SUBMITTED,
        facilities: d.facilities
          ? d.facilities.map((df: any) => ({
              ...df,
              facilitySecurities: df.facilitySecurities
                ? df.facilitySecurities.map((fsec: any) => ({
                    ...fsec,
                    recordStatus: Constants.recordStatusConst.SUBMITTED,
                  }))
                : [],
              recordStatus: Constants.recordStatusConst.SUBMITTED,
            }))
          : [],
        sortData: this.sortDataList(d.facilities),
        rows: d.facilities.length + 1,
        isSystem: d.isSystem ? d.isSystem : 0,
        share: this.getFloat(d.share),
        limitTotal: this.getFloat(d.limitTotal),
        osTotal: this.getFloat(d.osTotal),
        commonSecurities: d.commonSecurities
          ? d.commonSecurities.map((csec: any) => ({
              ...csec,
              recordStatus: Constants.recordStatusConst.SUBMITTED,
            }))
          : [],
      }))
      .sort((a: any, b: any) => a.isSystem - b.isSystem);
  }

  getFormattedAmount(amount: any) {
    return this.currencyPipe.transform(amount ? amount : 0, "", "", "1.3-3");
  }

  getFormattedShare(amount: any) {
    return this.currencyPipe.transform(amount ? amount : 0, "", "", "1.2-2");
  }

  addEditWalletShare(item?: any) {
    this.modalRef = this.mdbModalService.show(AddEditWalletShareComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-75-p modal-dialog-scrollable",
      containerClass: "right",
      animated: false,
      data: {
        heading: "Add/Edit Wallet Share",
        content: {},
        facilityPaper: this.facilityPaper,
        selectedItem: item ? item : null,
        prevDataList: this.dataList,
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        let facilities: any[] = this.filterRemoveData(result.facilities).map(
          (f: any) => ({
            ...f,
            facilityCurrency: "LKR",
          })
        );

        let facilityLength: number = facilities.length + 1;

        let item: any = {
          ...result,
          sortData: this.sortDataList(facilities),
          rows: facilityLength,
          share: 0,
          limitTotal: this.calculateLimitTotal(facilities),
          osTotal: this.calculateOSTotal(facilities),
          isSystem: 0,
        };

        if (
          this.dataList.some(
            (d: any) => d.walletShareId == result.walletShareId
          )
        ) {
          this.dataList = this.dataList.map((d: any) =>
            d.walletShareId == result.walletShareId
              ? {
                  ...d,
                  bank: result.bank,
                  facilities: result.facilities.map((f: any) => ({
                    ...f,
                    facilityCurrency: "LKR",
                  })),
                  security: result.security,
                  sortData: this.sortDataList(facilities),
                  rows: facilityLength,
                  share: 0,
                  limitTotal: this.calculateLimitTotal(facilities),
                  osTotal: this.calculateOSTotal(facilities),
                  commonSecurities: result.commonSecurities,
                }
              : d
          );
        } else {
          let insertIndex: number =
            this.dataList.length >= 1 ? this.dataList.length - 1 : 0;
          this.dataList.splice(insertIndex, 0, item);
        }
        this.calculateSubTotal();
        this.calculateShare();

        this.isDataEdited = true;
      }
    });
  }

  sortDataList(data: any): any[] {
    return data.filter((d: any, i: number) => i != 0);
  }

  calculateLimitTotal(facilities: any[]) {
    let result: any = 0;

    facilities.forEach((element: any) => {
      if (this.getFloat(element.limitAmount)) {
        result = result + this.getFloat(element.limitAmount);
      }
    });

    return result;
  }

  calculateOSTotal(facilities: any[]) {
    let result: any = 0;
    facilities.forEach((element: any) => {
      if (this.getFloat(element.osAmount)) {
        result = result + this.getFloat(element.osAmount);
      }
    });

    return result;
  }

  calculateShare() {
    this.dataList
      .filter((d: any) => d.recordStatus !== Constants.recordStatusConst.DELETE)
      .forEach((element: any) => {
        let potion: any =
          (this.getFloat(element.limitTotal) / this.getFloat(this.limitTotal)) *
          100;
        element.share = this.getFloat(potion).toFixed(2);
      });

    this.detectShareChange();
  }

  detectShareChange() {
    this.dataList.forEach((element: any) => {
      let prevData: any = this.prevDataList.find(
        (d: any) => d.walletShareId == element.walletShareId
      );
      if (
        prevData &&
        this.getFloat(element.share) !== this.getFloat(prevData.share)
      ) {
        element.recordStatus = Constants.recordStatusConst.UPDATE;
      }
    });
  }

  calculateSubTotal() {
    this.limitTotal = 0;
    this.osTotal = 0;
    this.dataList
      .filter((d: any) => d.recordStatus !== Constants.recordStatusConst.DELETE)
      .forEach((element: any) => {
        this.limitTotal =
          this.getFloat(this.limitTotal) + this.getFloat(element.limitTotal);

        this.osTotal =
          this.getFloat(this.osTotal) + this.getFloat(element.osTotal);
      });
  }

  getFloat(value: any) {
    return parseFloat(value) ? parseFloat(value) : 0.0;
  }

  removeData(item: any) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      containerClass: "right",
      animated: false,
      data: {
        heading: "Remove Wallet Share",
        message: `Do you want remove ${item.bank} from wallet share?`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        if (item.recordStatus === Constants.recordStatusConst.NEW) {
          this.dataList = this.dataList.filter(
            (d: any) => d.walletShareId !== item.walletShareId
          );
        } else {
          this.dataList = this.dataList.map((d: any) =>
            d.walletShareId === item.walletShareId
              ? {
                  ...d,
                  recordStatus: Constants.recordStatusConst.DELETE,
                }
              : d
          );
        }
        this.calculateSubTotal();
        this.calculateShare();

        this.isDataEdited = true;
      }
    });
  }

  filterRemoveData(data: any[]) {
    return data.filter(
      (d: any) => d.recordStatus !== Constants.recordStatusConst.DELETE
    );
  }

  submit() {
    if (
      this.dataList.filter(
        (d: any) =>
          d.isSystem === 0 &&
          d.recordStatus !== Constants.recordStatusConst.DELETE
      ).length > 0
    ) {
      let request: any = {
        facilityPaperId: this.facilityPaper.facilityPaperID,
        walletShares: this.dataList.map((d: any) => ({
          walletShareId:
            d.recordStatus !== Constants.recordStatusConst.NEW
              ? d.walletShareId
              : 0,
          bank: d.bank,
          share: this.getFloat(d.share),
          recordStatus:
            d.facilities.some(
              (df: any) =>
                df.recordStatus !== Constants.recordStatusConst.SUBMITTED
            ) ||
            d.commonSecurities.some(
              (df: any) =>
                df.recordStatus !== Constants.recordStatusConst.SUBMITTED
            )
              ? Constants.recordStatusConst.UPDATE
              : d.recordStatus,
          facilities: d.facilities.map((f: any) => ({
            ...f,
            facilityId:
              f.recordStatus !== Constants.recordStatusConst.NEW
                ? f.facilityId
                : 0,
            limitAmount: this.getFloat(f.limitAmount),
            osAmount: this.getFloat(f.osAmount),
          })),
          commonSecurities: d.commonSecurities,
          facilityPaperId: d.facilityPaperId,
          isSystem: d.isSystem,
          limitTotal: d.limitTotal,
          osTotal: d.osTotal,
        })),
      };

      this.facilityPaperAddEditService
        .saveWalletShare(request)
        .then((res: any[]) => {
          if (res !== null && res.length > 0) {
            this.handleTableData(res);
          }
        });
    } else {
      this.alertService.showToaster(
        "Bank(s) shares are required.",
        SETTINGS.TOASTER_MESSAGES.error
      );
    }
  }

  isSaveDisabled() {
    return JSON.stringify(this.dataList) === JSON.stringify(this.prevDataList);
  }

  getWalletShareSecurity(share: any) {
    let securitiesTxt: string = "";

    if (share.facilities && share.facilities.length > 0) {
      share.facilities.forEach((element: any, i: number) => {
        let securities: any[] = element.facilitySecurities
          .filter(
            (s: any) => s.recordStatus !== Constants.recordStatusConst.DELETE
          )
          .map((fs: any) => fs.securityDetail);
        if (securities.length > 0) {
          securitiesTxt =
            securitiesTxt +
            `Facility ${i + 1}: \n${securities.join("\n")} \n\n`;
        }
      });
    }
    if (share.commonSecurities && share.commonSecurities.length > 0) {
      if (share.isSystem === 1) {
        share.commonSecurities.forEach((element: any, i: number) => {
          if (element.securityDetail) {
            securitiesTxt =
              securitiesTxt +
              `Common Security ${i + 1}: \n${element.securityDetail} \n\n`;
          }
        });
      } else {
        share.commonSecurities.forEach((element: any, i: number) => {
          if (element.securityDetail) {
            securitiesTxt = securitiesTxt + `\n${element.securityDetail} \n\n`;
          }
        });
      }
    }

    return securitiesTxt !== "" ? securitiesTxt : "-";
  }

  isActionEnabled() {
    return this.isAllowedPaper() && this.isAllowedUser();
  }

  isAllowedPaper() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.REJECTED
    );
  }

  isAllowedUser() {
    return (
      this.facilityPaper.currentAssignUser ===
      this.applicationService.getLoggedInUserUserName()
    );
  }

  getCurrency(value: string) {
    return value !== "LKR" ? value : "";
  }
}
