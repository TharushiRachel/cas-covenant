import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'calculateAverage'
})
export class CalculateAveragePipe implements PipeTransform {

  transform(dataArray: any[], columnName: string): number {
    if (!dataArray || dataArray.length === 0) {
      return 0;
    }

    const sum = dataArray.reduce((total, data) => Number(total) + Number(data[columnName]), 0);
    return sum / dataArray.length;
  }


}
