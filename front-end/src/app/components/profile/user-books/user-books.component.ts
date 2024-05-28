import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/authService/auth.service';
import { FileService } from 'src/app/services/fileService/file.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-books',
  templateUrl: './user-books.component.html',
  styleUrls: ['./user-books.component.css']
})
export class UserBooksComponent implements OnInit{

  selectedFile: File | null = null;
  selectedImage: File | null = null;
  title:string;
  author:string;
  user:User = new User();

  constructor(private fileService: FileService, private login:AuthService){}

  ngOnInit(): void {
    this.getCurrentUser();
  }

  getCurrentUser(){
    this.login.currentUser().subscribe((user:any) => {
      this.login.setUser(user);
      this.user = user;
    })
  }

  onFileChange(event:any) {
    this.selectedFile = event.target.files[0];
  }

  onFileChangeT(event:any) {
    this.selectedImage = event.target.files[0];
  }

  onUpload() {
    if (this.selectedFile && this.title != undefined && this.author != undefined && this.selectedImage) {
      this.fileService.uploadFile(this.selectedFile, this.title, this.author, this.user.id, this.selectedImage).subscribe(
        response => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Archivo subido con éxito.",
            showConfirmButton: false,
            timer: 2500
          });
        },
        error => {
          Swal.fire({
            icon: "error",
            title: "Ocurrio un error...",
            text: "Verifica que el archivo sea .pdf"
          });
        }
      );
    } else {
      alert('Debes seleccionar un archivo, poner su título y autor.');
    }
  }

  onDownload(field_id: number): void {
    this.fileService.downloadFile(field_id).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'downloaded.pdf';
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    }, error => {
      Swal.fire({
        icon: "error",
        text: "Algo salió mal.",
      });
    });
  }
}
