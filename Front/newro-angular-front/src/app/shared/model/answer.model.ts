export class Answer{
    id : number;
    label: string;
    text : string;
    validAnswer:number;
    question_id:number;
    checked:boolean = false;
    color:string='';

    constructor(id:number,label:string,text:string,validAnswer:number,question_id:number){
        this.id =id;
        this.label=label;
        this.text=text;
        this.validAnswer=validAnswer;
        this.question_id = question_id;
        this.checked = false;
        this.color = '';
    }
}