import {AfterViewInit, Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { OfferDto } from 'src/app/models/dto/dto-models';
import { OfferService } from 'src/app/services/offer.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  offerList: OfferDto[] = [];

  length: number;

  constructor(private router: Router, private offerService: OfferService) {}

  ngOnInit(): void {
    this.offerService.getOffers().subscribe((offers: OfferDto[]) => {
      this.offerList = this.offerList.concat(offers);
      this.length = this.offerList.length; 
    });
  }

  newOfferRedirect() {
    this.router.navigate(['new-offer']);
  }
}
