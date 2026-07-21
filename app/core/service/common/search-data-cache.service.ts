import {Injectable} from '@angular/core';
import * as _ from 'lodash';
import {PageSize} from '../../dto/page.size';

export class CacheSearchData {
  pageSize: PageSize;
  searchData: any;
  extra?: any;
}

@Injectable()
export class SearchDataCacheService {

  private searchDataCache: any = {};

  constructor() {
  }

  hasSearchData(key: string): boolean {
    return !_.isEmpty(this.searchDataCache[key]);
  }

  setSearchData(key: string, data: CacheSearchData) {
    this.searchDataCache[key] = data;
  }

  getSearchData(key: string): CacheSearchData {
    return this.searchDataCache[key];
  }

  resetSearchDataCache() {
    this.searchDataCache = {};
  }

}
