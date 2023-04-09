import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

import { LoginDto, TokenDto } from '../../models/dto/dto-models';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public email: string;
  public password: string;

  constructor(private authService: AuthService,
              private userService: UserService,
              private router: Router) { }


  login() {
    if(this.authService.isAuthenticated()) {
      this.router.navigate(['home']);
    }

    const loginDto: LoginDto = {
      'email': this.email,
      'password': this.password
    };

    this.authService.login(loginDto).subscribe(
      (tokenDto: any) => {
        localStorage.setItem(this.authService.JWT_LOCAL_STORAGE_KEY, tokenDto.token);
        this.router.navigate(['home']);
      }
    )
  }

  registerRedirect() {
    this.router.navigate(['register']);
  }

  ngOnInit(): void {
  }

}
