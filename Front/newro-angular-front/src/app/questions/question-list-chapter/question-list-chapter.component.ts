import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { Answer } from 'src/app/shared/model/answer.model';
import { Question } from 'src/app/shared/model/question.model';
import { AnswerService } from 'src/app/shared/services/answer.service';
import { ChapterService } from 'src/app/shared/services/chapter.service';
import { QuestionService } from 'src/app/shared/services/question.service';

@Component({
  selector: 'app-question-list-chapter',
  templateUrl: './question-list-chapter.component.html',
  styleUrls: ['./question-list-chapter.component.scss'],
})
export class QuestionListChapterComponent implements OnInit {
  id!: string | null;
  idParent!: string | null;
  title!: string | null;
  questionList!: Question[];
  answerList!: Answer[];
  questionsCount = 0;
  questionBool = false;
  questionAllBool = false;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [10, 50, 100];
  displayedColumns: string[] = ['id', 'title', 'statement', 'chapter_id'];

  constructor(
    private activatedRoute: ActivatedRoute,
    private questionService: QuestionService,
    private answerService: AnswerService,
    private router: Router,
    private chapterService: ChapterService,
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: (param) => {
        this.id = param.get('id1');
        this.idParent = param.get('id');
        this.chapterDetails(param.get('id1'))   
        if (param.get('id') == param.get('id1')) {
          this.questionAllBool = true;
        } else {
          
          this.questionAllBool = false;
          console.log("non")
        }
        this.getQuestions(this.id);


      },
      error: (err) => {
        const numberError = err.status;
        this.redirect_toErrorPage(numberError);
      },
    });

  }

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.getQuestions(this.id);
  }

  getQuestions(id: string | null) {
    if(this.questionAllBool){
      this.questionService.getCountQuestionsByAllChapter(id!).subscribe({
        next: (count) => {
          this.questionsCount = count;
          this.questionBool = count == 0 ? true : false;
  
          this.questionService
            .getAllQuestionsChapter(id!, this.pageIndex + 1, this.pageSize)
            .subscribe({
              next: (data) => {
                this.questionList = data;
                this.getAnswer();
              },
              error: (err) => {
                const numberError = err.status;
                this.redirect_toErrorPage(numberError);
              },
            });
        },
        error: (err) => {
          const numberError = err.status;
          this.redirect_toErrorPage(numberError);
        },
      });
    }else{
      this.questionService.getCountQuestionsByChapter(id!).subscribe({
        next: (count) => {
          this.questionsCount = count;
          this.questionBool = count == 0 ? true : false;
  
          this.questionService
            .getQuestionsByChapter(id!, this.pageIndex + 1, this.pageSize)
            .subscribe({
              next: (data) => {
                this.questionList = data;
                this.getAnswer();
              },
              error: (err) => {
                const numberError = err.status;
                this.redirect_toErrorPage(numberError);
              },
            });
        },
        error: (err) => {
          const numberError = err.status;
          this.redirect_toErrorPage(numberError);
        },
      });
    }

    
  }


  getAllQuestions(id: string | null) {
    this.questionService
      .getAllQuestionsChapter(id!, this.pageIndex + 1, this.pageSize)
      .subscribe({
        next: (data) => {
          console.log(data)
          console.log(data.length)
          this.questionsCount = data.length;
          this.questionBool = data.length == 0 ? true : false;
          this.questionList = data;
          this.getAnswer();
        },
        error: (err) => {
          const numberError = err.status;
          this.redirect_toErrorPage(numberError);
        },
      });

  }


  chapterDetails(id: string | null) {
    this.chapterService.getChapterName(id).subscribe((name) => {
      this.title = name.name;

    })
  }

  getAnswer() {
    for (let i = 0; i < this.questionList.length; i++) {
      this.answerService.getAnswer(this.questionList[i].id).subscribe({
        next: (data) => {
          this.questionList[i].answer = data;
        },
        error: (err) => {
          const numberError = err.status;
          this.redirect_toErrorPage(numberError);
        },
      });
    }
  }

  validAnswer(index: number) {

    const p = this.questionList.map((e) => e.id).indexOf(index);
    this.questionList[p].validate = true;
    const checked = this.questionList[p].answer.filter(
      (answer) => answer.checked
    );
    const valid = this.questionList[p].answer.filter(
      (answer) => answer.validAnswer == 1
    );

    let bool = true;

    for (let i = 0; i < checked.length; i++) {
      if (checked[i].validAnswer == 0) {
        bool = false;
      }
    }

    this.questionList[p].answer.forEach((data) => {
      data.color = '';
      if (data.checked && data.validAnswer == 1) {
        data.color = 'green';
      } else if (data.checked && data.validAnswer == 0) {
        data.color = 'red';
      } else if (data.validAnswer == 1) {
        data.color = 'orange';
      }
    });
  }

  clear() {
    this.questionList.forEach((element) => {
      element.validate = false;
      element.answer.forEach((answer) => {
        answer.checked = false;
        answer.color = '';
      });
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

  back() {
    this.router.navigate(["cours/" + this.idParent])
  }
}
