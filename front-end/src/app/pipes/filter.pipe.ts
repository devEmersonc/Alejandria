import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(value: any, arg: any): any {
    const resultBooks = [];
    for(const book of value){
      let fileName: any = book.fileName;
      let category:any = book.category;
      let author:any = book.author;
      if(fileName.normalize("NFD").replace(/[\u0300-\u036f]/g, '').toLowerCase().indexOf(arg.toLowerCase()) > -1 || category.normalize("NFD").replace(/[\u0300-\u036f]/g, '').toLowerCase().indexOf(arg.toLowerCase()) > -1 || author.normalize("NFD").replace(/[\u0300-\u036f]/g, '').toLowerCase().indexOf(arg.toLowerCase()) > -1){
        resultBooks.push(book);
      };
    };
    return resultBooks;
  }

}
