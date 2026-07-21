import * as _ from 'lodash';
import {FileUploadError} from "../dto/file-upload-error";
import {SETTINGS} from "../../core/setting/commons.settings";

export class FileValidator {

  static allowedExtensions = SETTINGS.UPLOAD_IMAGE_ALLOWED_EXTENSIONS;
  static sizeLimitsMB = SETTINGS.UPLOAD_IMAGE_SIZE_LIMITS_MB;
  static defaultSizeMB = SETTINGS.UPLOAD_IMAGE_DEFAULT_SIZE_MB;

  public static isValidFile(file: File): FileUploadError {
    let error = new FileUploadError();

    if (!file) {
      error.errorMessage = `Empty Document`;
      error.hasError = true;
      return error;
    }

    let fileName = file.name;

    if (!fileName) {
      error.errorMessage = `Invalid Document`;
      error.hasError = true;
      return error;
    }

    let fileExtension = fileName.split('.').pop();

    if (!fileExtension || _.indexOf(this.allowedExtensions, fileExtension) === -1) {
      error.errorMessage = `File type ${fileExtension} is not allowed`;
      error.hasError = true;
      return error;
    }

    const maxSize = this.sizeLimitsMB[fileExtension] || this.defaultSizeMB;

    if (((file.size) / (1024 * 1024)) > maxSize) {
      error.errorMessage = `Maximum allowed file size for .${fileExtension} is ${maxSize}MB`;
      error.hasError = true;
      return error;
    }

    return error;
  }

  public static isValidFileCustom(file: File, allowedExtensions, maximumFileSizeMB) {
    let error = new FileUploadError();

    let fileName = file.name;
    let fileExtension = fileName.split('.').pop();

    if (_.indexOf(allowedExtensions, fileExtension.toLowerCase()) === -1) {
      error.errorMessage = `File type ${fileExtension} is not allowed`;
      error.hasError = true;
      return error;
    }

    if (((file.size) / (1024 * 1024)) > maximumFileSizeMB) {
      error.errorMessage = `Maximum allowed file size is ${maximumFileSizeMB}MB`;
      error.hasError = true;
      return error;
    }

    return error;
  }
}
