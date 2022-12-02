import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forkJoin, Observable } from 'rxjs';
import { User } from '../model/user.model';
import { BACK_END_URL } from './back-end-url';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  createUser(
    user: string | undefined | null,
    pwd: string | undefined | null
  ): Observable<any> {
    const token = sessionStorage.getItem('token');
    return this.http.post<any>(
      BACK_END_URL + '/register',
      {
        username: user,
        password: pwd,
      },
      { headers: { Authorization: `Bearer ${token}` } }
    );
  }

  getAllUsers(pageIndex: number, pageSize: number,user: string | undefined): Observable<User[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<User[]>(
      BACK_END_URL +
        '/user/dashboard?page=' +
        pageIndex +
        '&nbUsers=' +
        pageSize +
        '&user=' +
        user,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
  }

  getUser(id: number): Observable<User> {
    const token = sessionStorage.getItem('token');
    return this.http.get<User>(`${BACK_END_URL}/user/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  editUser(user: User): Observable<{ [key: string]: string }> {
    const token = sessionStorage.getItem('token');
    return this.http.patch<{ [key: string]: string }>(
      `${BACK_END_URL}/user/${user.id}`,
      {
        id: user.id,
        username: user.username,
        password: user.password,
        role: user.role,
      },
      { headers: { Authorization: `Bearer ${token}` } }
    );
  }

  getCountUsers(): Observable<number> {
    const token = sessionStorage.getItem('token');
    return this.http.get<number>(BACK_END_URL + '/user/count', {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  deleteUserList(idList: number[]): Observable<any> {
    const token = sessionStorage.getItem('token');
    const requests = idList.map((id) =>
      this.http.delete<any>(`${BACK_END_URL}/user/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
    );
    return forkJoin(requests);
  }
}
