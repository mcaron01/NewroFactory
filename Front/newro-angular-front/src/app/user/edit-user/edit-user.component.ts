import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, EMPTY } from 'rxjs';
import { User } from '../../shared/model/user.model';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {
  user!: User;
  id!: number;
  editForm!: FormGroup;
  errors: { [key: string]: string } = {};
  password!: string;
  role!: string;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  pressEnter() {
    //add validation check
    this.submitForm();
  }

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.userService.getUser(this.id).subscribe({
      next: (result) => {
        this.user = result;
        this.editForm = this.formBuilder.group({
          id: this.user.id,
          username: this.user.username,
          password: this.user.password,
          role: this.user.role,
        });
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
  }

  validateForm(): boolean {
    return true;
  }

  submitForm() {
    this.user = this.editForm.value as User;
    this.user.id = this.id;
    this.userService.editUser(this.user).subscribe({
      next: (errors) => {
        this.errors = errors;
        this.redirect_toUserDashboard();
        this.openSnackBar();
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
  }

  openSnackBar() {
    this.snackBar.open("L'utilisateur a bien été modifié.", 'Fermé.', {
      duration: 4000,
    });
    this.snackBar._openedSnackBarRef?.onAction().subscribe(() => {
      this.snackBar.dismiss();
    });
  }

  redirect_toUserDashboard() {
    this.router.navigate(['utilisateurs']);
  }

  redirect_toErrorPage(numberError: number) {
    switch (numberError) {
      case 400:
        this.router.navigate(['error400']);
        break;
      case 401:
        this.router.navigate(['accesRefuse']);
        break;
      case 403:
        this.router.navigate(['accesRefuse']);
        break;
      case 404:
        this.router.navigate(['PageNotFoundComponent']);
        break;
      case 500:
        this.router.navigate(['error']);
        break;
    }
  }
}
