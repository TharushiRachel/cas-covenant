import {Injectable} from '@angular/core';
import * as moment from 'moment';
import {Moment} from 'moment';

@Injectable()
export class DateService {

  private defaultDateFormat = 'DD/MM/YYYY';
  private defaultDateTimeFormat = 'DD/MM/YYYY HH:mm';

  constructor() {
  }

  getDefaultFormattedDate(date: Moment): string {
    return moment(date).format(this.defaultDateFormat);
  }

  getDefaultFormattedDateTime(date: Moment): string {
    return moment(date).format(this.defaultDateTimeFormat);
  }

  getMomentDateFromDateStr(dateStr: string) {
    return moment(dateStr, this.defaultDateFormat);
  }

  getMomentDateTimeFromDateStr(dateStr: string) {
    return moment(dateStr, this.defaultDateTimeFormat);
  }

  getMonthStartDateStr(date: Moment): string {
    return moment(date).startOf('month').format(this.defaultDateFormat);
  }

  getMonthEndDateStr(date: Moment): string {
    return moment(date).endOf('month').format(this.defaultDateFormat);
  }

  getJsDate(date: string) {
    return this.getMomentDateFromDateStr(date).toDate();
  }

  getJSDateDifference(date1: Date, date2: Date) {
    return moment(date2).diff(moment(date1), 'days');
  }

  getMomentDateFromJSDate(date: Date) {
    return moment(date);
  }

  getNow(): Moment {
    return moment();
  }

  isSameOrBefore(fromDate: string, toDate: string) {
    return this.getMomentDateFromDateStr(fromDate).isSameOrBefore(this.getMomentDateFromDateStr(toDate));
  }

  isSameOrBeforeDateTime(fromDate: string, toDate: string) {
    return this.getMomentDateTimeFromDateStr(fromDate).isSameOrBefore(this.getMomentDateTimeFromDateStr(toDate));
  }

  isSame(fromDate: string, toDate: string) {
    return this.getMomentDateFromDateStr(fromDate).isSame(this.getMomentDateFromDateStr(toDate));
  }

  getDateDifference(dateString, dateDiffUnit) {
    return this.getNow().diff(this.getMomentDateFromDateStr(dateString), dateDiffUnit);
  }

}
