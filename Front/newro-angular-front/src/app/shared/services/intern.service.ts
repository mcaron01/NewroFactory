import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, forkJoin } from 'rxjs';
import { Intern } from '../model/intern.model';
import { BACK_END_URL } from './back-end-url';

@Injectable({
  providedIn: 'root',
})
export class InternService {
  constructor(private http: HttpClient) {}

  getCountInterns(search: string, searchPromotion: string): Observable<number> {
    const token = sessionStorage.getItem('token');
    return this.http.get<number>(
      BACK_END_URL +
        '/count?search=' +
        search +
        '&searchPromotion=' +
        searchPromotion,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
  }

  getInterns(
    pageIndex: number,
    pageSize: number,
    orderBy: string,
    orderByDirection: string,
    search: string,
    searchPromotion: string
  ): Observable<Intern[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Intern[]>(
      BACK_END_URL +
        '/dashboard?page=' +
        pageIndex +
        '&nbInterns=' +
        pageSize +
        '&orderBy=' +
        orderBy +
        '&orderByDirection=' +
        orderByDirection +
        '&search=' +
        search +
        '&searchPromotion=' +
        searchPromotion,
      { headers: { Authorization: `Bearer ${token}` } }
    );
  }

  getIntern(id: number): Observable<Intern> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Intern>(`${BACK_END_URL}/editStagiaire/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  getInternSeach(search: string): Observable<Intern[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Intern[]>(
      BACK_END_URL + '/dashboard?search=' + search,
      { headers: { Authorization: `Bearer ${token}` } }
    );
  }

  addIntern(intern: Intern): Observable<{ [key: string]: string }> {
    const token = sessionStorage.getItem('token');
    return this.http.post<{ [key: string]: string }>(
      BACK_END_URL + '/addStagiaire',
      {
        id: intern.id,
        firstName: intern.firstName,
        lastName: intern.lastName,
        dateArrivee: intern.dateArrivee,
        dateFinFormation: intern.dateFinFormation,
        promotion: { id: intern.promotion.id, name: intern.promotion.name },
      },
      { headers: { Authorization: `Bearer ${token}` } }
    );
  }

  editIntern(intern: Intern): Observable<{ [key: string]: string }> {
    const token = sessionStorage.getItem('token');
    return this.http.put<{ [key: string]: string }>(
      `${BACK_END_URL}/editStagiaire/${intern.id}`,
      {
        id: intern.id,
        firstName: intern.firstName,
        lastName: intern.lastName,
        dateArrivee: intern.dateArrivee,
        dateFinFormation: intern.dateFinFormation,
        promotion: { id: intern.promotion.id, name: intern.promotion.name },
      },
      { headers: { Authorization: `Bearer ${token}` } }
    );
  }

  deleteInternList(idList: number[]): Observable<any> {
    const token = sessionStorage.getItem('token');
    const requests = idList.map((id) =>
      this.http.delete<any>(`${BACK_END_URL}/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
    );
    return forkJoin(requests);
  }
}
