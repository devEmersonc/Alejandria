import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from 'src/app/models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = "http://localhost:8080/api";

  constructor(private http:HttpClient) { }

  getAllCategories(): Observable<Category[]>{
    return this.http.get<Category[]>(`${this.baseUrl}/categories`);
  }
}
