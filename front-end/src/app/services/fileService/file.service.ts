import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pdf } from 'src/app/models/Pdf';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private baseUrl = "http://localhost:8080/api/files";

  constructor(private http: HttpClient) { }

  getAllBooks(): Observable<Pdf[]>{
    return this.http.get<Pdf[]>(`${this.baseUrl}/books`);
  }

  uploadFile(file: File, title:string, author:string, user_id:number, image: File, category:string): Observable<any>{
    const formData : FormData = new FormData();
    formData.append('file', file);
    formData.append('image', image)

    return this.http.post(`${this.baseUrl}/upload/${user_id}/${title}/${author}/${category}`, formData, {
      responseType: 'text' as 'json'
    });
  }

  downloadFile(file_id: number): Observable<Blob>{
    return this.http.get(`${this.baseUrl}/download-file/${file_id}`, {
      responseType: 'blob'
    })
  }
}
