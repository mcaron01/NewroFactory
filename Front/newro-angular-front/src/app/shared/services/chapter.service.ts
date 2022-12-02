import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Chapter } from '../model/chapter.model';
import { BACK_END_URL } from './back-end-url';

@Injectable({
  providedIn: 'root'
})
export class ChapterService {

  constructor(
    private http: HttpClient,
  ) { }

  getChapters(): Observable<Chapter[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Chapter[]>(BACK_END_URL + '/chapter', {
      headers: { Authorization: `Bearer ${token}` }});
  }

  getSubChapters(id :string | null): Observable<Chapter[]>{
    const token = sessionStorage.getItem('token');
    return this.http.get<Chapter[]>(`${BACK_END_URL}/chapter/${id}`, {
      headers: { Authorization: `Bearer ${token}` }});
  }


  getChapterName(id:string|null): Observable<Chapter>{
    const token = sessionStorage.getItem('token');
    return this.http.get<Chapter>(`${BACK_END_URL}/chapterDetails/${id}`, {
      headers: { Authorization: `Bearer ${token}` }});
  }
}
