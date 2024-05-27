import { User } from "./user";
import { Comment } from "./comment";

export class Post{
    id:number;
    tag:string;
    title:string;
    description:string;
    user:User;
    comments:Comment[] = [];
}