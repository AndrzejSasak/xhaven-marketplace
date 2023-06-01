import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from 'src/app/services/auth.service';

import {LoginDto} from '../../models/dto/dto-models';
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginForm: FormGroup;
  showPassword: boolean = false;
  errorMessage: string;

  public email: string;
  public password: string;

  constructor(private authService: AuthService,
              private userService: UserService,
              private router: Router,
              private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if(this.loginForm.invalid) {
      return;
    }

    const loginDto: LoginDto = {
      'email': this.loginForm.get('email')?.value,
      'password': this.loginForm.get('password')?.value
    }

    this.authService.login(loginDto).subscribe(
      (tokenDto: any) => {
        localStorage.setItem(this.authService.JWT_LOCAL_STORAGE_KEY, tokenDto.token);
        this.router.navigate(['home']);
      },
      (error) => {
        this.errorMessage = error;
      }
    )
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  registerRedirect() {
    this.router.navigate(['register']);
  }

}
