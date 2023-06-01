import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSelectModule} from '@angular/material/select';
import {MatOptionModule} from '@angular/material/core';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTreeModule} from '@angular/material/tree';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";

import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {TokenInteceptor} from './helpers/auth.interceptor';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RegisterComponent} from './components/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {AuthGuardService} from './services/auth-guard.service';
import {JWT_OPTIONS, JwtHelperService} from '@auth0/angular-jwt';
import {NavbarComponent} from './components/navbar/navbar.component';
import {ProfileComponent} from './components/profile/profile.component';
import {NewAuctionComponent} from './components/new-auction/new-auction.component';
import {ImageUploadComponent} from './components/image-upload/image-upload.component';
import {AuctionComponent} from './components/auction/auction.component';
import {LogoutComponent} from './components/logout/logout.component';
import { ImageStepperComponent } from './components/image-stepper/image-stepper.component';
import {CdkStepperModule} from "@angular/cdk/stepper";
import { RemoveAuctionDialogComponent } from './components/remove-auction-dialog/remove-auction-dialog.component';

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent, canActivate: [AuthGuardService]},
  {path: 'home', component: HomeComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService]},
  {path: 'new-auction', component: NewAuctionComponent, canActivate: [AuthGuardService]},
  {path: 'auctions/:auctionId', component: AuctionComponent},
  {path: '**', redirectTo: 'home'}
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    NavbarComponent,
    ProfileComponent,
    NewAuctionComponent,
    ImageUploadComponent,
    AuctionComponent,
    LogoutComponent,
    ImageStepperComponent,
    RemoveAuctionDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {enableTracing: true}),
    BrowserAnimationsModule,
    MatCardModule,
    FormsModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatOptionModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatTabsModule,
    MatTreeModule,
    NgbModule,
    CdkStepperModule,
    MatDialogModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInteceptor,
      multi: true
    },
    {
      provide: JWT_OPTIONS,
      useValue: JWT_OPTIONS,
    },
    {
      provide: MatDialogRef,
      useValue: {}
    },
    JwtHelperService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
