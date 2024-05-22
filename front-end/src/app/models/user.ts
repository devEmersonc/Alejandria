import { Pdf } from "./Pdf";

export class User{
    id:number;
    username:string;
    email:string;
    password:string;
    libros:Pdf[] = [];
}