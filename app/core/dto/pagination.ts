export class Pagination {

  pageIndex: number;
  pageSize: number;

  constructor(pagination?) {
    pagination = pagination || {};
    this.pageSize = pagination.pageSize || 10;
    this.pageIndex = (pagination.pageIndex + 1) || 1;
  }

  getPageData() {
    return {
      page: this.pageIndex,
      rows: this.pageSize
    };
  }
}
