import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'calculateTotal'
})
export class CalculateTotalPipe implements PipeTransform {

  transform(dataArray: any[], column: string): number {
    
    if (!dataArray || dataArray.length === 0) {

      return 0;
    }

    return dataArray.reduce((total, item) => Number(total) + Number(item[column]), 0);
  }

}
