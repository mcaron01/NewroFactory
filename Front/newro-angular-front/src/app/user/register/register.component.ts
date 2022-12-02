import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { LoginService } from 'src/app/shared/services/login.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  hide = true;
  hideConfirm = true;
  errors: { [key: string]: string } = {};
  registerForm = this.formBuilder.group({
    username: '',
    password: '',
    passwordConfirmation: '',
  });
  firstAttempt: boolean = true;
  unsubscribe: Subject<boolean> = new Subject();
  isLoggedIn: boolean = sessionStorage.getItem('token') !== null;
  role: String = '';

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private loginService: LoginService,
    private router: Router,
    private snackBar: MatSnackBar,
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
    if (
      this.registerForm.value.username == null ||
      this.registerForm.value.username.trim().length < 3
    ) {
      return true;
    } else {
      return !(
        this.registerForm.value.username?.length >= 3 &&
        this.registerForm.value.username?.length < 25
      );
    }
  }

  validatePassword(): boolean {
    if (
      this.registerForm.value.password == null ||
      this.registerForm.value.password.trim().length < 3
    ) {
      return true;
    } else {
      return !(
        this.registerForm.value.password?.length >= 3 &&
        this.registerForm.value.password?.length < 25
      );
    }
  }

  validatePasswordConfirmation(): boolean {
    if (
      this.registerForm.value.passwordConfirmation == null ||
      this.registerForm.value.passwordConfirmation.trim().length < 3
    ) {
      return true;
    } else {
      return !(
        this.registerForm.value.passwordConfirmation ==
        this.registerForm.value.password
      );
    }
  }

  validateForm(): boolean {
    return (
      this.validateUsername() ||
      this.validatePassword() ||
      this.validatePasswordConfirmation()
    );
  }

  tryRegister() {
    if (this.firstAttempt) {
      this.firstAttempt = false;
    }
    if (!this.validateForm()) {
      this.register();
    }
  }

  register() {
    const item = this.registerForm.value;
    this.userService
      .createUser(
        this.registerForm.value.username,
        this.registerForm.value.password
      )
      .subscribe({
        next: (error) => {
          this.errors = error;
          if (!('username' in this.errors)) {
            this.router.navigateByUrl('/connexion');
          } else {
            console.log(error);
          }
          this.openSnackBar();
        },
        error: (err) => console.log('ERROR ' + err.message),
      });
  }

  openSnackBar() {
    this.snackBar.open('Vous compte a bien été créé', 'Fermer', {
      duration: 4000,
    });
    this.snackBar._openedSnackBarRef?.onAction().subscribe(() => {
      this.snackBar.dismiss();
    });
  }

  redirect_toHome() {
    this.router.navigate(['stagiaires']);
  }

  redirect_toChapterHome() {
    this.router.navigate(['cours']);
  }
}
