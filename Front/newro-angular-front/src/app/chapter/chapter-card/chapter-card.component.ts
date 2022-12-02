import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Chapter } from 'src/app/shared/model/chapter.model';

@Component({
  selector: 'app-chapter-card',
  templateUrl: './chapter-card.component.html',
  styleUrls: ['./chapter-card.component.scss']
})
export class ChapterCardComponent implements OnInit {

  @Input()
  chapter!: Chapter;
  urlImage = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZujUIrupUH_6rTBnw9ikThSWJGqmgM94MNA&usqp=CAU"

  constructor(
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  redirectToDetailsChapter() {
    this.router.navigate(["cours/" + this.chapter.id])
  }
}
