import { Component } from '@angular/core';
import {Location} from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent {

  constructor(private authService: AuthService,
              private userService: UserService,
              private location: Location,
              private router: Router) {
  }

  logout() {
    this.authService.logout().subscribe(response => {
      this.userService.removeCurrentUser();
      localStorage.removeItem('jwt');
      this.router.navigate(['home']);
    });
  }

  redirectBack() {
    this.location.back();
  }
}
