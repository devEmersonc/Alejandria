import { HttpClient } from '@angular/common/http';
import { Comment } from '../../models/comment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = "http://localhost:8080/api/comments";

  constructor(private http: HttpClient) { }

  newComment(comment:Comment, user_id:number, post_id:number): Observable<Comment>{
    return this.http.post<Comment>(`${this.baseUrl}/new-comment/${user_id}/${post_id}`, comment);
  }
}
