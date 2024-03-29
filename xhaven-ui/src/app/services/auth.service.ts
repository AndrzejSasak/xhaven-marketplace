import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';

import { LoginDto, RegisterDto, TokenDto } from '../models/dto/dto-models';

const API_URL = 'http://localhost:8080/api/v1/auth'

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public JWT_LOCAL_STORAGE_KEY: string = 'jwt';

  constructor(private http: HttpClient, private jwtHelperService: JwtHelperService) { }

  getToken(): string {
    return localStorage.getItem(this.JWT_LOCAL_STORAGE_KEY) || '';
  }

  isAuthenticated(): boolean {
    const token = this.getToken();

    if(!token || token == '') {
      return false;
    }

    return !this.jwtHelperService.isTokenExpired(token);
  }

  login(loginDto: LoginDto): Observable<TokenDto> {
    return this.http.post<TokenDto>(`${API_URL}/login`, loginDto, {headers: {skipIntercept: 'true'}})
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if(error.status === 403) {
            return throwError(() => 'Unable to log in with provided credentials')
          }
          return throwError(() => 'An error occurred while trying to log in')
        })
      );
  }

  logout(): Observable<any> {
    return this.http.post(`${API_URL}/logout`, null);
  }

  register(registerDto: RegisterDto): Observable<TokenDto> {
    return this.http.post<TokenDto>(`${API_URL}/register`, registerDto, {headers: {skipIntercept: 'true'}})
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if(error.status === 409) {
            return throwError(() => 'User with entered email already exists');
          }
          return throwError(() => 'An error occurred while registering the user')
        })
      );
  }

}
