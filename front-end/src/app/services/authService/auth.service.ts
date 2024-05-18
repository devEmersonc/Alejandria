import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl = "http://localhost:8080/api/auth"

  constructor(private http: HttpClient) { }

  generateToken(loginData: any){
    return this.http.post(`${this.authUrl}/login`, loginData);
  }

  currentUser(){
    return this.http.get(`${this.authUrl}/user_actual`);
  }

  login(token:any){
    localStorage.setItem('token', token);
  }

  isLoggedIn(){
    let tokenStr = localStorage.getItem('token');
    if(tokenStr == undefined || tokenStr == '' || tokenStr == null){
      return false;
    }
    return true;
  }

  logOut(){
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    return true;
  }

  getToken(){
    return localStorage.getItem('token');
  }

  setUser(user:any){
    localStorage.setItem('user', JSON.stringify(user));
  }

  getUser(){
    let userStr = localStorage.getItem('user');
    if(userStr != null){
      return JSON.parse(userStr);
    }else{
      this.logOut();
      return null;
    }
  }

  getUserRole(){
    let user = this.getUser();
    return user.authorities[0].authority;
  }
}
