import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CategoryDto, ThumbnailAuctionDto} from 'src/app/models/dto/dto-models';
import {AuctionService} from 'src/app/services/auction.service';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ThumbnailAuction} from "../../models/thumbnailAuction";
import {mergeMap} from "rxjs";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {CategoryService} from "../../services/category.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  auctions: ThumbnailAuction[] = [];
  auctionsLength: number;
  categories: CategoryDto[];

  constructor(private router: Router,
              private auctionService: AuctionService,
              private sanitizer: DomSanitizer,
              private userService: UserService,
              private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.auctionService.getAuctions().subscribe((auctions: ThumbnailAuctionDto[]) => {

      auctions.forEach((auction: ThumbnailAuctionDto) => { //TODO extract duplicated for each to imageservice

        const imageURL: SafeUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/jpg;base64,' + auction.thumbnail?.fileBytes);
        let thumbnailAuction: ThumbnailAuction = {
          id: auction.id,
          title: auction.title,
          description: auction.description,
          price: auction.price,
          thumbnail: imageURL,
          postedAt: auction.postedAt,
          favorite: auction.favorite,
          active: auction.active
        }

        console.log(thumbnailAuction.favorite)
        this.auctions = this.auctions.concat(thumbnailAuction);
      })

      this.auctionsLength = this.auctions.length;
    });


    this.categoryService.getCategories().subscribe((categories: CategoryDto[]): void => {
      this.categories = categories;
    })
  }

  newAuctionRedirect() {
    this.router.navigate(['new-auction']);
  }

  auctionRedirect(id?: string) {
      this.router.navigate(['auctions', id]);
  }

  addToFavorites(auction: ThumbnailAuction) {
    this.userService.getCurrentUser().pipe(
      mergeMap((user: User) =>
        this.userService.addAuctionToFavorites(auction.id || '', user.id || ''))
    ).subscribe(value => {
      auction.favorite = true;
    })
  }

  removeFromFavorites(auction: ThumbnailAuction) {
    this.userService.getCurrentUser().pipe(
      mergeMap((user: User) =>
        this.userService.removeAuctionFromFavorites(auction.id || '', user.id || ''))
    ).subscribe(value => {
      auction.favorite = false;
    })
  }
}
