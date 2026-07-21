import {Injectable} from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable()
export class EncryptionService {

  private secretKey = 'EoRTD3<Z%`+';

  constructor() {
  }

  encrypt(field): string {
    return CryptoJS.AES.encrypt(field, this.secretKey).toString();
  }

  decrypt(cypherStr: string): string {
    let bytes = CryptoJS.AES.decrypt(cypherStr.toString(), this.secretKey);
    return bytes.toString(CryptoJS.enc.Utf8);
  }
}
