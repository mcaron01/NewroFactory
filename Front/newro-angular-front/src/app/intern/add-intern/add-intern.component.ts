import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Intern } from 'src/app/shared/model/intern.model';
import { Promotion } from 'src/app/shared/model/promotion.model';
import { InternService } from 'src/app/shared/services/intern.service';
import { PromotionService } from 'src/app/shared/services/promotion.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-intern',
  templateUrl: './add-intern.component.html',
  styleUrls: ['./add-intern.component.scss'],
})
export class AddInternComponent implements OnInit {
  promotionList!: Promotion[];
  intern!: Intern;
  errors: { [key: string]: string } = {};
  lastNameIn!: boolean;
  firstNameIn!: boolean;
  dateArriveeIn!: boolean;
  dateFinFormationIn!: boolean;
  promotionIn!: boolean;
  InternForm = this.fb.group({
    lastName: '',
    firstName: '',
    dateArrivee: '',
    dateFinFormation: '',
    promotion: { id: 0, name: '' },
  });
  firstAttempt: boolean = true;

  constructor(
    private fb: FormBuilder,
    private promotionService: PromotionService,
    private internService: InternService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.promotionService.getPromotions().subscribe({
      next: (result) => {
        this.promotionList = result;
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
        this.promotionList = [];
      },
    });
  }

  validateNom(): boolean {
    if (
      !this.InternForm.value.lastName ||
      this.InternForm.value.lastName.trim().length < 2
    ) {
      return true;
    } else {
      return !(
        this.InternForm.value.lastName?.length >= 2 &&
        this.InternForm.value.lastName?.length < 25
      );
    }
  }

  validatePrenom(): boolean {
    if (
      !this.InternForm.value.firstName ||
      this.InternForm.value.firstName.trim().length < 2
    ) {
      return true;
    } else {
      return !(
        this.InternForm.value.firstName?.length >= 2 &&
        this.InternForm.value.firstName?.length < 25
      );
    }
  }

  validateDateArrivee(): boolean {
    return !this.InternForm.value.dateArrivee;
  }

  futureDateArrivee(): boolean {
    if (this.InternForm.value.dateArrivee) {
      const dateArrivee = new Date(this.InternForm.value.dateArrivee);
      return dateArrivee > new Date();
    } else {
      return false;
    }
  }

  validateDateFinFormation(): boolean {
    if (
      this.InternForm.value.dateArrivee &&
      this.InternForm.value.dateFinFormation
    ) {
      const dateArrivee = new Date(this.InternForm.value.dateArrivee);
      const dateFinFormation = new Date(this.InternForm.value.dateFinFormation);
      return dateFinFormation < dateArrivee;
    } else {
      return false;
    }
  }

  validatePromotion(): boolean {
    return !this.InternForm.value.promotion?.name;
  }

  validateForm(): boolean {
    return (
      this.validateNom() ||
      this.validatePrenom() ||
      this.validateDateArrivee() ||
      this.validatePromotion() ||
      this.futureDateArrivee() ||
      this.validateDateFinFormation()
    );
  }

  trySubmit() {
    if (this.firstAttempt) {
      this.firstAttempt = false;
    }
    if (!this.validateForm()) {
      this.submitForm();
    }
  }

  submitForm() {
    this.intern = this.InternForm.value as Intern;

    if (this.intern.dateArrivee) {
      const date = new Date(this.intern.dateArrivee);
      date.setDate(date.getDate() + 1);
      this.intern.dateArrivee = date.toISOString().slice(0, 10);
    }
    if (this.intern.dateFinFormation) {
      const date = new Date(this.intern.dateFinFormation);
      date.setDate(date.getDate() + 1);
      this.intern.dateFinFormation = date.toISOString().slice(0, 10);
    }

    this.internService.addIntern(this.intern).subscribe({
      next: (errors) => {
        this.errors = errors;
        this.lastNameIn = 'lastName' in this.errors;
        this.firstNameIn = 'firstName' in this.errors;
        this.dateArriveeIn = 'dateArrivee' in this.errors;
        this.dateFinFormationIn = 'dateFinFormation' in this.errors;
        this.promotionIn = 'promotion.id' in this.errors;
        this.redirect_toDashboard();
        this.openSnackBar();
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
  }

  openSnackBar() {
    this.snackBar.open('Le stagiaire a bien été créé.', 'Fermer', {
      duration: 4000,
    });
    this.snackBar._openedSnackBarRef?.onAction().subscribe(() => {
      this.snackBar.dismiss();
    });
  }

  redirect_toDashboard() {
    this.router.navigate(['stagiaires']);
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
