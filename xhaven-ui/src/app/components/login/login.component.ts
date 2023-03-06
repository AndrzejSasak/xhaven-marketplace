import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

import { LoginDto, TokenDto } from '../../models/dto/dto-models';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public email: string;
  public password: string;
  
  constructor(private authService: AuthService, private router: Router) { }
  

  login() {
    const loginDto: LoginDto = {
      'email': this.email,
      'password': this.password
    }
    
    this.authService.login(loginDto).subscribe(
      (tokenDto: any) => localStorage.setItem(this.authService.JWT_LOCAL_STORAGE_KEY, tokenDto.token)
    );
  }

  ngOnInit(): void {
  }

}
