import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = "http://localhost:8080/api/user";

  constructor(private http: HttpClient) { }

  register(user: User): Observable<User>{
    return this.http.post<User>(`${this.baseUrl}/register`, user);
  }
}
