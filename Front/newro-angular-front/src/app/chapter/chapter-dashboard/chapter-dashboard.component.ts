import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Chapter } from 'src/app/shared/model/chapter.model';
import { ChapterService } from 'src/app/shared/services/chapter.service';

@Component({
  selector: 'app-chapter-dashboard',
  templateUrl: './chapter-dashboard.component.html',
  styleUrls: ['./chapter-dashboard.component.scss'],
})
export class ChapterDashboardComponent implements OnInit {
  chapterList!: Chapter[];
  isLoadingResults = true;

  constructor(private router: Router, private chapterService: ChapterService) {}

  ngOnInit(): void {
    this.getChapters();
  }

  getChapters() {
    this.chapterService.getChapters().subscribe({
      next: (data) => {
        console.log(data)
        this.chapterList = data;
        this.isLoadingResults = false;
      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });
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
