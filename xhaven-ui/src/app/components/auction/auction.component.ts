import {Component, OnInit} from '@angular/core';
import {AuctionDto, ImageDto} from "../../models/dto/dto-models";
import {AuctionService} from "../../services/auction.service";
import {ActivatedRoute} from "@angular/router";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-auction',
  templateUrl: './auction.component.html',
  styleUrls: ['./auction.component.scss']
})
export class AuctionComponent implements OnInit {

  isFollowed = false;
  currentAuction: AuctionDto;   //TODO: change to Auction class
  imageURLs: SafeUrl[] = [];

  constructor(private auctionService: AuctionService,
              private activatedRoute: ActivatedRoute,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.auctionService.getAuctionById(this.activatedRoute.snapshot.paramMap.get('auctionId')).subscribe(
      (auction: AuctionDto) => {
        auction.images?.forEach((image: ImageDto) => {
          const imageURL: SafeUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/jpg;base64,' + image.fileBytes);

          this.imageURLs = this.imageURLs.concat(imageURL);
        })

        this.currentAuction = auction;
        console.log(this.imageURLs)
      }
    );

  }
}
