import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/authService/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{


  categorias = [
    {
      id : '1',
      name: 'Libros Científicos'
    },
    {
      id : '1',
      name: 'Libros de Autoayuda'
    },
    {
      id : '1',
      name: 'Libros de Marketing'
    },
    {
      id : '1',
      name: 'Libros de Salud'
    },
    {
      id : '1',
      name: 'Libros de Historia'
    },
    {
      id : '1',
      name: 'Libros de Filosofía'
    },
    {
      id : '1',
      name: 'Libros de Arte'
    },
    {
      id : '1',
      name: 'Literatura Clásica'
    },
    {
      id : '1',
      name: 'Libros de Tecnología'
    }
  ]


  constructor(private login: AuthService, private router:Router){}

  ngOnInit(): void {
    this.isLoggedIn();
  }

  isLoggedIn(){
    if(this.login.isLoggedIn()){
      return true;
    }else{
      return false;
    }
  }

  logOut(){
    this.router.navigate(['/']);
    this.login.logOut();
  }
}
