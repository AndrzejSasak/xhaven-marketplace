import { Injectable } from '@angular/core';
import {User} from "../models/user";
import {of, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";

const API_URL = 'http://localhost:8080/api/v1/users';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUser: User | null;
  constructor(private httpClient: HttpClient) {}

  getCurrentUser() {
    return this.currentUser ? of(this.currentUser) : this.httpClient.get(`${API_URL}/current`)
      .pipe(tap((user: User) => this.currentUser = user));
  }

  removeCurrentUser() {
    this.currentUser = null;
  }

}
