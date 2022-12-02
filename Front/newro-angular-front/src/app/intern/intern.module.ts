import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from '../app-routing.module';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomMaterialModule } from '../shared/custom.material.module';
import { AddInternComponent } from './add-intern/add-intern.component';
import { EditStagiaireComponent } from './edit-stagiaire/edit-stagiaire.component';
import { DeleteDialogComponent } from './delete-dialog/delete-dialog.component';
import { InternListComponent } from './intern-list/intern-list.component';
import { SnackbarComponent } from './snackbar/snackbar.component';
import { MatTableModule } from '@angular/material/table';

@NgModule({
  declarations: [
    AddInternComponent,
    EditStagiaireComponent,
    DeleteDialogComponent,
    InternListComponent,
    SnackbarComponent,
    
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    CustomMaterialModule,
    FormsModule,
    MatTableModule,
    ReactiveFormsModule,
  ],
})
export class InternModule {}
