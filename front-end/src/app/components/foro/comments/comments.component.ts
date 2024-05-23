import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/models/Post';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/authService/auth.service';
import { PostService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit{

  post:Post = new Post();
  currentUser:User = new User();

  constructor(private login: AuthService, private route: ActivatedRoute, private postService: PostService){}

  ngOnInit(): void {
    this.getPost();
    this.getCurrentUser();
  }

  isLoggedIn(){
    if(this.login.isLoggedIn()){
      return true;
    }else{
      return false;
    }
  }

  getPost(){
    this.route.paramMap.subscribe(params => {
      let id:number = Number(params.get('id'));
      if(id){
        this.postService.getPost(id).subscribe(post => {
          this.post = post;
          if (!this.post.user) {
            this.post.user = new User();
            this.post.user.username = 'Anonimo';
          }
          window.scrollTo({
            top: 0,
            left: 0,
            behavior: 'smooth'
          });
        })
      }
    })
  }

  getCurrentUser(){
    this.login.currentUser().subscribe((user:any) => {
      this.login.setUser(user);
      this.currentUser = user;
    })
  }
}
