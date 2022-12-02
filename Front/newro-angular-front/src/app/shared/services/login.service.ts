import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { BACK_END_URL } from './back-end-url';

@Injectable({
  providedIn: 'root'
})
export class LoginService implements CanActivate {

  private isLoggedin: BehaviorSubject<boolean> = new BehaviorSubject(sessionStorage.getItem('token') !== null);
  private role: BehaviorSubject<string> = new BehaviorSubject(String(sessionStorage.getItem('permission')));

  constructor(private router: Router, private http:HttpClient) { }

  getLoginObs(): Observable<boolean> {
    return this.isLoggedin.asObservable();
  }

  setLoginObs(log: boolean): void {
    this.isLoggedin.next(log);
  }

  getRoleObs(): Observable<string> {
    return this.role.asObservable();
  }

  setRoleObs(role: string): void {
    this.role.next(role);
  }

  login(user: string | undefined | null , pwd: string | undefined | null): Observable<any> {
    return this.http.post(BACK_END_URL + '/login', {
      username: user,
      password: pwd
    } )
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (sessionStorage.getItem('permission')) {
      return true
    }
    else {
      this.router.navigateByUrl('/login')
      return false;
    }
  }

  logout(user : string | undefined | null): Observable<any>{
    const token = sessionStorage.getItem("token");
    return this.http.post(BACK_END_URL+'/deco', {
      username : user
    }, { headers: { "Authorization": `Bearer ${token}` } });
  }
}
