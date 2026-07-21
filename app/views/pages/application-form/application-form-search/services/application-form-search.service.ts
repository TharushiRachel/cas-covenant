import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { Pagination } from 'src/app/core/dto/pagination';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { SearchDataCacheService } from 'src/app/core/service/common/search-data-cache.service';
import { DataService } from 'src/app/core/service/data/data.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';

@Injectable()
export class ApplicationFormSearchService implements Resolve<any> {

  uniquePageName = 'BranchApplicationFormComponent-#aardw';
  applicationForms: any = [];
  onSearchApplicationFormChange: BehaviorSubject<any> = new BehaviorSubject({});


  constructor(private dataService: DataService,
              private searchDataCacheService: SearchDataCacheService,
              public applicationService: ApplicationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {

      Promise.all([
        this.getApplicationForms()
      ]).then(
        () => {
          resolve();
        },
        reject
      );
    });
  }

  getApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {
    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      return new Promise((resolve, reject) => {
        resolve({});
      });
    }
    return this.searchApplicationForms(searchData, paginationData);
  }

  searchApplicationForms(searchData?, paginationData?: Pagination): Promise<any> {
    
      return new Promise((resolve, reject) => {
        this.dataService.postWithPageData(
          SETTINGS.ENDPOINTS.getPagedSearchApplicationForm, searchData, paginationData).subscribe((response: any) => {
          this.applicationForms = response.pageData;
          this.onSearchApplicationFormChange.next(response);
          resolve(response);
        }, reject);
        
      });
          
  }
  
}
