import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {delay, Subject} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthService,
              private userService: UserService ) {
  }

  isLoggedIn: boolean = false;
  currentUser: User | null;

  homeRedirect() {
    this.router.navigate(['home']);
  }
  loginRedirect() {
    this.router.navigate(['login']);
  }
  logoutRedirect() {
    this.router.navigate(['logout']);
  }
  profileRedirect() {
    this.router.navigate(['profile']);
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(
      (user: User | null) => {
        this.currentUser = user;
        this.isLoggedIn = this.authService.isAuthenticated();
        console.log(user?.id);
      }
    );

  }

}
