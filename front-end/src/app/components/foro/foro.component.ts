import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/models/Post';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/authService/auth.service';
import { PostService } from 'src/app/services/postService/post.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-foro',
  templateUrl: './foro.component.html',
  styleUrls: ['./foro.component.css']
})
export class ForoComponent implements OnInit{

  user:User = new User();
  post:Post = new Post();
  posts:Post[];
  errors:string[];

  constructor(private postService: PostService, private login: AuthService){}

  ngOnInit(): void {
    this.getCurrentUser();
    this.getAllPosts();
  }

  getCurrentUser(){
    this.login.currentUser().subscribe((user:any) => {
      this.login.setUser(user);
      this.user = user;
    })    
  }

  isLoggedIn(){
    if(this.login.isLoggedIn()){
      return true;
    }else{
      return false;
    }
  }

  getAllPosts(){
    this.postService.getAllPosts().subscribe(posts => {
      this.posts = posts;
    })
  }

  newPost(){
    this.postService.newPost(this.post, this.user.id).subscribe({
      next: (json) => {
        window.location.reload();
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Publicación exitosa.",
          showConfirmButton: false,
          timer: 2500
        });
      },
      error: (err) => {
        this.errors = err.error.errors as string[];
      }
    })
  }
}
