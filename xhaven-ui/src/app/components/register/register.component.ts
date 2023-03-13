import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterDto } from 'src/app/models/dto/dto-models';
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

  register() {
    const registerDto: RegisterDto = {
      'name': this.name,
      'surname': this.surname,
      'email': this.email,
      'phoneNumber': this.phoneNumber,
      'password': this.password
    }

    this.authService.register(registerDto).subscribe(
      (tokenDto: any) => {
        localStorage.setItem(this.authService.JWT_LOCAL_STORAGE_KEY, tokenDto.token)
        this.router.navigate(['home']);
      }
    )

  }
}
