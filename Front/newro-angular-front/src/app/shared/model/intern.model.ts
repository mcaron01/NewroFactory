import { Promotion } from "./promotion.model";

export class Intern {
  id? : number;
  lastName : string;
  firstName : string;
  dateArrivee : string;
  dateFinFormation : string;
  promotion : Promotion;

  constructor(lastName: string, firstName: string, dateArrivee: string, dateFinFormation: string, promotion : Promotion, id?: number) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.dateArrivee = dateArrivee;
    this.dateFinFormation = dateFinFormation;
    this.promotion = promotion;
    this.id = id;
  }
}
