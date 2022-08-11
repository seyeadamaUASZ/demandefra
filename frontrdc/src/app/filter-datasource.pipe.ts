import { Pipe, PipeTransform } from '@angular/core';
import { MatTableDataSource } from '@angular/material';

@Pipe({
  name: 'filterDatasource'
})
export class FilterDatasourcePipe implements PipeTransform {

  transform(items: any[], searchText: string): any {
    if (!items) {
      return [];
    }
    if (!searchText) {
      return items;
    }
    searchText = searchText.toLocaleLowerCase();

    return items.filter(it => {
      // if(typeof it == Object){

      // }else{
      //   return it.toLocaleLowerCase().includes(searchText);
      // }
    });
  }

}
