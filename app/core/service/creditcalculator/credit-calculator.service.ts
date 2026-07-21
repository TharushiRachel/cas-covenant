import {Injectable} from '@angular/core';
import {SETTINGS} from "../../setting/commons.settings";
import {DataService} from "../data/data.service";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CreditCalculatorService {

  creditCalculatorResponse = new BehaviorSubject({});

  constructor(
    private dataService: DataService
  ) {
  }

  getCreditCalculatorData(data) {
    this.dataService.post(SETTINGS.ENDPOINTS.getCreditCalculatorData, data)
      .subscribe((response: any) => {
        this.creditCalculatorResponse.next(response);
        //console.log(response)
      })
  }
}
