import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router,
              private userService: UserService,
              private authService: AuthService) {}

  isLoggedIn: boolean; //@Input()??
  currentUser: User;

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
    this.userService.getCurrentUser()
      .subscribe((user: User) => {
        this.currentUser = user;
        this.isLoggedIn = this.authService.isAuthenticated();
      });
  }

}
