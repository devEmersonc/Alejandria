import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AboutusComponent } from './components/aboutus/aboutus.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { userGuard } from './services/user.guard';
import { UserBooksComponent } from './components/profile/user-books/user-books.component';
import { ForoComponent } from './components/foro/foro.component';
import { CommentsComponent } from './components/foro/comments/comments.component';

const routes: Routes = [
  {path: '', component:HomeComponent, pathMatch: 'full'},
  {path: "register", component:RegisterComponent},
  {path: "login", component: LoginComponent},
  {path: "about-us", component: AboutusComponent},
  {path: "profile", component: ProfileComponent, canActivate: [userGuard]},
  {path: "your-books", component: UserBooksComponent, canActivate: [userGuard]},
  {path: "foro", component: ForoComponent},
  {path: "comments/:id", component: CommentsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
