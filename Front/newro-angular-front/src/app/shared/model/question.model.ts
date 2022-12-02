import { Answer } from "./answer.model";

export class Question {
    id : number;
    title : string;
    statement : string;
    chapter_id : number;
    answer : Answer[];
    validate : boolean

    constructor(title:string, statement:string, chapter_id:number, answer:Answer[], id:number){
        this.title = title;
        this.statement = statement;
        this.chapter_id = chapter_id;
        this.id = id;
        this.answer = answer;
        this.validate = false;
    }
}