import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from '../model/question.model';
import { BACK_END_URL } from './back-end-url';


@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) {}

  getQuestions(
    pageIndex:number,
    pageSize:number,
  ): Observable<Question[]> {
      const token = sessionStorage.getItem('token');
      return this.http.get<Question[]>(
        BACK_END_URL
        + '/questions/?page='
        + pageIndex
        + '&nbQuestions='
        + pageSize
        ,{
        headers: { Authorization: `Bearer ${token}` },
      });
  }

  getQuestion(id:number): Observable<Question> {
    const token =  sessionStorage.getItem('token');
    return this.http.get<Question>(BACK_END_URL + `/questions/${id}`,{
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  getCountQuestions(): Observable<number> {
    const token = sessionStorage.getItem('token');
    return this.http.get<number>(BACK_END_URL + '/questions/count',{
      headers: { Authorization: `Bearer ${token}` },
    });
  }


  getQuestionsByChapter(id:string, pageIndex:number,pageSize:number):Observable<Question[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Question[]>(
      BACK_END_URL
      + `/questions/byChapter/${id}`
      + '?page='+ pageIndex
    + '&nbQuestions='
    + pageSize,{
      headers: { Authorization: `Bearer ${token}` },
    });
  }


  getAllQuestionsChapter(id:string, pageIndex:number,pageSize:number):Observable<Question[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Question[]>(
      BACK_END_URL
      + `/questions/${id}`
      + '?page='+ pageIndex
    + '&nbQuestions='
    + pageSize,{
      headers: { Authorization: `Bearer ${token}` },
    });
  }


  getCountQuestionsByChapter(id:string): Observable<number> {
    const token = sessionStorage.getItem('token');
    return this.http.get<number>(BACK_END_URL + `/questions/countByChapter/${id}`,{
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  getCountQuestionsByAllChapter(id:string): Observable<number> {
    const token = sessionStorage.getItem('token');
    return this.http.get<number>(BACK_END_URL + `/questions/countByParentChapter/${id}`,{
      headers: { Authorization: `Bearer ${token}` },
    });
  }




}
