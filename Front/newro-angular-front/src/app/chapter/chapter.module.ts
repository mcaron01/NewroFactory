import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChapterDashboardComponent } from './chapter-dashboard/chapter-dashboard.component';
import { ChapterCardComponent } from './chapter-card/chapter-card.component';
import { AppRoutingModule } from '../app-routing.module';
import { CustomMaterialModule } from '../shared/custom.material.module';
import { ChapterDetailsComponent } from './chapter-details/chapter-details.component';
import { InternListComponent } from '../intern/intern-list/intern-list.component';
import { InternModule } from '../intern/intern.module';
import { QuestionsModule } from '../questions/questions.module';

@NgModule({
  declarations: [
    ChapterDashboardComponent,
    ChapterCardComponent,
    ChapterDetailsComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    CustomMaterialModule,
    InternModule,
    QuestionsModule,
  ],
  exports: [
    ChapterDashboardComponent,
    ChapterCardComponent,
    ChapterDetailsComponent,
    
  ],
})
export class ChapterModule { }
