import {Injectable} from '@angular/core';
import {SETTINGS} from "../../setting/commons.settings";
import {DataService} from "./data.service";
import {Subject} from "rxjs";
import { tryCatch } from 'rxjs/internal-compatibility';
import { ApplicationService } from '../application/application.service';

@Injectable()
export class CasDocumentStorageService {

  onDocumentStorageChange = new Subject();
  onViewDocumentStorageChange = new Subject();


  constructor(private dataService: DataService,
    private applicationService: ApplicationService,) {
  }

  downloadDocument(docStorage) {
    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + '/' + docStorage.docStorageID;
      this.dataService.get(data)
        .subscribe((response: any) => {
          // this.onDocumentStorageChange.next({
          //   data: response,
          //   fileName: docStorage.fileName
          // });
          resolve(response);
        }, reject)
    });
  }

  viewDocument(docStorage) {

    return new Promise((resolve, reject) => {
      const data = Object.assign({}, SETTINGS.ENDPOINTS.downloadDocument);
      data.url = data.url + '/' + docStorage.docStorageID;

      this.dataService.get(data)
        .subscribe((response: any) => {
          // this.onViewDocumentStorageChange.next({
          //   data: response,
          //   fileName: docStorage.fileName
          // });
          resolve(response);
        }, reject)

    });
  }
}
