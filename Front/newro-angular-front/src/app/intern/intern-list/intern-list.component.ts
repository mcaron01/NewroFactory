import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Intern } from 'src/app/shared/model/intern.model';
import { User } from 'src/app/shared/model/user.model';
import { InternService } from 'src/app/shared/services/intern.service';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarComponent } from '../snackbar/snackbar.component';
import { LoginService } from 'src/app/shared/services/login.service';
import { Subject, takeUntil } from 'rxjs';
import { Promotion } from 'src/app/shared/model/promotion.model';
import { PromotionService } from 'src/app/shared/services/promotion.service';

@Component({
  selector: 'app-intern-list',
  templateUrl: './intern-list.component.html',
  styleUrls: ['./intern-list.component.scss'],
})
export class InternListComponent implements OnInit {
  user!: User;
  internList!: Intern[];
  title = 'newro-angular-front';
  displayedColumns: string[] = [];
  dataSource!: MatTableDataSource<Intern>;
  selection!: SelectionModel<Intern>;
  isLoadingResults = true;
  internsCount = 0;
  internBool = false;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [10, 50, 100];
  filterName: string = '';
  orderBy = 'lastName';
  orderByDirection = 'asc';
  unsubscribe: Subject<boolean> = new Subject();
  role: String = '';

  promotionList!: Promotion[];
  selectedPromotion: string = '';

  constructor(
    private router: Router,
    private internService: InternService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private loginService: LoginService,
    private promotionService: PromotionService
  ) {}

  getInterns() {
    this.isLoadingResults = true;
    this.internService
      .getCountInterns(this.filterName, this.selectedPromotion)
      .subscribe({
        next: (count) => {
          this.internsCount = count;
          this.internBool = count == 0 ? true : false;
          this.internBool ? this.openSnackBar() : null;
          this.internService
            .getInterns(
              this.pageIndex + 1,
              this.pageSize,
              this.orderBy,
              this.orderByDirection,
              this.filterName,
              this.selectedPromotion
            )
            .subscribe({
              next: (data) => {
                this.internList = data;
                this.dataSource = new MatTableDataSource(data);
                this.dataSource.filterPredicate = function (
                  dataIntern,
                  filter: string
                ): boolean {
                  return (
                    dataIntern.lastName.toLowerCase().includes(filter) ||
                    dataIntern.firstName.toLowerCase().includes(filter)
                  );
                };
                this.isLoadingResults = false;
              },
              error: (err) => {
                const numberError = err.status;
                this.redirect_toErrorPage(numberError);
                this.internList = [];
              },
            });
        },
        error: (err) => {
          const numberError = err.status;
          this.redirect_toErrorPage(numberError);
        },
      });
  }

  initDisplayColumns(): void {
    if (this.role === 'ROLE_ADMIN') {
      this.displayedColumns = [
        'select',
        'lastName',
        'firstName',
        'dateArrivee',
        'dateFinFormation',
        'promotion',
        'edit',
      ];
    } else {
      this.displayedColumns = [
        'lastName',
        'firstName',
        'dateArrivee',
        'dateFinFormation',
        'promotion',
      ];
    }
  }

  ngOnInit(): void {
    this.loginService
      .getRoleObs()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((role) => (this.role = role));
    this.initDisplayColumns();
    this.user = JSON.parse(sessionStorage.getItem('user') || '{}');
    this.getInterns();

    this.promotionService.getPromotions().subscribe({
      next: (result) => {
        this.promotionList = result;
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });

    const initialSelection: Intern[] = [];
    const allowMultiSelect = true;
    this.selection = new SelectionModel<Intern>(
      allowMultiSelect,
      initialSelection
    );
  }

  displayDate(dateStr: string) {
    if (dateStr) {
      const date = new Date(dateStr);
      return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
    }
    return '';
  }

  handlePageEvent(e: PageEvent) {
    this.selection.clear();
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.getInterns();
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.internList.length;
    return numSelected == numRows;
  }

  toggleAllRows() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.internList.forEach((intern) => this.selection.select(intern));
  }

  deleteDialog() {
    const dialogRef = this.dialog.open(DeleteDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleteSelection();
      }
    });
  }

  deleteSelection() {
    this.isLoadingResults = true;
    const idList: number[] = this.selection.selected
      .filter((intern) => intern.id)
      .map((intern) => intern.id) as number[];
    this.internService.deleteInternList(idList).subscribe({
      next: () => {
        if (this.isAllSelected()) {
          this.pageIndex -= 1;
        }
        this.selection.clear();
        this.getInterns();
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
  }

  applyFilter() {
    this.pageIndex = 0;
    this.getInterns();
  }

  openSnackBar() {
    this.snackBar.openFromComponent(SnackbarComponent, {
      duration: 5000,
    });
  }

  sortBy(sort: Sort) {
    this.orderByDirection = sort.direction;
    sort.active === 'promotion'
      ? (this.orderBy = 'promotion_id')
      : (this.orderBy = sort.active);
    this.getInterns();
  }

  edit(intern: Intern) {
    this.router.navigate(['/editStagiaire/' + intern.id]);
  }

  clearSearch() {
    this.filterName = '';
    this.getInterns();
  }

  clearSearchPromo(){
    this.selectedPromotion = '';
    this.getInterns();
  }

  redirect_toAddStagiaires() {
    this.router.navigate(['ajoutStagiaire']);
  }

  promotionChange(value: string) {
    this.pageIndex = 0;
    this.getInterns();
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
