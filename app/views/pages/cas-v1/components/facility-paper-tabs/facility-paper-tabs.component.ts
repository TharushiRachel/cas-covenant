import { Component, Input, OnInit } from "@angular/core";
import * as _ from "lodash";
import { CurrencyService } from "src/app/core/service/common/currency.service";
import { CasV1Service } from "../../services/cas-v1.service";

@Component({
  selector: "app-facility-paper-tabs",
  templateUrl: "./facility-paper-tabs.component.html",
  styleUrls: ["./facility-paper-tabs.component.scss"],
})
export class FacilityPaperTabsComponent implements OnInit {
  combinedData: any;
  upcFormatNum: string = "No Records Found";

  tabs: any[] = [
    { index: 1, title: "Facility" },
    { index: 2, title: "Security" },
    { index: 3, title: "UPC Comments" },
    { index: 4, title: "Comments" },
    { index: 5, title: "Full Paper" },
  ];
  currentIndex: number = 1;

  @Input("facilityPaper") facilityPaper: any = null;

  facilities: any[] = [];
  basicData: any = null;

  sectorList: any[] = [];
  subSectorList: any[] = [];
  purposeOfAdvancedList: any[] = [];
  purposeOfAdvancedMap: any = {};

  tabLoadingSetting: any = {
    isUPCLoaded: false,
    isCmntLoaded: false,
  };
  tabLoadedData: any = {
    commentsDetails: [],
    upc: [],
  };
  constructor(
    private readonly casV1Service: CasV1Service,
    private readonly currencyService: CurrencyService
  ) { }

  ngOnInit() {
    if (this.facilityPaper && this.facilityPaper.facilities) {
      Promise.all([
        this.casV1Service.getAllSectorData().then((res: any) => {
          this.sectorList = res || [];
        }),

        this.casV1Service.getAllSubSectorData().then((res: any) => {
          this.subSectorList =
            res && res.subSectorDTOList ? res.subSectorDTOList : [];
        }),

        this.casV1Service.getAllPurposeOfAdvanced().then((res: any) => {
          this.purposeOfAdvancedList = res || [];
          _.forEach(this.purposeOfAdvancedList, (pusposeOfAdvanced) => {
            this.purposeOfAdvancedMap[pusposeOfAdvanced.referenceCode] =
              pusposeOfAdvanced.referenceCode +
              " - " +
              pusposeOfAdvanced.referenceDescription;
          });
        }),
      ]).then(() => {
        this.prepareFacilitiesAndSecurities();
      });
    }

    this.basicData =
      this.facilityPaper && this.facilityPaper.basicData
        ? this.facilityPaper.basicData
        : null;

    if (this.basicData) {
      this.upcFormatNum =
        this.basicData.upcFormatNum && this.basicData.upcFormatNum !== "No Records Found"
          ? this.basicData.upcFormatNum
          : null;


      this.combinedData = {
        refNo: this.basicData.refNo,
        selectedDate: this.basicData.date,
        upcFormatNum: this.upcFormatNum,
      };
    }
  }

  prepareFacilitiesAndSecurities() {
    let facilityTexts: any[] = this.facilityPaper.facilityTexts
      ? this.facilityPaper.facilityTexts
      : [];

    let securityTexts: any[] = this.facilityPaper.securityTexts
      ? this.facilityPaper.securityTexts
      : [];

    this.facilities = this.facilityPaper.facilities.sort((a:any,b:any) => a.facilityID - b.facilityID).map(
      (f: any, i: number) => ({
        value: f.value ? this.getFormattedAmount(parseFloat(f.value)) : "0.00",
        preValue: f.preValue
          ? this.getFormattedAmount(parseFloat(f.preValue))
          : "0.00",
        currency: f.currency,
        facilityName: f.facilityName,
        sightUsance: f.sightUsance,
        facilityTexts: this.prepareFacilityTexts(facilityTexts, i),
        securityTexts: this.prepareSecurityTexts(securityTexts, i),
        sector: f.sectorRef ? this.getSectorDescription(f.sectorRef) : "",
        subSector: f.subSectorRef
          ? this.getSubSectorDescription(f.subSectorRef)
          : "",
        purpose: f.prpsAdvncRef
          ? this.getPurposeDescription(f.prpsAdvncRef)
          : "",
      })
    );
  }

  prepareFacilityTexts(data: any[], index: number) {
    return data
      .filter((ft: string) => parseInt(ft.split("|")[0]) === index + 1)
      .map((ft: string, x: number) => {
        let splitTexts: string = ft.split("|")[1];

        let lable: string = splitTexts.split("~~")[0];
        let value: string = splitTexts.split("~~").slice(1).join(" ");
        return {
          lable: lable,
          value:
            value && value.replace(/\s/g, "").toLowerCase() !== "null"
              ? value
              : "",
        };
      });
  }

  prepareSecurityTexts(data: any[], index: number) {
    return data
      .filter((ft: string) => parseInt(ft.split("|")[0]) === index + 1)
      .map((ft: string, x: number) => {
        let splitTexts: string = ft.split("|")[1];
        let startsWith: string = (x + 1).toString() + ". ~~ ";
        return splitTexts.startsWith(startsWith)
          ? (x + 1).toString() + "." + splitTexts.split("~~").slice(1).join(" ")
          : splitTexts.replace("~~", " ");
      });
  }

  getFormattedAmount(amount: any) {
    if (amount) {
      return this.currencyService.getFormattedAmount(amount);
    }
    return "0.00";
  }

  getSectorDescription(code: any) {
    let referenceDescription = "";

    if (this.sectorList.length > 0 && code) {
      this.sectorList.forEach((sector) => {
        if (sector.referenceCode == code) {
          return (referenceDescription = sector.referenceDescription);
        }
      });
    }

    return referenceDescription;
  }

  getSubSectorDescription(longRefCode: any) {
    let referenceDescription = "";

    if (this.subSectorList.length > 0) {
      this.subSectorList.forEach((subSector) => {
        if (subSector.longRefCode == longRefCode) {
          return (referenceDescription = subSector.referenceDescription);
        }
      });
    }

    return referenceDescription;
  }

  getPurposeDescription(code: any) {
    let referenceDescription = "";

    if (this.purposeOfAdvancedList.length > 0) {
      this.purposeOfAdvancedList.forEach((purpose) => {
        if (purpose.referenceCode == code) {
          return (referenceDescription = purpose.referenceDescription);
        }
      });
    }

    return referenceDescription;
  }

  handleTabSelect(index: number) {
    this.currentIndex = index;
  }

  handleTabLoadingSetting(type: string) {
    this.tabLoadingSetting = {
      isUPCLoaded: type == "UPC" ? true : this.tabLoadingSetting.isUPCLoaded,
      isCmntLoaded: type == "CMNT" ? true : this.tabLoadingSetting.isCmntLoaded,
    };
  }

  setComments(data: any[]) {
    this.tabLoadedData = {
      ...this.tabLoadedData,
      commentsDetails: data,
    };
  }

  setUpcData(data: any[]) {
    this.tabLoadedData = {
      ...this.tabLoadedData,
      upc: data,
    };
  }

  public clearTabData() {
    this.tabLoadingSetting = {
      isUPCLoaded: false,
      isCmntLoaded: false,
    };
    this.tabLoadedData = {
      commentsDetails: [],
      upc: [],
    };
  }
}
