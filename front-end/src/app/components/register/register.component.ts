import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/authService/auth.service';
import { UserService } from 'src/app/services/userService/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  user:User = new User();
  errors:string[];

  constructor(private login: AuthService, private userService: UserService, private router: Router){}

  ngOnInit(): void {
    
  }

  register(){
    this.userService.register(this.user).subscribe({
      next: (json) => {
        Swal.fire({
          position: "center",
          icon: "success",
          showConfirmButton: false,
          timer: 2500
        });
        this.router.navigate(['/login'])
      },
      error: (err) => {
        this.errors = err.error.errors as string[];
      }
    })
  }
}
