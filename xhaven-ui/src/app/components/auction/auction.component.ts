import {Component, OnInit} from '@angular/core';
import {AuctionDto} from "../../models/dto/dto-models";
import {AuctionService} from "../../services/auction.service";

@Component({
  selector: 'app-auction',
  templateUrl: './auction.component.html',
  styleUrls: ['./auction.component.scss']
})
export class AuctionComponent implements OnInit {


  isFollowed = false;
  currentAuction: AuctionDto;   //TODO: change to Auction class
  currentAuctionId = '1';

  constructor(private auctionService: AuctionService) {
  }

  ngOnInit(): void {
    this.auctionService.getAuctionById(this.currentAuctionId).subscribe(
      (auction: AuctionDto) => {
        this.currentAuction = auction;
        console.log(auction);
      }

    );
  }
}
