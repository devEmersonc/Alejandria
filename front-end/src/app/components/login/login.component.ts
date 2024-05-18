import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/authService/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData = {
    "username": "",
    "password": ""
  }

  errors: string[];

  constructor(private login: AuthService, private router: Router) { }

  ngOnInit(): void {

  }

  formLogin() {
    this.login.generateToken(this.loginData).subscribe(
      (data: any) => {
        this.login.login(data.token);
        this.login.currentUser().subscribe((user: any) => {
          this.login.setUser(user);

          if(this.login.getUserRole() == 'ROLE_USER'){
            this.router.navigate(['/'])
          }else{
            this.login.logOut();
          }
        })
      }, (error) => {
        this.errors = error.error.errors as string[];
      }
    )
  }
}
