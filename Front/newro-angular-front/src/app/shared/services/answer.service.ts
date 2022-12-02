import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Answer } from '../model/answer.model';
import { BACK_END_URL } from './back-end-url';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient) { }


  getAnswer(id:number): Observable<Answer[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Answer[]>(BACK_END_URL + `/answer/${id}`, {
      headers: {Authorization : `Bearer ${token}` },
    });
  }
}
