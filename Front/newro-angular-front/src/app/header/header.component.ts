import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { LoginService } from '../shared/services/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = sessionStorage.getItem('token') !== null;
  unsubscribe: Subject<boolean> = new Subject();
  role: String = '';
  userName: String = '';
  icon: String = 'subject';

  constructor(
    private router: Router,
    private loginService: LoginService,
    private snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {
    this.loginService
      .getLoginObs()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((bool) => {
        this.isLoggedIn = bool;
        this.userName =
          sessionStorage.getItem('user') !== null
            ? (sessionStorage.getItem('user') as String)
            : '';
        this.userName = this.userName.replace(/"/g, '');
      });
    this.loginService
      .getRoleObs()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((role) => {
        this.role = role;
        this.icon =
          this.role === 'ROLE_ADMIN'
            ? 'supervisor_account'
            : this.role === 'ROLE_USER'
            ? 'account_circle'
            : '';
      });
  }

  redirect_toHome() {
    this.router.navigate(['stagiaires']);
  }

  redirect_toUserHome() {
    this.router.navigate(['utilisateurs']);
  }

  redirect_toChapterHome() {
    this.router.navigate(['cours']);
  }

  logout() {
    console.log(
      sessionStorage.getItem('user')?.replace('"', '')?.replace('"', '')
    );
    this.loginService
      .logout(
        sessionStorage.getItem('user')?.replace('"', '')?.replace('"', '')
      )
      .subscribe(() => {
        console.log('succes');
      });
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('permission');
    this.loginService.setLoginObs(false);
    this.loginService.setRoleObs('');
    this.router.navigateByUrl('/connexion');
    this.openSnackBar();
  }

  openSnackBar() {
    this.snackBar.open('Vous avez bien été deconnecté', 'Fermer', {
      duration: 4000,
    });
    this.snackBar._openedSnackBarRef?.onAction().subscribe(() => {
      this.snackBar.dismiss();
    });
  }
}
