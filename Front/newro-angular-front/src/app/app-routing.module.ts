import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './user/dashboard/dashboard.component';
import { AddInternComponent } from './intern/add-intern/add-intern.component';
import { EditStagiaireComponent } from './intern/edit-stagiaire/edit-stagiaire.component';
import { InternListComponent } from './intern/intern-list/intern-list.component';
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ChapterDashboardComponent } from './chapter/chapter-dashboard/chapter-dashboard.component';
import { ChapterDetailsComponent } from './chapter/chapter-details/chapter-details.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { QuestionListChapterComponent } from './questions/question-list-chapter/question-list-chapter.component';
import { PageAccessDeniedComponent } from './page-access-denied/page-access-denied.component';
import { PageInternalErrorComponent } from './page-internal-error/page-internal-error.component';
import { PageErrorBadRequestComponent } from './page-error-bad-request/page-error-bad-request.component';

const routes: Routes = [
  {
    path: 'stagiaires',
    component: InternListComponent,
    pathMatch: 'full',
  },
  {
    path: 'ajoutStagiaire',
    component: AddInternComponent,
    pathMatch: 'full',
  },
  {
    path: 'editStagiaire/:id',
    component: EditStagiaireComponent,
    pathMatch: 'full',
  },
  {
    path: 'connexion',
    component: LoginComponent,
    pathMatch: 'full',
  },
  {
    path: 'inscription',
    component: RegisterComponent,
    pathMatch: 'full',
  },
  {
    path: 'utilisateurs',
    component: DashboardComponent,
    pathMatch: 'full',
  },
  {
    path: 'editUtilisateur/:id',
    component: EditUserComponent,
    pathMatch: 'full',
  },
  {
    path: 'cours',
    component: ChapterDashboardComponent,
    pathMatch: 'full',
  },
 
  {
    path : 'cours/:id/:id1',
    component : QuestionListChapterComponent,
    pathMatch: 'full'

  },

  {
    path: 'cours/:name',
    component: ChapterDetailsComponent,
    pathMatch: 'full',
  },
  {
    path: '',
    redirectTo: '/connexion',
    pathMatch: 'full',
  },
  {
    path: 'accesRefuse',
    component: PageAccessDeniedComponent,
    pathMatch: 'full',
  },
  {
    path: 'error',
    component: PageInternalErrorComponent,
    pathMatch: 'full',
  },
  {
    path: 'error400',
    component: PageErrorBadRequestComponent,
    pathMatch: 'full',
  },
  {
    path: '**',
    component: PageNotFoundComponent,
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
