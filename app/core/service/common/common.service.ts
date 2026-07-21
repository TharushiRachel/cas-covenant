import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  private loading = new Subject<any>();
  private showLoadingCount = 0;
  private hideLoadingCount = 0;

  onError: Subject<any> = new Subject();

  constructor() {
  }

  public showLoading() {
    this.showLoadingCount++;
    this.loading.next({status: true});
  }

  public hideLoading() {
    this.hideLoadingCount++;
    if (this.showLoadingCount <= this.hideLoadingCount) {
      this.showLoadingCount = 0;
      this.hideLoadingCount = 0;

      this.loading.next({status: false});
    }
  }

  public resetLoading() {
    this.showLoadingCount = 0;
    this.hideLoadingCount = 0;
    this.loading.next({status: false});
  }

  public getLoadingStatus(): Observable<any> {
    return this.loading.asObservable();
  }

  public onApiError() {
    this.onError.next(true);
  }

}
