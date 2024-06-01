import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/authService/auth.service';
import { UserService } from 'src/app/services/userService/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{

  user:User = new User();
  updatedUser:User = new User();
  selectedImage:File | any = null;
  errors:string[];

  constructor(private login:AuthService, private userService: UserService){}

  ngOnInit(): void {
    this.getCurrentUser();
  }

  getCurrentUser(){
    this.login.currentUser().subscribe((user:any) => {
      this.login.setUser(user);
      this.user = user;
    })
  }

  updateUser(){
    this.updatedUser.id = this.user.id;
    this.updatedUser.username = this.user.username;
    this.updatedUser.email = this.user.email;
    this.updatedUser.photo = this.user.photo;
    this.updatedUser.password = this.user.password;
    this.userService.updateUser(this.updatedUser).subscribe({
      next: (json) => {
        Swal.fire("Perfil actualizado.");
        
      },
      error: (err) => {
        this.errors = err.error.errors as string[];
      }
    })
  }

  selectImage(event:any){
    this.selectedImage = event.target.files[0];
    if(this.selectedImage.type.indexOf('image') < 0){
      Swal.fire("Error", "La imagen debe ser jpg o png", "error");
      this.selectedImage = null;
    }
  }

  uploadImage(){
    if(!this.selectedImage){
      Swal.fire("Error", "Debe seleccionar una imagen", "error");
    }else{
      this.userService.uploadImage(this.selectedImage, this.user.id).subscribe(user => {
        window.location.reload();
        this.getCurrentUser();
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Foto de perfil actualizada.",
          showConfirmButton: false,
          timer: 2000
        });
      })
    }
  }
}
