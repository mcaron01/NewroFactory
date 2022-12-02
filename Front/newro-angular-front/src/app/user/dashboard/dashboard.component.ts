import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { User } from 'src/app/shared/model/user.model';
import { LoginService } from 'src/app/shared/services/login.service';
import { UserService } from 'src/app/shared/services/user.service';
import { DeleteUserDialogComponent } from '../delete-user-dialog/delete-user-dialog.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  constructor(
    private userService: UserService,
    private loginService: LoginService,
    private router: Router,
    private dialog: MatDialog
  ) {}
  username!: string | undefined;
  usersList!: User[];
  unsubscribe: Subject<boolean> = new Subject();
  role: String = '';
  displayedColumns: string[] = [];
  dataSource!: MatTableDataSource<User>;
  selection!: SelectionModel<User>;
  usersCount = 0;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [10, 50, 100];
  isLoadingResult = true;

  ngOnInit(): void {
    this.username = sessionStorage
      .getItem('user')
      ?.replace('"', '')
      ?.replace('"', '');
    this.getUsers();
    this.initDisplayColumns();
    const initialSelection: User[] = [];
    const allowMultiSelect = true;
    this.selection = new SelectionModel<User>(
      allowMultiSelect,
      initialSelection
    );
  }

  getUsers() {
    this.userService.getCountUsers().subscribe({
      next: (count) => {
        this.usersCount = count-1;
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
    this.loginService
      .getRoleObs()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((role) => (this.role = role));
    this.userService.getAllUsers(this.pageIndex + 1, this.pageSize,this.username).subscribe({
      next: (data) => {
        this.usersList = data;
        this.dataSource = new MatTableDataSource(this.usersList);
        this.isLoadingResult = false;
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
  }

  compare(name: string): boolean {
    return this.username != name;
  }

  initDisplayColumns(): void {
    this.displayedColumns = ['select', 'userName', 'role', 'edit'];
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.usersList.length;
    return numSelected == numRows;
  }

  toggleAllRows() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.usersList.forEach((user) => this.selection.select(user));
  }

  deleteDialog() {
    const dialogRef = this.dialog.open(DeleteUserDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleteSelection();
      }
    });
  }

  deleteSelection() {
    const idList: number[] = this.selection.selected
      .filter((user) => user.id)
      .map((user) => user.id) as number[];
    this.userService.deleteUserList(idList).subscribe({
      next: () => {
        if (this.isAllSelected()) {
          this.pageIndex -= 1;
        }
        this.selection.clear();
        this.getUsers();
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
  }

  edit(user: User) {
    this.router.navigate(['/editUtilisateur/' + user.id]);
  }

  handlePageEvent(e: PageEvent) {
    this.selection.clear();
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.getUsers();
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
