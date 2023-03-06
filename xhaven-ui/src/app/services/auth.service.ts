import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { LoginDto, TokenDto } from '../models/dto/dto-models';

const API_URL = 'http://localhost:8080/api/v1/auth'

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public JWT_LOCAL_STORAGE_KEY: string = 'jwt';

  constructor(private http: HttpClient) { }

  getToken(): string {
    return localStorage.getItem(this.JWT_LOCAL_STORAGE_KEY) || '{}';
  }

  login(loginDto: LoginDto) {
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        }),
        observe: 'response'
      }

      return this.http.post<TokenDto>(`${API_URL}/login`, loginDto);
  }


}
