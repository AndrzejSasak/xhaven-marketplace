import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {RegisterDto} from 'src/app/models/dto/dto-models';
import {AuthService} from 'src/app/services/auth.service';
import {CustomErrorStateMatcher} from "../../helpers/error-state-matcher";
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  registrationForm: FormGroup;
  showPassword: boolean = false;
  showConfirmPassword: boolean = false;
  matcher = new CustomErrorStateMatcher();
  errorMessage: string;

  public name: string;
  public surname: string;
  public email: string;
  public phoneNumber: string;
  public password: string;
  public confirmPassword: string;

  constructor(private authService: AuthService,
              private router: Router,
              private formBuilder: FormBuilder) {
    this.registrationForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      surname: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['']
    }, {validators: this.checkPasswords});
  }

  onSubmit() {
    if(this.registrationForm.invalid) {
      return;
    }

    const registerDto: RegisterDto = {
      'name': this.registrationForm.get('name')?.value,
      'surname': this.registrationForm.get('surname')?.value,
      'email': this.registrationForm.get('email')?.value,
      'phoneNumber': this.registrationForm.get('phoneNumber')?.value,
      'password': this.registrationForm.get('password')?.value
    }

    this.authService.register(registerDto).subscribe(
      (tokenDto: any) => {
        localStorage.setItem(this.authService.JWT_LOCAL_STORAGE_KEY, tokenDto.token)
        this.router.navigate(['home']);
      },
      (error) => {
        this.errorMessage = error;
      }
    )

  }

  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = group.get('password')?.value;
    let confirmPass = group.get('confirmPassword')?.value
    return pass === confirmPass ? null : { notSame: true }
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }
}
