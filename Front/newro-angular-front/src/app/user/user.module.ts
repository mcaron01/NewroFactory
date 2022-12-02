import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { AppRoutingModule } from '../app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomMaterialModule } from '../shared/custom.material.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DeleteUserDialogComponent } from './delete-user-dialog/delete-user-dialog.component';

@NgModule({
  declarations: [
    DashboardComponent,
    LoginComponent,
    RegisterComponent,
    EditUserComponent,
    DeleteUserDialogComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    CustomMaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    LoginComponent,
    RegisterComponent,
    EditUserComponent,
    DashboardComponent,
  ],
})
export class UserModule {}
