import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionListChapterComponent } from './question-list-chapter.component';

describe('QuestionListChapterComponent', () => {
  let component: QuestionListChapterComponent;
  let fixture: ComponentFixture<QuestionListChapterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuestionListChapterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuestionListChapterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
