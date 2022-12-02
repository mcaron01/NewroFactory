import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { LoginService } from '../shared/services/login.service';

@Component({
  selector: 'app-page-error-bad-request',
  templateUrl: './page-error-bad-request.component.html',
  styleUrls: ['./page-error-bad-request.component.scss']
})
export class PageErrorBadRequestComponent implements OnInit {

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
