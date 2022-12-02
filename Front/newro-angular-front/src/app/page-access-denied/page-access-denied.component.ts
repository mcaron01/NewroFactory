import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { LoginService } from '../shared/services/login.service';

@Component({
  selector: 'app-page-access-denied',
  templateUrl: './page-access-denied.component.html',
  styleUrls: ['./page-access-denied.component.scss']
})
export class PageAccessDeniedComponent implements OnInit {

  unsubscribe: Subject<boolean> = new Subject();
  role: string = '';
  isLoggedIn: boolean = sessionStorage.getItem('token') !== null;

  constructor(private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {
    this.loginService
      .getLoginObs()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((bool) => (this.isLoggedIn = bool));
    this.loginService
      .getRoleObs()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((role) => (this.role = role));
  }

  redirect_toHome() {
    this.router.navigate(['stagiaires']);
  }

  redirect_toChapterHome() {
    this.router.navigate(['cours']);
  }

  redirect_toLogin() {
    this.router.navigate(['connexion']);
  }
}
