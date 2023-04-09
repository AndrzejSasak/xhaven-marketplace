import {Component, OnInit} from '@angular/core';
import {AuctionDto} from "../../models/dto/dto-models";
import {Router} from "@angular/router";
import {AuctionService} from "../../services/auction.service";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {mergeMap} from "rxjs";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  userAuctionList: AuctionDto[] = [];
  length: number;
  currentUser: User;

  constructor(private router: Router,
              private auctionService: AuctionService,
              private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getCurrentUser()
      .pipe(
        mergeMap((user: User) => this.auctionService.getAuctionsByUserId(user.id || ''))
      )
      .subscribe((auctions: AuctionDto[]) => {
        this.userAuctionList = this.userAuctionList.concat(auctions);
        this.length = this.userAuctionList.length;
      });
  }

}
