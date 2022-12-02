import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Intern } from 'src/app/shared/model/intern.model';
import { InternService } from 'src/app/shared/services/intern.service';
import { PromotionService } from 'src/app/shared/services/promotion.service';
import { Promotion } from '../../shared/model/promotion.model';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-stagiaire',
  templateUrl: './edit-stagiaire.component.html',
  styleUrls: ['./edit-stagiaire.component.scss'],
})
export class EditStagiaireComponent implements OnInit {
  promotionList!: Promotion[];
  intern!: Intern;
  id!: number;
  editForm!: FormGroup;
  errors: { [key: string]: string } = {};
  lastNameIn!: boolean;
  firstNameIn!: boolean;
  dateArriveeIn!: boolean;
  dateFinFormationIn!: boolean;
  promotionIn!: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private promotionService: PromotionService,
    private internService: InternService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.internService.getIntern(this.id).subscribe({
      next: (result) => {
        this.intern = result;
        this.editForm = this.formBuilder.group({
          id: this.intern.id,
          lastName: this.intern.lastName,
          firstName: this.intern.firstName,
          dateArrivee: this.intern.dateArrivee,
          dateFinFormation: this.intern.dateFinFormation,
          promotion: {
            id: this.intern.promotion.id,
            name: this.intern.promotion.name,
          },
        });
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });

    this.promotionService.getPromotions().subscribe({
      next: (result) => (this.promotionList = result),
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
  }

  validateNom(): boolean {
    if (this.editForm) {
      if (
        !this.editForm.value.lastName ||
        this.editForm.value.lastName.trim().length < 2
      ) {
        return true;
      } else {
        return !(
          this.editForm.value.lastName?.length >= 2 &&
          this.editForm.value.lastName?.length < 25
        );
      }
    }
    return true;
  }

  validatePrenom(): boolean {
    if (!this.editForm.value.firstName) {
      return true;
    } else {
      return !(
        this.editForm.value.firstName?.length >= 2 &&
        this.editForm.value.firstName?.length < 25
      );
    }
  }

  validateDateArrivee(): boolean {
    return !this.editForm.value.dateArrivee;
  }

  futureDateArrivee(): boolean {
    if (this.editForm.value.dateArrivee) {
      const dateArrivee = new Date(this.editForm.value.dateArrivee);
      return dateArrivee > new Date();
    } else {
      return false;
    }
  }

  validateDateFinFormation(): boolean {
    if (
      this.editForm.value.dateArrivee &&
      this.editForm.value.dateFinFormation
    ) {
      const dateArrivee = new Date(this.editForm.value.dateArrivee);
      const dateFinFormation = new Date(this.editForm.value.dateFinFormation);
      return dateFinFormation < dateArrivee;
    } else {
      return false;
    }
  }

  validatePromotion(): boolean {
    return !this.editForm.value.promotion?.name;
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
    if (!this.validateForm()) {
      this.submitForm();
    }
  }

  submitForm() {
    const changeArrivee =
      this.intern.dateArrivee !== this.editForm.value.dateArrivee;
    const changeFinFormation =
      this.intern.dateFinFormation !== this.editForm.value.dateFinFormation;

    this.intern = this.editForm.value as Intern;
    this.intern.id = this.id;

    if (this.intern.dateArrivee && changeArrivee) {
      const date = new Date(this.intern.dateArrivee);
      date.setDate(date.getDate() + 1);
      this.intern.dateArrivee = date.toISOString().slice(0, 10);
    }
    if (this.intern.dateFinFormation && changeFinFormation) {
      const date = new Date(this.intern.dateFinFormation);
      date.setDate(date.getDate() + 1);
      this.intern.dateFinFormation = date.toISOString().slice(0, 10);
    }

    this.internService.editIntern(this.intern).subscribe({
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
    this.snackBar.open('Le stagiaire a bien été modifié.', 'Fermer', {
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
