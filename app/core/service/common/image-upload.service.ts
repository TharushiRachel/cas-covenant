import {Injectable} from '@angular/core';
import {DataService} from "../data/data.service";
import {SETTINGS} from "../../setting/commons.settings";
import {Subject} from "rxjs";

export interface ImageUploadResponse {
	imageUrl: string,
	helperInfo: any
}

@Injectable()
export class ImageUploadService {

	onImageUploadCompleted = new Subject<ImageUploadResponse>();

	constructor(private dataService: DataService) {
	}

	uploadImageCommon(imageData, helperInfo?): Promise<any> {
		return new Promise((resolve, reject) => {
			this.dataService.post(
				SETTINGS.ENDPOINTS.uploadImageCommon, imageData).subscribe((response: any) => {
				this.onImageUploadCompleted.next({
					imageUrl: response,
					helperInfo: helperInfo
				});
				resolve(response);
			}, reject);
		});
	}

}
