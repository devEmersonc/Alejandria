import { Post } from "./Post";
import { User } from "./user";

export class Comment{
    id:number;
    content:string;
    user:User;
    post:Post;
    createdAt:string;
}