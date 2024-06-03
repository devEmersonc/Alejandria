import { User } from "./user";
import { Comment } from "./comment";

export class Post{
    id:number;
    title:string;
    description:string;
    user:User;
    createdAt:string;
    comments:Comment[] = [];
}