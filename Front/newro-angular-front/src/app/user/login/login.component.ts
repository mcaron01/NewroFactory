import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { LoginService } from 'src/app/shared/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  hide = true;
  badCredentials = false;
  loginForm = this.formBuilder.group({
    username: '',
    password: '',
  });
  firstAttempt: boolean = true;
  unsubscribe: Subject<boolean> = new Subject();
  role: String = '';
  isLoggedIn: boolean = sessionStorage.getItem('token') !== null;

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.loginService
    .getLoginObs()
    .pipe(takeUntil(this.unsubscribe))
    .subscribe((bool) => (this.isLoggedIn = bool));
    this.loginService
    .getRoleObs()
    .pipe(takeUntil(this.unsubscribe))
    .subscribe((role) => (this.role = role));
    if (this.isLoggedIn === true) {
      if (this.role === "ROLE_ADMIN") {
        this.redirect_toHome();
      } else {
        this.redirect_toChapterHome();
      }
    }
  }

  validateUsername(): boolean {
    return (
      this.loginForm.value.username == null ||
      this.loginForm.value.username.trim().length === 0
    );
  }

  validatePassword(): boolean {
    return (
      this.loginForm.value.password == null ||
      this.loginForm.value.password.trim().length === 0
    );
  }

  validateForm(): boolean {
    return this.validateUsername() || this.validatePassword();
  }

  tryLogin() {
    if (this.firstAttempt) {
      this.firstAttempt = false;
    }
    if (!this.validateForm()) {
      this.login();
    }
  }

  login() {
    this.loginService
      .login(this.loginForm.value.username, this.loginForm.value.password)
      .subscribe({
        next: (data) => {
          const infos = JSON.parse(JSON.stringify(data));

          sessionStorage.setItem('token', infos['jwttoken']);
          sessionStorage.setItem('user', JSON.stringify(infos['username']));
          sessionStorage.setItem('permission', infos['role']);
          this.loginService.setLoginObs(true);

          this.loginService.setRoleObs(infos['role']);

          this.loginService
          .getRoleObs()
          .pipe(takeUntil(this.unsubscribe))
          .subscribe((role) => (this.role = role));
          if (this.role === "ROLE_ADMIN") {
            this.redirect_toHome();
          } else {
            this.redirect_toChapterHome();
          }
        },
        error: (err) => {
          this.badCredentials = true;
        },
      });
  }

  redirect_toHome() {
    this.router.navigate(['stagiaires']);
  }

  redirect_toChapterHome() {
    this.router.navigate(['cours']);
  }
}
