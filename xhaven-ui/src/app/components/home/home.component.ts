import { Component } from '@angular/core';
import { OfferDto } from 'src/app/models/dto/dto-models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  offerList: OfferDto[] = [
    {
      title: "this is a title",
      description: "this is a description",
      postedDate: "2022-01-01",
      price: "13.99"
    },
    {
      title: "this is a second title",
      description: "this is a third description",
      postedDate: "2022-01-01",
      price: "13.99"
    },
    {
      title: "this is a third title",
      description: "this is a second description",
      postedDate: "2022-01-01",
      price: "13.99"
    },
    {
      title: "this is a third title",
      description: "this is a second description",
      postedDate: "2022-01-01",
      price: "13.99"
    },
    {
      title: "this is a third title",
      description: "this is a second description",
      postedDate: "2022-01-01",
      price: "13.99"
    },
    {
      title: "this is a third title",
      description: "this is a second description",
      postedDate: "2022-01-01",
      price: "13.99"
    }
  ];

  length: number = this.offerList.length;
}
