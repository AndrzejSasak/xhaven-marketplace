import {Component, OnInit} from '@angular/core';
import {ThumbnailAuctionDto} from "../../models/dto/dto-models";
import {Router} from "@angular/router";
import {AuctionService} from "../../services/auction.service";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {map, mergeMap, switchMap} from "rxjs";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ThumbnailAuction} from "../../models/thumbnailAuction";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  userAuctions: ThumbnailAuction[] = [];
  userAuctionsLength: number;
  favoriteAuctions: ThumbnailAuction[] = [];
  favoriteAuctionsLength: number;
  currentUser: User;

  constructor(private router: Router,
              private auctionService: AuctionService,
              private userService: UserService,
              private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.userService.getCurrentUser()
      .pipe(
        switchMap((user: User) => this.auctionService.getAuctionsByOwnerId(user.id || ''))
      )
      .subscribe((auctions: ThumbnailAuctionDto[]) => {
        let auctionsArray: ThumbnailAuctionDto[] = [];
        auctionsArray = auctionsArray.concat(auctions);
        auctionsArray.forEach((auction: ThumbnailAuctionDto) => { //TODO extract duplicated for each to imageservice
          const imageURL: SafeUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/jpg;base64,' + auction.thumbnail?.fileBytes);
          let thumbnailAuction: ThumbnailAuction = {
            id: auction.id,
            title: auction.title,
            description: auction.description,
            price: auction.price,
            thumbnail: imageURL,
            postedAt: auction.postedAt,
            favorite: auction.favorite
          }

          this.userAuctions = this.userAuctions.concat(thumbnailAuction);
        })
        this.userAuctionsLength = this.userAuctions.length;
      });

    this.userService.getCurrentUser()
      .pipe(
        switchMap((user: User) => this.userService.getFavoriteAuctionsOfUser(user.id || ''))
      )
      .subscribe((auctions: ThumbnailAuctionDto[]) => {
        let auctionsArray: ThumbnailAuctionDto[] = [];
        auctionsArray = auctionsArray.concat(auctions);
        auctionsArray.forEach((auction: ThumbnailAuctionDto) => { //TODO extract duplicated for each to imageservice
          const imageURL: SafeUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/jpg;base64,' + auction.thumbnail?.fileBytes);
          let thumbnailAuction: ThumbnailAuction = {
            id: auction.id,
            title: auction.title,
            description: auction.description,
            price: auction.price,
            thumbnail: imageURL,
            postedAt: auction.postedAt,
            favorite: auction.favorite
          }

          this.favoriteAuctions = this.favoriteAuctions.concat(thumbnailAuction);
        })
        this.favoriteAuctionsLength = this.favoriteAuctions.length;
      });
  }

  auctionRedirect(id?: string) {
    this.router.navigate(['auctions', id]);
  }

  addToFavorites(auction: ThumbnailAuction) {
    this.userService.getCurrentUser().pipe(
      mergeMap((user: User) =>
        this.userService.addAuctionToFavorites(auction.id || '', user.id || ''))
    ).subscribe(value => {
      this.favoriteAuctions.push(auction)
    })
    auction.favorite = true;
  }

  removeFromFavorites(auction: ThumbnailAuction) {
    this.userService.getCurrentUser().pipe(
      mergeMap((user: User) =>
        this.userService.removeAuctionFromFavorites(auction.id || '', user.id || ''))
    ).subscribe(value => {
      auction.favorite = false;
      const index = this.favoriteAuctions.indexOf(auction, 0);
      if(index > -1) {
        this.favoriteAuctions.splice(index, 1);
      }
    })
  }

}
