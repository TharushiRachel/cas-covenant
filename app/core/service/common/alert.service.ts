import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor(private toastrService: ToastrService) {
  }

  showToaster(message: string, type: string, customConfigs?: { timeOut?: number, panelClass?: string }) {

    let title;
    const config: any = Object.assign({}, {
        duration: 5000,
        closeButton: true,
        enableHtml: true,
        horizontalPosition: 'right'
      },
      customConfigs);

    if (type.toUpperCase() === 'SUCCESS') {
      title = 'Success';
      this.toastrService.success(message, title, config);
    }
    if (type.toUpperCase() === 'ERROR') {
      title = 'Error';
      this.toastrService.error(message, title, config);
    }
    if (type.toUpperCase() === 'WARNING') {
      title = 'Warning';
      this.toastrService.warning(message, title, config);
    }
    if (type.toUpperCase() === 'INFO') {
      title = 'Info';
      this.toastrService.info(message, title, config);
    }
    if (type.toUpperCase() === 'CUSTOM') {
      title = 'Custom';
      this.toastrService.info(message, title, config);
    }
  }
}
