import {Injectable} from '@angular/core';

@Injectable()
export class CommentCacheService {

  commentDataCache: any = {};

  constructor() {
  }

  expireCommentCache() {
    this.commentDataCache = {};
  }

  expireCommentCacheData(key: string) {
    this.commentDataCache[key] = null;
  }

  cacheComment(key: string, data: any) {
    this.commentDataCache[key] = data;
  }

  getCommentFromCache(key: string) {
    return this.commentDataCache[key];
  }
}
