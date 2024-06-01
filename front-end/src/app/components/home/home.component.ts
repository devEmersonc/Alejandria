import { Component, OnInit } from '@angular/core';
import { Pdf } from 'src/app/models/Pdf';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/authService/auth.service';
import { FileService } from 'src/app/services/fileService/file.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  books:Pdf[] = [];
  filterBooks:string = '';

  constructor(private fileService: FileService){}

  ngOnInit(): void {
    this.getAllBooks();
  }  

  getAllBooks(){
    this.fileService.getAllBooks().subscribe(books => {
      this.books = books;
    })
  }

  downloadBook(book_id: number): void {
    this.fileService.downloadFile(book_id).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'book.pdf';
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    }, error => {
      console.error('Error downloading file:', error);
    });
  }
}
