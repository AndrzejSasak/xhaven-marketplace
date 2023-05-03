import {AfterViewInit, Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuctionDto } from 'src/app/models/dto/dto-models';
import { AuctionService } from 'src/app/services/auction.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  auctionList: AuctionDto[] = [];

  length: number;

  constructor(private router: Router, private auctionService: AuctionService) {}

  ngOnInit(): void {
    this.auctionService.getAuctions().subscribe((auctions: AuctionDto[]) => {
      this.auctionList = this.auctionList.concat(auctions);
      this.length = this.auctionList.length;
    });
  }

  newAuctionRedirect() {
    this.router.navigate(['new-auction']);
  }

  auctionRedirect(id?: string) {
      this.router.navigate(['auctions', id]);
  }

}
