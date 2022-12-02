import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from '../app-routing.module';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomMaterialModule } from '../shared/custom.material.module';
import { InternModule } from '../intern/intern.module';
import { QuestionListChapterComponent } from './question-list-chapter/question-list-chapter.component';

@NgModule({
  declarations: [
    QuestionListChapterComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    CustomMaterialModule,
    InternModule,
  ],
  exports :[
   
  ],

})
export class QuestionsModule { }
