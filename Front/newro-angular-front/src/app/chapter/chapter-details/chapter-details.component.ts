import { FlatTreeControl } from '@angular/cdk/tree';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import {
  MatTreeFlatDataSource,
  MatTreeFlattener,
} from '@angular/material/tree';
import { ActivatedRoute, Router } from '@angular/router';
import { Chapter } from 'src/app/shared/model/chapter.model';
import { ChapterService } from 'src/app/shared/services/chapter.service';
import { QuestionService } from 'src/app/shared/services/question.service';
interface SubChapterNode {
  expandable: boolean;
  name: string;
  level: number;
  id: number | undefined;
}
@Component({
  selector: 'app-chapter-details',
  templateUrl: './chapter-details.component.html',
  styleUrls: ['./chapter-details.component.scss'],
})
export class ChapterDetailsComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private chapterService: ChapterService,
    private router: Router,
    private questionService: QuestionService,
    private snackBar : MatSnackBar

  ) {}

  id!: string | null;
  listCompleteSubChapter!: Chapter[];
  countQuestion = 0;
  title!: string;
  hasQuestions! : boolean;
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      (params) => {
        this.id = params.get("name");
        this.chapterDetails(params.get("name"))
   
        this.questionService.getCountQuestionsByChapter(params.get("name")!).subscribe({
          next: (count) => {
            this.hasQuestions = count == 0 ? false : true
          }
        
        })
      }
    )
    this.getSubChapters();

  }
  chapterDetails(id: string | null) {
    this.chapterService.getChapterName(id).subscribe((name) => {
      this.title = name.name;

    })
  }
  private _transformer = (node: Chapter, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
      id: node.id
    };
  };
  treeControl = new FlatTreeControl<SubChapterNode>(
    (node) => node.level,
    (node) => node.expandable
  );
  treeFlattener = new MatTreeFlattener(
    this._transformer,
    (node) => node.level,
    (node) => node.expandable,
    (node) => node.children
  );
  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
  hasChild = (_: number, node: SubChapterNode) => node.expandable;
  getSubChapters() {
    this.chapterService.getSubChapters(this.id).subscribe({
      next: (data) => {
        this.listCompleteSubChapter = data;
        this.dataSource.data = this.listCompleteSubChapter;
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
  redirectToQuestionChapters(id1: string) {
    this.questionService.getCountQuestionsByChapter(id1).subscribe((count) => {
      if (count != 0) {
        this.router.navigate(["cours/" + this.id + "/" + id1])
      } else {
        this.openSnackBar();
      }
    })
  }
  openSnackBar() {
    this.snackBar.open('Pas de question associé à ce chapitre.', 'Fermer', {
      duration: 4000,
    });
    this.snackBar._openedSnackBarRef?.onAction().subscribe(() => {
      this.snackBar.dismiss();
    });
  }
  questionChapter() {
    this.router.navigate(["cours/" + this.id + "/" + this.id])
  }


  back(){
    this.router.navigate(["cours/"])
  }

  
}
