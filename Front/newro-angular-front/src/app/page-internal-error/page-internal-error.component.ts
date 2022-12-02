import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-internal-error',
  templateUrl: './page-internal-error.component.html',
  styleUrls: ['./page-internal-error.component.scss']
})
export class PageInternalErrorComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  redirect_toLogin() {
    this.router.navigate(['connexion']);
  }

}
