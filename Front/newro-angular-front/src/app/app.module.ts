import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InternModule } from './intern/intern.module';
import { HeaderComponent } from './header/header.component';
import { CustomMaterialModule } from './shared/custom.material.module';
import { HttpClientModule } from '@angular/common/http';
import { UserModule } from './user/user.module';
import { MAT_DATE_LOCALE } from '@angular/material/core';

import { ChapterModule } from './chapter/chapter.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PageAccessDeniedComponent } from './page-access-denied/page-access-denied.component';
import { PageInternalErrorComponent } from './page-internal-error/page-internal-error.component';
import { PageErrorBadRequestComponent } from './page-error-bad-request/page-error-bad-request.component';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { MyCustomPaginatorIntl } from './shared/custom.paginator';
import { QuestionsModule } from './questions/questions.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PageNotFoundComponent,
    PageAccessDeniedComponent,
    PageInternalErrorComponent,
    PageErrorBadRequestComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    InternModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    HttpClientModule,
    UserModule,
    ChapterModule,
    QuestionsModule,
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'fr-FR' },
    { provide: MatPaginatorIntl, useClass: MyCustomPaginatorIntl },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
