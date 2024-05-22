import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/authService/auth.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit{

  constructor(private login: AuthService){}

  ngOnInit(): void {
    
  }

  isLoggedIn(){
    if(this.login.isLoggedIn()){
      return true;
    }else{
      return false;
    }
  }
}
