import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AuctionDto, ImageDto} from "../../models/dto/dto-models";
import {AuctionService} from "../../services/auction.service";
import {ActivatedRoute} from "@angular/router";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {mergeMap} from "rxjs";

@Component({
  selector: 'app-auction',
  templateUrl: './auction.component.html',
  styleUrls: ['./auction.component.scss']
})
export class AuctionComponent implements OnInit {

  currentAuction: AuctionDto;   //TODO: change to Auction class
  imageURLs: SafeUrl[] = [];

  constructor(private auctionService: AuctionService,
              private userService: UserService,
              private activatedRoute: ActivatedRoute,
              private sanitizer: DomSanitizer,
              private changeDetectorRef: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.auctionService.getAuctionById(this.activatedRoute.snapshot.paramMap.get('auctionId')).subscribe(
      (auction: AuctionDto) => {
        auction.images?.forEach((image: ImageDto) => {
          const imageURL: SafeUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/jpg;base64,' + image.fileBytes);
          this.imageURLs = this.imageURLs.concat(imageURL);
        })
        this.currentAuction = auction;
        this.currentAuction.favorite;
        console.log(auction)
      }
    );
  }

  addToFavorites() {
    this.userService.getCurrentUser().pipe(
      mergeMap((user: User) =>
        this.userService.addAuctionToFavorites(this.currentAuction.id || '', user.id || ''))
    ).subscribe(value => {
      this.currentAuction.favorite = true;
    })
  }

  removeFromFavorites() {
    this.userService.getCurrentUser().pipe(
      mergeMap((user: User) =>
        this.userService.removeAuctionFromFavorites(this.currentAuction.id || '', user.id || ''))
    ).subscribe(value => {
      this.currentAuction.favorite = false;
    })
  }

}
