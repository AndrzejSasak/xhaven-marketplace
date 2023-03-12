import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public name: string;
  public surname: string;
  public email: string;
  public phoneNumber: string;
  public password: string;
  public confirmPassword: string;

  constructor(private authService: AuthService, private router: Router) { }


}
