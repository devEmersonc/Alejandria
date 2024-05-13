import { Component, OnInit } from '@angular/core';

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

  ngOnInit(): void {
    
  }

}
