import { Pdf } from "./Pdf";
import { Post } from "./Post";

export class User{
    id:number;
    username:string;
    email:string;
    password:string;
    libros:Pdf[] = [];
    posts:Post[] = [];
}