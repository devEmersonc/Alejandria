import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/models/Post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseUrl = "http://localhost:8080/api/post";

  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Post[]>{
    return this.http.get<Post[]>(`${this.baseUrl}/all-posts`);
  }

  newPost(post:Post, user_id:number): Observable<Post>{
    return this.http.post<Post>(`${this.baseUrl}/post/${user_id}`, post);
  }
}
